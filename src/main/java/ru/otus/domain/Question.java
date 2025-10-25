package ru.otus.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Question {

    private final int number;


    private final String text;


    private final List<Answer> possibleAnswers;


    private final QuestionType questionType;


    public Question(int number, QuestionType questionType, String text, List<Answer> possibleAnswers) {
        this.number = number;
        this.questionType = questionType;
        this.text = text;
        this.possibleAnswers = possibleAnswers;

    }


}
