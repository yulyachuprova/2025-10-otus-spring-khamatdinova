package ru.otus.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig implements TestConfig {


    private final int minimumPassPercent;


    private final String testFileName;

    public AppConfig(@Value("${exam.settings.minimum-pass-percent:90}") int minimumPassPercent, @Value("${exam.questions.file.name:exam-questions.csv}") String testFileName) {
        this.minimumPassPercent = minimumPassPercent;
        this.testFileName = testFileName;
    }
}
