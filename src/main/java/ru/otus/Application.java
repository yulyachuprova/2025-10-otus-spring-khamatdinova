package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.CSVQuestionService;


public class Application
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        CSVQuestionService questionService = classPathXmlApplicationContext.getBean(CSVQuestionService.class);
        questionService.displayQuestions();


    }
}
