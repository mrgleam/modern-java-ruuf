package org.example;

public class UserComplicate {
    public UserSimple userSimple;
    public String field1;
    public String field2;
    public String field3;
    public String field4;
    public String field5;

    public UserComplicate(UserSimple simple, String field1, String field2, String field3, String field4, String field5) {
        //super(simple.name, simple.email, simple.age, simple.isDeveloper);
        this.userSimple = simple;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
    }
}
