/*
 * Copyright 2021. Chandrasekhar Patra
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

import io.appform.http.client.models.Endpoint;
import io.appform.http.client.models.EndpointProviderContext;
import io.appform.http.client.models.HttpConfiguration;

public class StaticEndpointProvider implements EndpointProvider {

    public StaticEndpointProvider() {}

    public Endpoint provide(EndpointProviderContext context) {
        final HttpConfiguration configuration = context.getConfiguration();
        return Endpoint.builder()
                .host(configuration.getHost())
                .port(configuration.getPort())
                .secure(configuration.isSecure())
                .build();
    }
}
