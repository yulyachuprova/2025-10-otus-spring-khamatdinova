package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionType;
import ru.otus.domain.Student;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Тесты сервиса выполнения тестирования")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    @Mock
    private QuestionService questionService;
    @Mock
    private StudentService studentService;
    @Mock
    private IOService ioService;
    @Mock
    private ResultService resultService;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void executeTest_ShouldPass() {
        Student student = new Student("Ivan", "Ivanov");
        when(studentService.getStudentInfo()).thenReturn(student);
        Question question = new Question(1, QuestionType.ONE_ANSWER, "Which company developed the Java programming language?", new ArrayList<>());
        when(questionService.getQuestions()).thenReturn(List.of(question));
        when(ioService.readLine()).thenReturn("a");
        when(resultService.checkAnswer(question, "a")).thenReturn(true);

        testService.executeTest();

        verify(ioService, times(1)).printLine("Hello, {}!", student.getFullName());
        verify(questionService, times(1)).displayQuestion(question);
        verify(ioService, times(1)).printLine("Your answer is correct!\n");


    }

    @Test
    void executeTest_ShouldFail() {
        Student student = new Student("Ivan", "Ivanov");
        when(studentService.getStudentInfo()).thenReturn(student);
        Question question = new Question(1, QuestionType.ONE_ANSWER, "Which company developed the Java programming language?", new ArrayList<>());
        when(questionService.getQuestions()).thenReturn(List.of(question));
        when(ioService.readLine()).thenReturn("a");
        when(resultService.checkAnswer(question, "a")).thenReturn(false);

        testService.executeTest();

        verify(ioService, times(1)).printLine("Hello, {}!", student.getFullName());
        verify(questionService, times(1)).displayQuestion(question);
        verify(ioService, times(1)).printLine("Your answer is incorrect!\n");


    }
}