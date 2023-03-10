/*
 * Artikel - Warenwirtschaft (Basis)
 * Synchronisation der Artikel mit Kumavision
 *
 * OpenAPI spec version: 1.0.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.model.Article;
import io.swagger.client.model.ArticleMapping;
import org.junit.Test;
import org.junit.Ignore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for DefaultApi
 */
@Ignore
public class DefaultApiTest {

    private final DefaultApi api = new DefaultApi();

    /**
     * neuen Artikel in Alberta anlegen
     *
     * Legt einen neuen Artikel in Alberta an
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void addArticleTest() throws Exception {
        String albertaApiKey = null;
        Article body = null;
        ArticleMapping response = api.addArticle(albertaApiKey, body);

        // TODO: test validations
    }
    /**
     * neuen Krankenkassenvertrag in Alberta anlegen
     *
     * Legt einen neuen Krankenkassenvertrag in Alberta an
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void addInsuranceContractTest() throws Exception {
        String albertaApiKey = null;
        Article body = null;
        ArticleMapping response = api.addInsuranceContract(albertaApiKey, body);

        // TODO: test validations
    }
    /**
     * Artikel in Alberta ändern
     *
     * ändert einen Artikel in Alberta
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateArticleTest() throws Exception {
        String albertaApiKey = null;
        String customerNumber = null;
        Article body = null;
        ArticleMapping response = api.updateArticle(albertaApiKey, customerNumber, body);

        // TODO: test validations
    }
    /**
     * Krankenkassenvertrag in Alberta ändern
     *
     * ändert einen Krankenkassenvertrag in Alberta
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateInsuranceContractTest() throws Exception {
        String albertaApiKey = null;
        String tenant = null;
        String id = null;
        Article body = null;
        ArticleMapping response = api.updateInsuranceContract(albertaApiKey, tenant, id, body);

        // TODO: test validations
    }
}
