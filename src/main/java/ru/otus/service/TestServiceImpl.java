package ru.otus.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Question;
import ru.otus.domain.Student;
import ru.otus.domain.TestResult;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {


    private final QuestionService questionService;

    private final StudentService studentService;

    private final IOService ioService;

    private final ResultService resultService;


    @Override
    public void executeTest() {
        Student student = studentService.getStudentInfo();
        ioService.printLine("Hello, {}!", student.getFullName());
        TestResult testResult = runTest(student);
        resultService.showResult(testResult);

    }


    private TestResult runTest(Student student) {
        List<Question> questions = questionService.getQuestions();
        int score = 0;
        for (Question question : questions) {
            questionService.displayQuestion(question);
            if (askQuestion(question)) {
                ioService.printLine("Your answer is correct!\n");
                score++;
            } else {
                ioService.printLine("Your answer is incorrect!\n");
            }
        }

        return new TestResult(student, score, questions.size());


    }

    private boolean askQuestion(Question question) {
        String userAnswer = ioService.readLine().trim().toLowerCase();
        return resultService.checkAnswer(question, userAnswer);
    }


}
