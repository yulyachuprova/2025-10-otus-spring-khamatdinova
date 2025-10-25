package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.TestConfig;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.domain.TestResult;

import java.util.List;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final IOService ioService;

    private final TestConfig testConfig;


    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("=== Test results ===");
        ioService.printLine("Student: {} ", testResult.getStudent().getFullName());
        ioService.printLine("Score: {}/ {}", testResult.getScore(), testResult.getQuestionCount());
        int passingScore = testResult.getQuestionCount() * testConfig.getMinimumPassPercent() / 100;
        ioService.printLine("Passing score: " + passingScore);
        if (testResult.getScore() >= passingScore) {
            ioService.printLine("Result: PASSED ✓");
        } else {
            ioService.printLine("Result: FAILED ✗");
        }
    }


    private boolean checkSingleChoice(List<Answer> answers, String userAnswer) {
        if (userAnswer.length() != 1) {
            return false;
        }
        int index = userAnswer.charAt(0) - 'a';
        return index >= 0 && index < answers.size() && answers.get(index).isCorrect();
    }

    private boolean checkMultipleChoice(List<Answer> answers, String userAnswer) {
        if (userAnswer.isEmpty()) {
            return false;
        }
        for (int i = 0; i < answers.size(); i++) {
            boolean isSelected = userAnswer.indexOf('a' + i) != -1;
            boolean shouldBeSelected = answers.get(i).isCorrect();
            if (isSelected != shouldBeSelected) {
                return false;
            }
        }

        return true;
    }

    private boolean checkFreeAnswer(List<Answer> answers, String userAnswer) {
        return !answers.isEmpty() &&
                answers.get(0).getText().equalsIgnoreCase(userAnswer.trim());
    }

    @Override

    public boolean checkAnswer(Question question, String userAnswer) {
        List<Answer> answers = question.getPossibleAnswers();
        return switch (question.getQuestionType()) {
            case ONE_ANSWER -> checkSingleChoice(answers, userAnswer);
            case MULTI_ANSWER -> checkMultipleChoice(answers, userAnswer);
            case FREE_ANSWER -> checkFreeAnswer(answers, userAnswer);
            default -> false;
        };
    }
}
