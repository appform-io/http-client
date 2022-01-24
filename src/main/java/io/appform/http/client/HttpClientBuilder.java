/*
 * Copyright 2021. Shashank Gautam
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package io.appform.http.client;

import io.appform.http.client.models.HttpConfiguration;
import lombok.val;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpClientBuilder {

    private HttpConfiguration configuration;

    public static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }

    public HttpClientBuilder withConfiguration(HttpConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public OkHttpClient build() {

        if (configuration == null) {
            throw new IllegalArgumentException("httpConfiguration cannot be null");
        }

        val connections = configuration.getConnections() == 0 ? 10 : configuration.getConnections();

        val idleTimeOutSeconds = configuration.getIdleTimeOutSeconds() == 0 ? 30 : configuration.getIdleTimeOutSeconds();

        val connTimeout = configuration.getConnectTimeoutMs() == 0 ? 10000 : configuration.getConnectTimeoutMs();

        val opTimeout = configuration.getOpTimeoutMs() == 0 ? 10000 : configuration.getOpTimeoutMs();

        val dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(connections);
        dispatcher.setMaxRequestsPerHost(connections);

        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(connections, idleTimeOutSeconds, TimeUnit.SECONDS))
                .connectTimeout(connTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(opTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(opTimeout, TimeUnit.MILLISECONDS)
                .dispatcher(dispatcher)
                .build();
    }
}
