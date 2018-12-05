package ru.bass2000.ripper.quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(Quoter.class).sayQuote();
    }
}