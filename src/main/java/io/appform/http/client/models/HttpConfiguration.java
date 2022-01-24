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
package io.appform.http.client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {
    @NotEmpty
    private String clientName;
    @NotEmpty
    private String host;
    @Min(0)
    @Max(65_535)
    private int port;

    private boolean secure;

    @Min(10)
    @Max(1024)
    @Builder.Default
    private int connections = 10;

    @Max(86400)
    @Builder.Default
    private int idleTimeOutSeconds = 30;

    @Max(86400000L)
    @Builder.Default
    private long connectTimeoutMs = 10000;

    @Max(86400000L)
    @Builder.Default
    private long opTimeoutMs = 10000;
}
