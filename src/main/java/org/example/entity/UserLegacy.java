package org.example.entity;

public record UserLegacy(
        String name,
        String email,
        int age,
        boolean isDeveloper,
        String field1,
        String field2,
        String field3,
        String field4,
        String field5
) {
    public UserComplicate toUserComplicate() {
        UserSimple userSimple = new UserSimple(
                this.name(),
                this.email(),
                this.age(),
                this.isDeveloper());
        return new UserComplicate(
                userSimple,
                "f1",
                "f2",
                "f3",
                "f4",
                "f5");
    }
}
