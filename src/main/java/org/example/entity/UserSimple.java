package org.example.entity;

public record UserSimple(
        String name,
        String email,
        int age,
        boolean isDeveloper
) {
}
