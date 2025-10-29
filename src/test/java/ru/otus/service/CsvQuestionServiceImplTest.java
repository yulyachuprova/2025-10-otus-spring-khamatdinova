package ru.otus.service;


import junit.framework.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.config.AppConfig;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionType;
import ru.otus.exception.QuestionLoadException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvQuestionServiceImplTest {

    @Mock
    private AppConfig appConfig;


    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private CsvQuestionServiceImpl csvQuestionService;

    @Mock
    private Resource resource;

    @Mock
    private IOService consoleIOService;


    @BeforeEach
    public void setUp()  {
        String fileName = "exam-questions.csv";
        when(appConfig.getTestFileName()).thenReturn(fileName);

    }


    private void setUpWithCorrectFile() throws IOException {
        String csvContent = "ONE_ANSWER,Which company developed the Java programming language?,Sun Microsystems*,Microsoft,Google,Oracle\n" +
                "ONE_ANSWER,What does CPU stand for?,Central Processing Unit*,Computer Processing Unit,Central Processor Unit\n" +
                "MULTI_ANSWER,Which of these are database management systems?,MySQL*,PostgreSQL*,MongoDB*,Excel,Word\n" +
                "FREE_ANSWER,Explain what is Cloud Computing:,\n" +
                "ONE_ANSWER,What year was the first iPhone released?,2007*,2005,2008,2010";
        when(resourceLoader.getResource(appConfig.getTestFileName())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));
    }

    @Test
    void getQuestions_ShouldReturnQuestions() throws IOException {
        setUpWithCorrectFile();

        List<Question> questions = csvQuestionService.getQuestions();
        Assertions.assertThat(questions).isNotNull().hasSize(5);
        Question firstQuestion = questions.get(0);
        Assert.assertEquals(QuestionType.ONE_ANSWER, firstQuestion.getQuestionType());
        Assert.assertEquals("Which company developed the Java programming language?", firstQuestion.getText());
        Assert.assertEquals(4, firstQuestion.getPossibleAnswers().size());

    }

    @Test
    void getQuestions_ShouldThrowException_WhenFileDoesNotExist() {
        when(resourceLoader.getResource(appConfig.getTestFileName())).thenReturn(null);
        assertThrows(QuestionLoadException.class, () -> csvQuestionService.getQuestions());

    }

    @Test
    void getQuestions_ShouldThrowException_WhenFileReadingError() throws IOException {
        when(resourceLoader.getResource(appConfig.getTestFileName())).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new IOException());
        assertThrows(QuestionLoadException.class, () -> csvQuestionService.getQuestions());

    }

    @Test
    void displayQuestion_ShouldDisplayOneAnswerQuestion() throws IOException {
        displayQuestion_ShouldDisplayQuestion(0, "Choose one letter (a, b, c, etc.):");


    }

    @Test
    void displayQuestions() throws IOException {
        setUpWithCorrectFile();
        csvQuestionService.displayQuestions();
        verify(consoleIOService, times(1)).printLine("{}.{} ", 1, "Which company developed the Java programming language?");


    }

    private void displayQuestion_ShouldDisplayQuestion(int questionIndex, String questionPrompt) throws IOException {
        setUpWithCorrectFile();
        List<Question> questions = csvQuestionService.getQuestions();
        Question question = questions.get(questionIndex);
        csvQuestionService.displayQuestion(question);
        verify(consoleIOService, times(1)).printLine("{}.{} ", question.getNumber(), question.getText());
        verify(consoleIOService, times(1)).printLine(questionPrompt);
    }

    @Test
    void displayQuestion_ShouldDisplayMultiAnswerQuestion() throws IOException {
        displayQuestion_ShouldDisplayQuestion(2, "Choose multiple letters (e.g., ab, acd):");

    }

    @Test
    void displayQuestion_ShouldDisplayFreeAnswerQuestion() throws IOException {
        displayQuestion_ShouldDisplayQuestion(3, "Please enter your answer: ____________");

    }



}