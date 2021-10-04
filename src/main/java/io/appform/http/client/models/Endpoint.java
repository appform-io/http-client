package io.appform.http.client.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Endpoint {
    private String host;
    private String port;
}
