package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

abstract class InitAll {
    int x, y, height, width;
    int space_bar = 0;
}


  public class TopClass {
    //global constant variables
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    //global variables
    //global swing objects
    private JFrame f = new JFrame("Flappy Bird Redux");

    public Jpanel topPanel;

    //other global objects
    private static TopClass tc = new TopClass();

    /**
     * Default constructor
     */
    public TopClass() {
    }

    /**
     * Main executable method invoked when running .jar file
     * @param args
     */
    public static void main(String[] args) {
        //build the GUI on a new thread

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tc.buildFrame();
                //create a new thread to keep the GUI responsive while the game runs
                Thread t = new Thread() {
                    public void run() {
                        //in here we will call a function to start the game
                    }
                };
                t.start();
            }
        });
    }

    /**
     * Method to construct the JFrame and add the program content
     */
    private void buildFrame() {
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/blue_bird.png"));
        f.setContentPane(createContentPane()); //adds the main content to the frame
        f.setResizable(true); //true, but game will not function properly unless maximized!
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setAlwaysOnTop(false);
        f.setVisible(true);
        f.setMinimumSize(new Dimension(SCREEN_WIDTH*1/4, SCREEN_HEIGHT*1/4)); //set to prevent collapse to tiny window upon resizing
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize the JFrame
        f.setIconImage(icon); //set the icon
    }

    private JPanel createContentPane() {
        topPanel = new JPanel(); //top-most JPanel in layout hierarchy
        return topPanel; //return a blank panel
    }
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
