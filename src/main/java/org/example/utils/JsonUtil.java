package org.example.utils;

import com.google.gson.Gson;

import java.util.function.Function;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> Result<String, Exception> safeToJson(T object) {
        try {
            String json = gson.toJson(object);
            return new Success<>(json);
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

    public static <T> Function<T, Result<String, Exception>> safeToJson() {
        return JsonUtil::safeToJson;
    }
}
