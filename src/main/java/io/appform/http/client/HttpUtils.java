package io.appform.http.client;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import okhttp3.Response;
import okhttp3.ResponseBody;

@UtilityClass
public class HttpUtils {

    @SneakyThrows
    public static byte[] bodyAsBytes(Response response){
        try (final ResponseBody body = response.body()) {
            return body.bytes();
        }
    }

    @SneakyThrows
    public static String bodyAsString(Response response) {
        try (final ResponseBody body = response.body()) {
            return body.string();
        }
    }
}
