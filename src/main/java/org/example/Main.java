package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

abstract class InitAll {
    int x, y, height, width;
    int space_bar = 0;
}

class Bird extends InitAll {
    void jump(){
        // Implement the logic for bird jumping here
    }
}

class BirdInputs extends InitAll implements KeyListener {
    static JTextField text = new JTextField();
    private Bird bird; // Add a reference to Bird

    public BirdInputs(Bird bird) {
        this.bird = bird; // Initialize the reference to Bird
    }

    // Other methods remain unchanged

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            bird.jump(); // Call jump() method on the instance of Bird
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Bird bird = new Bird(); // Create an instance of Bird
        BirdInputs birdInputs = new BirdInputs(bird); // Pass the Bird instance to BirdInputs
    }
}
