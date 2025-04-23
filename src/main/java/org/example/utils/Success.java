package org.example.utils;

public record Success<T, E extends Exception>(T value) implements Result<T, E> {
    public boolean isSuccess() {
        return true;
    }

    public E error() {
        throw new UnsupportedOperationException("No error on Success");
    }

    public <U> Result<U, E> map(java.util.function.Function<? super T, ? extends U> mapper) {
        return new Success<>(mapper.apply(value));
    }

    public <U> Result<U, E> flatMap(java.util.function.Function<? super T, Result<U, E>> binder) {
        return binder.apply(value);
    }

    public Result<T, E> onSuccess(java.util.function.Consumer<? super T> successHandler) {
        successHandler.accept(value);
        return this; // Return this for chaining
    }

    public Result<T, E> onFailure(java.util.function.Consumer<? super E> errorHandler) {
        return this; // No-op, just return the current instance
    }
}
