package ru.bass2000.ripper.ScreenSaver;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Service
public abstract class ColorFrame extends JFrame {

    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(getColor());
        repaint();
    }

    protected abstract Color getColor();

}
