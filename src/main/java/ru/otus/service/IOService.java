package ru.otus.service;

public interface IOService {
    String readLine();

    String readLineWithPrompt(String prompt);


    void printLine(String line, Object... args);
}
