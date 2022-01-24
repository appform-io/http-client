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

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import okhttp3.Response;
import okhttp3.ResponseBody;

@UtilityClass
public class HttpUtils {

    @SneakyThrows
    public static byte[] bodyAsBytes(Response response){
        try (val body = response.body()) {
            return null == body ? new byte[0] : body.bytes();
        }
    }

    @SneakyThrows
    public static String bodyAsString(Response response) {
        try (final ResponseBody body = response.body()) {
            return null == body ? "" : body.string();
        }
    }
}
