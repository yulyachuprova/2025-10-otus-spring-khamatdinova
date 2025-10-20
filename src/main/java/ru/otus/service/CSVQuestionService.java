package ru.otus.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionType;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CSVQuestionService implements QuestionService {




    @Setter
    private Resource resource;

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = null;
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            questions= reader.readAll().stream()
                    .filter(row -> row != null && row.length >= 2)
                    .map(row -> new Question(QuestionType.valueOf(row[0]), row[1], Arrays.asList(row).subList(2, row.length)))
                    .collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            log.error("Error reading CSV", e);

        }
        return  questions;

    }

    @Override
    public void displayQuestions() {
        List<Question> questions = getQuestions();
        if(questions!=null) {
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                log.info("{}.{} ", (i + 1), question.getText());
                switch (question.getQuestionType()) {
                    case ONE_ANSWER:
                        displayAnswer(question, "Choose one answer:");
                        break;
                    case MULTI_ANSWER:
                        displayAnswer(question, "Choose multiple answers");
                        break;
                    case FREE_ANSWER:
                        log.info("   Please enter your answer: ____________");
                        break;
                }
            }
        }
    }

    private void displayAnswer(Question question, String instruction) {
        log.info("   {}",instruction);
        for (int j = 0; j < question.getPossibleAnswers().size(); j++) {
            log.info("   {}){}" , (char) ('a' + j) ,question.getPossibleAnswers().get(j));
        }
    }


}
