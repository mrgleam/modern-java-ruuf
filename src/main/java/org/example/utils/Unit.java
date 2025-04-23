package org.example.utils;

public final class Unit {
    public static final Unit INSTANCE = new Unit();

    private Unit() {} // private constructor

    @Override
    public String toString() {
        return "Unit";
    }
}
