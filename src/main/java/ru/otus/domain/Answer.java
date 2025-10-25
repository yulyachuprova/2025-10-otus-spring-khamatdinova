package ru.otus.domain;

import lombok.Getter;

@Getter
public class Answer {

    private final String text;

    public Answer(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return getText().endsWith("*");
    }

    public String getClearText() {
        return isCorrect() ? getText().substring(0, getText().length() - 1) : getText();
    }
}
