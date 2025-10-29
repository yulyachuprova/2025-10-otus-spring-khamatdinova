package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private IOService inputService;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void getStudentInfo_ShouldReturnStudentInfo() {
        Mockito.when(inputService.readLineWithPrompt(Mockito.anyString())).thenReturn("Ivan").thenReturn("Ivanov");
        Student studentInfo = studentService.getStudentInfo();
        assertEquals("Ivan Ivanov", studentInfo.getFullName());
    }

    @Test
    void getStudentInfo_ShouldThrowExceptionWhenNameOrLastNameIsEmpty() {
        Mockito.when(inputService.readLineWithPrompt(Mockito.anyString())).thenReturn("").thenReturn("");
        assertThrows(IllegalArgumentException.class, ()->studentService.getStudentInfo());
    }
}