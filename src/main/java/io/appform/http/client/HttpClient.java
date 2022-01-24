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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.*;

import java.util.Map;

/**
 */
@Slf4j
@AllArgsConstructor
public class HttpClient {

    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    public final ObjectMapper mapper;
    public final OkHttpClient client;

    @SneakyThrows
    public Response post(
            String url,
            final Object payload,
            final Map<String, String> headers) {
        val httpUrl = HttpUrl.get(url);
        val postBuilder = new Request.Builder()
                .url(httpUrl);

        if (payload instanceof String) {
            postBuilder.post(RequestBody.create((String)payload, APPLICATION_JSON));
        }
        else {
            postBuilder.post(RequestBody.create(mapper.writeValueAsBytes(payload), APPLICATION_JSON));
        }
        if (headers != null) {
            headers.forEach(postBuilder::addHeader);
        }
        val request = postBuilder.build();
        log.debug("Calling {} with request: {}", url, request);
        return client.newCall(request).execute();
    }

    @SneakyThrows
    public Response get(
            final String url,
            final Map<String, String> headers) {
        val httpUrl = HttpUrl.get(url);
        val getBuilder = new Request.Builder()
                .url(httpUrl)
                .get();
        if (headers != null) {
            headers.forEach(getBuilder::addHeader);
        }
        val request = getBuilder.build();
        return client.newCall(request).execute();
    }
}

