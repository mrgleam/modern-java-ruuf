package org.example.utils;

import java.util.function.Function;

public record Compose() {
    @SafeVarargs
    public static <T> Function<T, Result<Unit, Exception>> pipe(Function<T, Result<Unit, Exception>>... fns) {
        return input -> {
            for (Function<T, Result<Unit, Exception>> fn : fns) {
                Result<Unit, Exception> result = fn.apply(input);
                if (!result.isSuccess()) {
                    return result; // Fail fast
                }
            }
            return new Success<>(Unit.INSTANCE);
        };
    }
}
