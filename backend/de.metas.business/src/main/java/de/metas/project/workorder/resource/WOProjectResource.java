/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.lang.ExternalId;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.List;

@Value
public class WOProjectResource
{
	@NonNull
	OrgId orgId;

	@NonNull
	WOProjectResourceId woProjectResourceId;

	@NonNull
	WOProjectStepId woProjectStepId;

	@NonNull CalendarDateRange dateRange;

	@NonNull
	ResourceId resourceId;

	@Nullable
	Boolean isActive;

	@NonNull
	Duration duration;

	@NonNull
	WFDurationUnit durationUnit;

	@Nullable
	ProjectId budgetProjectId;

	@Nullable
	BudgetProjectResourceId projectResourceBudgetId;

	@Nullable
	ExternalId externalId;

	@Nullable
	String testFacilityGroupName;

	@Nullable
	String description;

	@Builder(toBuilder = true)
	private WOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final WOProjectResourceId woProjectResourceId,
			@NonNull final WOProjectStepId woProjectStepId,
			@NonNull final CalendarDateRange dateRange,
			@NonNull final ResourceId resourceId,
			@NonNull final Duration duration,
			@NonNull final WFDurationUnit durationUnit,
			final boolean isActive,
			@Nullable final ProjectId budgetProjectId,
			@Nullable final BudgetProjectResourceId projectResourceBudgetId,
			@Nullable final ExternalId externalId,
			@Nullable final String testFacilityGroupName,
			@Nullable final String description)
	{
		if (!ProjectId.equals(woProjectResourceId.getProjectId(), woProjectStepId.getProjectId()))
		{
			throw new AdempiereException("Project step and resource ID shall share the same projectId: " + woProjectStepId + ", " + woProjectResourceId);
		}

		this.orgId = orgId;
		this.woProjectResourceId = woProjectResourceId;
		this.woProjectStepId = woProjectStepId;
		this.dateRange = dateRange;
		this.resourceId = resourceId;
		this.duration = duration;
		this.durationUnit = durationUnit;
		this.isActive = isActive;
		this.budgetProjectId = budgetProjectId;
		this.projectResourceBudgetId = projectResourceBudgetId;
		this.externalId = externalId;
		this.testFacilityGroupName = testFacilityGroupName;
		this.description = description;
	}

	public static CalendarDateRange computeDateRangeToEncloseAll(@NonNull final List<WOProjectResource> projectResources)
	{
		if (projectResources.isEmpty())
		{
			throw new AdempiereException("No project resources provided");
		}

		final ImmutableList<CalendarDateRange> dateRanges = projectResources.stream()
				.map(WOProjectResource::getDateRange)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		return CalendarDateRange.span(dateRanges);
	}

	public ProjectId getProjectId()
	{
		return woProjectResourceId.getProjectId();
	}
}