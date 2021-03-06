/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.asquera.elasticsearch.plugins.http.auth.integration;


import org.apache.http.impl.client.HttpClients;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.elasticsearch.test.ElasticsearchIntegrationTest.ClusterScope;
import org.elasticsearch.test.ElasticsearchIntegrationTest.Scope;
import org.elasticsearch.test.rest.client.http.HttpRequestBuilder;
import org.elasticsearch.test.rest.client.http.HttpResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Test a rest action that sets special response headers
 */
@ClusterScope(transportClientRatio = 0.0, scope = Scope.SUITE)
public class DefaultConfigurationIntegrationTest extends ElasticsearchIntegrationTest {

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        return ImmutableSettings.settingsBuilder()
                .build();
    }

    @Test
    public void testHealthCheck() throws Exception {
        HttpResponse response = httpClient().path("/").execute();
        assertThat(response.getStatusCode(), equalTo(RestStatus.OK.getStatus()));
    }

    @Test
    public void localhostClientIsAuthenticated() throws Exception {
        HttpResponse response = httpClient().path("/_status").execute();
        assertThat(response.getStatusCode(), equalTo(RestStatus.OK.getStatus()));
    }
   
    public static HttpRequestBuilder httpClient() {
        return new HttpRequestBuilder(HttpClients.createDefault()).host("localhost").port(9200);
    }
}
