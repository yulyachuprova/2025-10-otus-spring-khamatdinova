package ru.otus.service;

import ru.otus.domain.Question;
import ru.otus.domain.TestResult;

public interface ResultService {
    void showResult(TestResult testResult);


    boolean checkAnswer(Question question, String userAnswer);
}
