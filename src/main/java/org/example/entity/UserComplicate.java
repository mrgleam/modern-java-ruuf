package org.example.entity;

public record UserComplicate(
        UserSimple userSimple,
        String field1,
        String field2,
        String field3,
        String field4,
        String field5
) {
}
