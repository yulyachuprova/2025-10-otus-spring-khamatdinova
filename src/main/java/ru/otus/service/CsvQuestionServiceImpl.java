package ru.otus.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.otus.config.TestConfig;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionType;
import ru.otus.exception.QuestionLoadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
public class CsvQuestionServiceImpl implements QuestionService {

    private final TestConfig testConfig;


    private final IOService ioService;

    private final ResourceLoader    resourceLoader;


    @Override
    public List<Question> getQuestions() {
        Resource resource = resourceLoader.getResource(testConfig.getTestFileName());
        if(resource == null)  throw new QuestionLoadException("File does not exist");
        List<Question> questions = null;
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            questions = reader.readAll().stream()
                    .filter(row -> row != null && row.length >= 2)
                    .map(row ->
                            new Question(1, QuestionType.valueOf(row[0]), row[1], Arrays.asList(row).subList(2, row.length).stream().map(Answer::new).toList())).toList();
        } catch (IOException | CsvException e) {
            throw new QuestionLoadException("Error reading CSV", e);

        }
        return questions;

    }

    @Override
    public void displayQuestions() {
        List<Question> questions = getQuestions();
        if (questions != null) {
            for (Question question : questions) {
                displayQuestion(question);
            }
        }
    }

    @Override
    public void displayQuestion(Question question) {
        ioService.printLine("{}.{} ", question.getNumber(), question.getText());
        switch (question.getQuestionType()) {
            case ONE_ANSWER -> displayPossibleAnswers(question, "Choose one letter (a, b, c, etc.):");
            case MULTI_ANSWER -> displayPossibleAnswers(question, "Choose multiple letters (e.g., ab, acd):");
            case FREE_ANSWER -> ioService.printLine("Please enter your answer: ____________");
        }
    }

    private void displayPossibleAnswers(Question question, String instruction) {
        ioService.printLine( instruction);
        for (int j = 0; j < question.getPossibleAnswers().size(); j++) {
            ioService.printLine("   {}){}", (char) ('a' + j), question.getPossibleAnswers().get(j).getClearText());
        }
    }


}
