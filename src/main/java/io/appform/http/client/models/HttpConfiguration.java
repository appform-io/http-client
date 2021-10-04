package io.appform.http.client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {
    @NotNull
    private String clientName;
    @NotNull
    private String host;
    @NotNull
    private String port;

    private boolean secure;

    @Min(10L)
    @Max(1024L)
    @Builder.Default
    private int connections = 10;

    @Max(86400L)
    @Builder.Default
    private int idleTimeOutSeconds = 30;

    @Max(86400000L)
    @Builder.Default
    private int connectTimeoutMs = 10000;

    @Max(86400000L)
    @Builder.Default
    private int opTimeoutMs = 10000;
}
