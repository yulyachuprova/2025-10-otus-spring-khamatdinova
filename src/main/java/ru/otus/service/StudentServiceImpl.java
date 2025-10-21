package ru.otus.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.domain.Student;


@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final IOService inputService;


    @Override
    public Student getStudentInfo() {
        String name = inputService.readLineWithPrompt("Enter your name:");
        String lastName = inputService.readLineWithPrompt("Enter your lastname:");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("Name and lastname cannot be empty");
        }
        return new Student(name, lastName);


    }

}
