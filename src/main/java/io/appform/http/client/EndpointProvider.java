package io.appform.http.client;

import io.appform.http.client.models.Endpoint;
import io.appform.http.client.models.EndpointProviderContext;

public interface EndpointProvider {
    Endpoint provide(EndpointProviderContext context);
}
