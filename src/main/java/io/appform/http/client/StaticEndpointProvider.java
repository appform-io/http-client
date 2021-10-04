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
                .build();
    }
}
