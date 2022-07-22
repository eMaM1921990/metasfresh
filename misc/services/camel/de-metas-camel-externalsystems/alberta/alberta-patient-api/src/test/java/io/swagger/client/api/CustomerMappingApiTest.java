/*
 * Patient - Warenwirtschaft (Basis)
 * Synchronisation der Patienten mit der Warenwirtschaft
 *
 * OpenAPI spec version: 1.0.7
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.CustomerMapping;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CustomerMappingApi
 */
@Ignore
public class CustomerMappingApiTest {

    private final CustomerMappingApi api = new CustomerMappingApi();

    /**
     * Zuordnung Kunde (WaWi) zu Patient (Alberta) abrufen
     *
     * Szenario - das WaWi fragt bei Alberta nach, welche Alberta-Id dem jeweiligen Kunden zugeordnet ist
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getCustomerMappingTest() throws ApiException {
        String albertaApiKey = null;
        String customerId = null;
        CustomerMapping response = api.getCustomerMapping(albertaApiKey, customerId);

        // TODO: test validations
    }
}
