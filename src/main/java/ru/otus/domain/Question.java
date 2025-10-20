package ru.otus.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Question {


    private final String text;


    private final List<String> possibleAnswers;


    private final QuestionType questionType;


    public Question(QuestionType questionType, String text, List<String> possibleAnswers) {
        this.questionType = questionType;
        this.text = text;
        this.possibleAnswers = possibleAnswers;

    }

}
