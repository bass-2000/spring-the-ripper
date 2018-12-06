package ru.bass2000.ripper.ScreenSaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "ru.bass2000.ripper.ScreenSaver")
public class Config {

    public static void main(String[] args) throws InterruptedException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(100);
        }
    }

    @Bean
    @Scope("periodical")
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255)
                , random.nextInt(255)
                , random.nextInt(255));
    }

    @Bean
    public ColorFrame colorFrame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }
}