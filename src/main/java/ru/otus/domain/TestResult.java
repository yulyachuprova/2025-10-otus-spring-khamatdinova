package ru.otus.domain;

import lombok.Getter;

@Getter
public class TestResult {
    private final Student student;
    private final int score;
    private final int questionCount;

    public TestResult(Student student, int score, int questionCount) {
        this.student = student;
        this.score = score;
        this.questionCount = questionCount;
    }
}
