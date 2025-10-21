package ru.otus.domain;

import lombok.Getter;

@Getter
public class TestResult {
   private Student student;
   private int score;
   private int questionCount;

    public TestResult(Student student, int score, int questionCount) {
        this.student = student;
        this.score = score;
        this.questionCount = questionCount;
    }
}
