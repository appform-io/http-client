package io.appform.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.appform.http.client.models.EndpointProviderContext;
import io.appform.http.client.models.HttpConfiguration;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
@WireMockTest
class HttpClientTest {

    @Test
    @SneakyThrows
    void testGet(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").willReturn(ok()));
        val r = httpClient.get(epf.uri("/api"), null);
        assertTrue(r.isSuccessful());
    }

    @Test
    @SneakyThrows
    void testGetError(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        try {
            httpClient.get(epf.uri("/api"), Map.of());
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof SocketException);
        }
    }

    @Test
    @SneakyThrows
    void testGetWithHeader(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").withHeader("TH", equalTo("Value")).willReturn(ok()));
        {
            val r = httpClient.get(epf.uri("/api"), Map.of());
            assertFalse(r.isSuccessful());
        }
        stubFor(get("/api").withHeader("TH", equalTo("Value")).willReturn(ok()));
        {
            val r = httpClient.get(epf.uri("/api"), Map.of("TH", "Value"));
            assertTrue(r.isSuccessful());
        }

    }

    @Test
    @SneakyThrows
    void testGetWithBody(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").willReturn(jsonResponse(Map.of("name", "SG"), 200)));
        val r = httpClient.get(epf.uri("/api"), Map.of());
        assertTrue(r.isSuccessful());
        assertNotNull(HttpUtils.bodyAsString(r));
    }


    @Test
    @SneakyThrows
    void testGetWithBodyObject(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").willReturn(jsonResponse(Map.of("name", "SG"), 200)));
        val r = httpClient.get(epf.uri("/api"), Map.of());
        assertTrue(r.isSuccessful());
        assertNotNull(HttpUtils.bodyAsBytes(r));
    }

    @Test
    @SneakyThrows
    void testGetFail(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(get("/api").willReturn(serverError()));
        val r = httpClient.get(epf.uri("/api"), Map.of());
        assertFalse(r.isSuccessful());
    }

    @Test
    @SneakyThrows
    void testPost(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(post("/api")
                        .withHeader("content-type", equalTo("application/json; charset=UTF-8"))
                        .withHeader("TH", equalTo("Value"))
                        .willReturn(ok()));
        val r = httpClient.post(epf.uri("/api"), "{ \"name\" : \"ss\" }", Map.of("TH", "Value"));
        assertTrue(r.isSuccessful());
    }


    @Test
    @SneakyThrows
    void testPostFail(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(post("/api")
                        .willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        try {
            httpClient.post(epf.uri("/api"), "{ \"name\" : \"ss\" }", null);
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof SocketException);
        }
    }

    @Test
    @SneakyThrows
    void testPostObject(final WireMockRuntimeInfo wm) {
        final var httpConfig = HttpConfiguration.builder()
                .host("localhost")
                .port(wm.getHttpPort())
                .build();
        val httpClient = new HttpClient(
                new ObjectMapper(),
                HttpClientBuilder.builder()
                        .withConfiguration(httpConfig)
                        .build());
        val epf = new StaticEndpointProvider().provide(EndpointProviderContext.builder()
                                                               .configuration(httpConfig)
                                                               .build());
        stubFor(post("/api").withRequestBody(equalToJson(Json.write(Map.of("name", "SG")))).willReturn(ok()));
        val r = httpClient.post(epf.uri("/api"), Map.of("name", "SG"), Map.of());
        assertTrue(r.isSuccessful());
    }
}