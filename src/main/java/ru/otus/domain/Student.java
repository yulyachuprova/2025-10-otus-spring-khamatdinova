package ru.otus.domain;

import lombok.Getter;

@Getter
public class Student {
    private final String name;
    private final String lastName;

    public Student(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", name, lastName);
    }
}
