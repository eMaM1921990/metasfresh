/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.lims.process.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.lims.process.constants.LIMSConfigurationKeys;
import de.metas.lims.process.model.LIMSModel;
import de.metas.lims.process.service.LIMSIntegrationClient;
import de.metas.lims.process.service.errorHandler.RestTemplateResponseErrorHandler;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigDAO;
import org.compiere.util.Env;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class LIMSIntegrationClientImpl implements LIMSIntegrationClient
{
	private final ISysConfigDAO iSysConfigDAO =  Services.get(ISysConfigDAO.class);

	@Override
	public void sendSamples(final LIMSModel limsModel)
	{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		ClientAndOrgId clientId = ClientAndOrgId.ofClientId(Env.getAD_Client_ID());
		String url = iSysConfigDAO.getValue(LIMSConfigurationKeys.LIMS_URL_KEY, clientId)
				.orElse("https://demo.bilyazilim.com/seam/resource/resttkiv1/aliquotrs/erp/createSamples");
		String password = iSysConfigDAO.getValue(LIMSConfigurationKeys.PASSWORD_KEY, clientId)
						.orElse("1");
		String username = iSysConfigDAO.getValue(LIMSConfigurationKeys.USERNAME_KEY, clientId)
						.orElse("admin");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(username, password);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setHost(new InetSocketAddress("localhost", 8080));
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String body = objectMapper.writeValueAsString(limsModel);
			httpHeaders.setContentLength(Long.parseLong(String.valueOf(body.getBytes(StandardCharsets.UTF_8).length)));
			HttpEntity<?> requestData = new HttpEntity<>(limsModel, httpHeaders);
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestData, String.class);
			log.info("Data sync with status {}, and body {}", response.getStatusCodeValue(), response.getBody());
		} catch (Exception e) {
			log.info("Error {}", e);
			throw new AdempiereException("Error connect to LIMS , error : "  + e.getMessage());
		}
	}
}
