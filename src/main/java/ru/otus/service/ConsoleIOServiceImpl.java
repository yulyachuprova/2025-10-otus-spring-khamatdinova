package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
public class ConsoleIOServiceImpl implements IOService {

    private final Scanner scanner;

    public ConsoleIOServiceImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public String readLineWithPrompt(String prompt) {
        log.info(prompt);
        return readLine();
    }

    @Override
    public void printLine(String line, Object ...args) {
        log.info(line,args);
    }
}
