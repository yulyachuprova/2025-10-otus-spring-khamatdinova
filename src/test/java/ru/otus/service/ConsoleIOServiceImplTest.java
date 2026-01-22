package ru.otus.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("Тесты сервиса ввода-вывода на консоль")
@ExtendWith(MockitoExtension.class)
class ConsoleIOServiceImplTest {


    @Mock
    private Scanner scanner;

    private ListAppender<ILoggingEvent> listAppender;


    private IOService consoleIOService;


    private static final Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @BeforeEach
    void setUp() {
        consoleIOService = new ConsoleIOServiceImpl(scanner);
        listAppender = (ListAppender<ILoggingEvent>) rootLogger.getAppender("LIST");
        listAppender.list.clear();
    }


    @Test
    @DisplayName("Должен вернуть введенную строку")
    void readLine_ShouldReturnInput() {
        String input = "Hello!";
        Mockito.when(scanner.nextLine()).thenReturn(input);
        String result = consoleIOService.readLine();
        assertEquals(input, result);

    }

    @Test
    @DisplayName("Должен отобразить приглашение и вернуть введенную строку")
    void readLineWithPrompt_ShouldDisplayPromptAndReturnInput() {
        String input = "Ivan";
        String prompt = "Enter your name:";
        Mockito.when(scanner.nextLine()).thenReturn(input);
        String result = consoleIOService.readLineWithPrompt(prompt);
        assertEquals(input, result);
        checkLogs(prompt);

    }


    @Test
    @DisplayName("Должен вывести строку с аргументами")
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