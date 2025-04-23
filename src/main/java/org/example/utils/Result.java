package org.example.utils;

public interface Result<T, E extends Exception> {
    boolean isSuccess();
    T value();
    E error();

    <U> Result<U, E> map(java.util.function.Function<? super T, ? extends U> mapper);

    <U> Result<U, E> flatMap(java.util.function.Function<? super T, Result<U, E>> binder);

    Result<T, E> onSuccess(java.util.function.Consumer<? super T> successHandler);
    Result<T, E> onFailure(java.util.function.Consumer<? super E> errorHandler);
}