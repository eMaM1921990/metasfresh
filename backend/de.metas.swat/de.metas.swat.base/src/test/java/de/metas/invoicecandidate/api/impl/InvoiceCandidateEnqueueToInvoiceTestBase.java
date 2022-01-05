package de.metas.invoicecandidate.api.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_AD_User;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * Standard test:
 * <ul>
 * <li>create some invoice candidates: {@link #step10_createInvoiceCandidates()}
 * <li>enqueue them to be invoiced: {@link #step20_enqueueToInvoice()}
 * <li>process workpackages with enqueued invoice candidates: {@link #step30_processEnqueuedWorkpackages()}
 * </ul>
 *
 * @author tsa
 */
abstract class InvoiceCandidateEnqueueToInvoiceTestBase
{
	protected PlainLockManager lockManager;
	protected PlainLockDatabase locksDatabase;

	protected Properties ctx;
	protected I_C_BPartner bpartner1;
	protected ILoggable loggable;

	protected List<I_C_Invoice_Candidate> invoiceCandidates;
	protected IInvoiceCandidateEnqueueResult enqueueResult;
	protected AbstractICTestSupport icTestSupport;

	@BeforeEach
	public void init()
	{
		POJOWrapper.setDefaultStrictValues(false);

		icTestSupport = new AbstractICTestSupport();
		icTestSupport.initStuff();
		icTestSupport.registerModelInterceptors();

		this.lockManager = (PlainLockManager)Services.get(ILockManager.class);
		this.locksDatabase = lockManager.getLockDatabase();

		this.ctx = Env.getCtx();
		this.loggable = Loggables.console();

		this.bpartner1 = icTestSupport.bpartner("test-bp");
	}

	@Test
	public void test()
	{
		//
		// Create the initial invoice candidates
		this.invoiceCandidates = step10_createInvoiceCandidates();
		Check.assumeNotEmpty(invoiceCandidates, "invoiceCandidates not empty");

		//
		// Enqueue invoice candidates to be invoiced
		this.enqueueResult = step20_enqueueToInvoice();

		//
		// Process workpackages which were generated by enqueueing invoice candidates
		step30_processEnqueuedWorkpackages();
	}

	protected abstract List<I_C_Invoice_Candidate> step10_createInvoiceCandidates();

	private final IInvoiceCandidateEnqueueResult step20_enqueueToInvoice()
	{
		final PInstanceId selectionId = POJOLookupMap.get().createSelectionFromModelsCollection(invoiceCandidates);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(true);
		invoicingParams.setOnlyApprovedForInvoicing(false);

		final IInvoiceCandidateEnqueueResult enqueueResult = Services.get(IInvoiceCandBL.class).enqueueForInvoicing()
				.setContext(ctx)
				.setFailIfNothingEnqueued(true)
				.setFailOnChanges(true)
				.setInvoicingParams(invoicingParams)
				.enqueueSelection(selectionId);

		//
		// Make sure nothing is locked by enqueuer lock at this point because:
		// * invoice candidates which were added to workpackages, they have a lock per workpackage
		// * invoice candidates which were skipped, they shall be released
		final ILock enqueuerLock = enqueueResult.getLock();
		assertEquals(0, enqueuerLock.getCountLocked(), "Invalid enqueuerLock count: " + enqueuerLock);

		// Test: all invoice candidates were locked on enqueue
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			assertTrue(lockManager.isLocked(ic), "IC is locked: " + ic);
		}

		return enqueueResult;
	}

	private void step30_processEnqueuedWorkpackages()
	{
		final Class<InvoiceCandWorkpackageProcessor> workpackageProcessorClass = InvoiceCandWorkpackageProcessor.class;

		//
		// Validate invoice candidates from each workpackage
		for (final I_C_Queue_WorkPackage workpackage : retrieveWorkpackages(workpackageProcessorClass))
		{
			assertWorkpackageInvoiceCandidatesValid(workpackage);
		}

		final InvoiceGeneratedNotificationChecker notificationsChecker = InvoiceGeneratedNotificationChecker.createAnSubscribe();

		//
		// Make sure the current user is configured to receive notifications
		final org.compiere.model.I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setAD_User_ID(0);
		user.setNotificationType(X_AD_User.NOTIFICATIONTYPE_Notice);
		InterfaceWrapperHelper.save(user);
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, user.getAD_User_ID());

		//
		// Process all workpackages synchronously
		final IWorkPackageQueue workpackagesQueue = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, workpackageProcessorClass);
		final IQueueProcessor workpackagesQueueProcessor = Services.get(IQueueProcessorFactory.class)
				.createSynchronousQueueProcessor(workpackagesQueue);
		workpackagesQueueProcessor.run();

		//
		// Make sure all of them are processed
		final List<I_C_Queue_WorkPackage> workpackages = retrieveWorkpackages(workpackageProcessorClass);
		assertFalse(workpackages.isEmpty(), "Some workpackages were created");

		for (I_C_Queue_WorkPackage workpackage : workpackages)
		{
			assertTrue(workpackage.isProcessed(), "Workpackage processed: " + workpackage);
			assertFalse(workpackage.isError(), "Workpackage no error: " + workpackage);
		}

		//
		// Make sure all invoice candidates are without errors
		InterfaceWrapperHelper.refreshAll(invoiceCandidates);
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			assertFalse(ic.isError(), "IC shall have no error: " + ic);
			assertFalse(lockManager.isLocked(ic), "IC is not locked: " + ic);
		}

		//
		// Make sure all generated invoices were notified.
		// We can assume all existing invoices were generated now
		{
			final List<I_C_Invoice> allInvoices = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice.class, Env.getCtx(), ITrx.TRXNAME_None)
					.create()
					.list();

			notificationsChecker.assertAllNotified(allInvoices);
		}
	}

	protected void assertWorkpackageInvoiceCandidatesValid(@NonNull final I_C_Queue_WorkPackage workpackage)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);

		final IParams workpackageParams = workpackageParamDAO.retrieveWorkpackageParams(workpackage);
		final List<I_C_Invoice_Candidate> ics = queueDAO.retrieveAllItems(workpackage, I_C_Invoice_Candidate.class);

		//
		// Create the invoice candidates processor.
		final InvoiceCandWorkpackageProcessor workpackageProcessor = new InvoiceCandWorkpackageProcessor();
		workpackageProcessor.setC_Queue_WorkPackage(workpackage);
		workpackageProcessor.setParameters(workpackageParams);

		//
		// Test: each workpackage has locked it's own invoice candidates by a separate lock
		final Optional<ILock> icLock = workpackageProcessor.getElementsLock();
		Check.assumeNotNull(icLock.orElse(null), "icLock not null for workpackageProcessor {}", workpackageProcessor);
		for (final I_C_Invoice_Candidate ic : ics)
		{
			final String message = "IC is locked"
					+ "\n " + icLock
					+ "\n" + ic
					+ "\n" + workpackage;
			assertTrue(icLock.get().isLocked(ic), message);
		}

		//
		// Test: NetAmtToInvoice set per workpackage shall be the sum of NetAmtToInvoice of enqueued invoice candidates
		final BigDecimal netAmtToInvoiceCalc = calculateTotalNetAmtToInvoice(ics);
		final BigDecimal netAmtToInvoice = workpackageParams.getParameterAsBigDecimal(IInvoicingParams.PARA_Check_NetAmtToInvoice);
		assertThat(netAmtToInvoiceCalc).as("NetAmtToInvoice shall match: " + workpackage).isEqualByComparingTo(netAmtToInvoice);
	}

	protected final List<I_C_Queue_WorkPackage> retrieveWorkpackages(final Class<?> workpackageProcessorClass)
	{
		final String workpackageClassnameExpected = workpackageProcessorClass.getName();

		final List<I_C_Queue_WorkPackage> result = new ArrayList<>();
		for (final I_C_Queue_WorkPackage workpackage : POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class))
		{
			final String workpackageClassname = workpackage.getC_Queue_Block().getC_Queue_PackageProcessor().getClassname();
			if (workpackageClassnameExpected.equals(workpackageClassname))
			{
				result.add(workpackage);
			}
		}

		return result;
	}

	protected final BigDecimal calculateTotalNetAmtToInvoice(final Collection<I_C_Invoice_Candidate> invoiceCandidates)
	{
		BigDecimal totalNetAmt = BigDecimal.ZERO;
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			totalNetAmt = totalNetAmt.add(ic.getNetAmtToInvoice());
		}
		return totalNetAmt;
	}

}
