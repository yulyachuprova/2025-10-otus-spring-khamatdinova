package ru.otus.service;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.config.AppConfig;
import ru.otus.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {


    @Mock
    private IOService ioService;

    @InjectMocks
    private ResultServiceImpl resultService;

    @Spy
    private AppConfig appConfig = new AppConfig(90, "exam-questions.csv");


    @Test
    void showResult_ShouldPassExam() {
        TestResult testResult = new TestResult(new Student("Ivan", "Ivanov"), 9, 10);


        resultService.showResult(testResult);

        verify(ioService, times(1)).printLine("=== Test results ===");
        verify(ioService, times(1)).printLine("Student: {} ", "Ivan Ivanov");
        verify(ioService, times(1)).printLine("Score: {}/ {}", 9, 10);
        verify(ioService, times(1)).printLine("Passing score: 9");
        verify(ioService, times(1)).printLine("Result: PASSED ✓");

    }

    @Test
    void showResult_ShouldFailExam() {
        TestResult testResult = new TestResult(new Student("Ivan", "Ivanov"), 5, 10);

        resultService.showResult(testResult);

        verify(ioService, times(1)).printLine("=== Test results ===");
        verify(ioService, times(1)).printLine("Student: {} ", "Ivan Ivanov");
        verify(ioService, times(1)).printLine("Score: {}/ {}", 5, 10);
        verify(ioService, times(1)).printLine("Passing score: 9");
        verify(ioService, times(1)).printLine("Result: FAILED ✗");


    }

    @Test
    void checkAnswer_ShouldReturnTrue_WhenGivenCorrectOneAnswer() {
        Question question = getQuestionWithOneAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "a");
        Assert.assertTrue(result);
    }

    @Test
    void checkAnswer_ShouldReturnFalse_WhenGivenIncorrectOneAnswer() {
        Question question = getQuestionWithOneAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "b");
        Assert.assertFalse(result);
    }

    @Test
    void checkAnswer_ShouldReturnFalse_WhenGivenSeveralAnswerOnOneAnswerQuestionType() {
        Question question = getQuestionWithOneAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "ab");
        Assert.assertFalse(result);
    }

    @Test
    void checkAnswer_ShouldReturnTrue_WhenGivenCorrectMultiAnswer() {
        Question question = getQuestionWithMultiAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "abc");
        Assert.assertTrue(result);
    }

    @Test
    void checkAnswer_ShouldReturnFalse_WhenGivenEmptyMultiAnswer() {
        Question question = getQuestionWithMultiAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "");
        Assert.assertFalse(result);
    }


    @Test
    void checkAnswer_ShouldReturnFalse_WhenGivenIncorrectMultiAnswer() {
        Question question = getQuestionWithMultiAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "b");
        Assert.assertFalse(result);
    }

    @Test
    void checkAnswer_ShouldReturnTrue_WhenGivenCorrectFreeAnswer() {
        Question question = getQuestionWithFreeAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "");
        Assert.assertTrue(result);
    }

    @Test
    void checkAnswer_ShouldReturnFalse_WhenGivenIncorrectFreeAnswer() {
        Question question = getQuestionWithFreeAnswerQuestionType();
        boolean result = resultService.checkAnswer(question, "Answer");
        Assert.assertFalse(result);
    }

    private Question getQuestionWithOneAnswerQuestionType() {
        List<Answer> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(new Answer("Sun Microsystems*"));
        possibleAnswers.add(new Answer("Microsoft"));
        possibleAnswers.add(new Answer("Google"));
        possibleAnswers.add(new Answer("Oracle"));
        return new Question(1, QuestionType.ONE_ANSWER, "Which company developed the Java programming language?", possibleAnswers);

    }

    private Question getQuestionWithMultiAnswerQuestionType() {
        List<Answer> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(new Answer("MySQL*"));
        possibleAnswers.add(new Answer("PostgreSQL*"));
        possibleAnswers.add(new Answer("MongoDB*"));
        possibleAnswers.add(new Answer("Excel"));
        possibleAnswers.add(new Answer("Word"));
        return new Question(1, QuestionType.MULTI_ANSWER, "Which of these are database management systems?", possibleAnswers);

    }

    private Question getQuestionWithFreeAnswerQuestionType() {
        List<Answer> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(new Answer(""));
        return new Question(1, QuestionType.FREE_ANSWER, "Explain what is Cloud Computing?", possibleAnswers);

    }
}