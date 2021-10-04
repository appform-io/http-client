package io.appform.http.client;

import com.codahale.metrics.MetricRegistry;
import com.raskasa.metrics.okhttp.InstrumentedOkHttpClients;
import io.appform.http.client.models.HttpConfiguration;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpClientBuilder {

    private MetricRegistry metricRegistry;

    private HttpConfiguration configuration;

    public static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }

    public HttpClientBuilder withMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
        return this;
    }

    public HttpClientBuilder withConfiguration(HttpConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public OkHttpClient build() {

        if (configuration == null) {
            throw new IllegalArgumentException("httpConfiguration cannot be null");
        }

        int connections = configuration.getConnections();
        connections = connections == 0 ? 10 : connections;

        int idleTimeOutSeconds = configuration.getIdleTimeOutSeconds();
        idleTimeOutSeconds = idleTimeOutSeconds == 0 ? 30 : idleTimeOutSeconds;

        int connTimeout = configuration.getConnectTimeoutMs();
        connTimeout = connTimeout == 0 ? 10000 : connTimeout;

        int opTimeout = configuration.getOpTimeoutMs();
        opTimeout = opTimeout == 0 ? 10000 : opTimeout;

        final Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(connections);
        dispatcher.setMaxRequestsPerHost(connections);

        final OkHttpClient.Builder clientBuilder = (new OkHttpClient.Builder())
                .connectionPool(new ConnectionPool(connections, idleTimeOutSeconds, TimeUnit.SECONDS))
                .connectTimeout(connTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(opTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(opTimeout, TimeUnit.MILLISECONDS)
                .dispatcher(dispatcher);

        if (metricRegistry == null) {
            return clientBuilder.build();
        }

        return InstrumentedOkHttpClients.create(metricRegistry, clientBuilder.build(), configuration.getClientName() + System.currentTimeMillis());
    }
}
