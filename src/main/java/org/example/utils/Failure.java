package org.example.utils;

public record Failure<T, E extends Exception>(E error) implements Result<T, E> {

    public boolean isSuccess() {
        return false;
    }

    public T value() {
        throw new UnsupportedOperationException("No value on Failure");
    }

    public <U> Result<U, E> map(java.util.function.Function<? super T, ? extends U> mapper) {
        return new Failure<>(error);
    }

    public <U> Result<U, E> flatMap(java.util.function.Function<? super T, Result<U, E>> binder) {
        return new Failure<>(error);
    }

    public Result<T, E> onSuccess(java.util.function.Consumer<? super T> successHandler) {
        return this; // No-op, just return the current instance
    }

    public Result<T, E> onFailure(java.util.function.Consumer<? super E> errorHandler) {
        errorHandler.accept(error);
        return this; // Return this for chaining
    }
}
