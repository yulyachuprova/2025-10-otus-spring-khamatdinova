package ru.otus.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class ConsoleIOServiceImplTest {


    private final Logger log = (Logger) LoggerFactory.getLogger(ConsoleIOServiceImpl.class);
    @Mock
    private Scanner scanner;

    private ListAppender<ILoggingEvent> listAppender;


    private IOService consoleIOService;

    @BeforeEach
    public void setUp() {
        consoleIOService = new ConsoleIOServiceImpl(scanner);
        listAppender = (ListAppender<ILoggingEvent>) log.getAppender("LIST");
        listAppender.list.clear();
    }


    @Test
    void readLine_ShouldReturnInput() {
        String input = "Hello!";
        Mockito.when(scanner.nextLine()).thenReturn(input);
        String result = consoleIOService.readLine();
        assertEquals(input, result);

    }

    @Test
    void readLineWithPrompt_ShouldDisplayPromptAndReturnInput() {
        String input = "Ivan";
        String prompt = "Enter your name:";
        Mockito.when(scanner.nextLine()).thenReturn(input);
        String result = consoleIOService.readLineWithPrompt(prompt);
        assertEquals(input, result);
        checkLogs(prompt);

    }


    @Test
    void testPrintLine() {

        String line = "line";
        String args = "args";
        consoleIOService.printLine("line {}", "args");
        checkLogs(line +" "+ args);

    }

    private  void checkLogs(String resultMessage){
        List<ILoggingEvent> logs = listAppender.list;
        assertFalse(logs.isEmpty(), "Should have logged messages");
        assertEquals(resultMessage, logs.get(0).getFormattedMessage());
    }
}