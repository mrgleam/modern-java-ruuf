package org.example.utils;

import java.io.FileWriter;
import java.util.function.Function;

public class FileWriterUtil {
    public static Function<String, Result<Unit, Exception>> to(String fileName) {
        return  value -> {
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(value);
                return new Success<>(Unit.INSTANCE);
            } catch (Exception e) {
                return new Failure<>(e);
            }
        };
    }
}
