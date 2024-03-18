package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {          //basic syntax, is used to make sure game runs single thread preventing conflict
            TopClass tc = new TopClass();              //obj for top class, init all
            tc.buildFrame();                            //making the game properties
            new Thread(tc::startGame).start();          //assigns thread
        });
    }
}

class TopClass {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private JFrame f = new JFrame("Flappy Bird");
    public JPanel topPanel;
    private Bird bird;
    private List<Obstacle> obstacles = new ArrayList<>();       //obstacle array
    private int score = 0;
    private boolean gameOver = false;

    void buildFrame() {
        f.setContentPane(createContentPane());              //creates pane
        f.setResizable(true);                               //allows changes in size by user
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //prog termination when closed window
        f.setVisible(true);                                      // without this screen may not be visible
        f.setMinimumSize(new Dimension(SCREEN_WIDTH * 1 / 4, SCREEN_HEIGHT * 1 / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);          //alows maximising
    }

    private JPanel createContentPane() {
        topPanel = new JPanel() {                                   //infers top class

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Obstacle obstacle : obstacles) {
                    g.setColor(Color.GREEN);
                    g.fillRect(obstacle.getX(), 0, obstacle.getWidth(), obstacle.getHeight());
                    g.fillRect(obstacle.getX(), obstacle.getHeight() + obstacle.getGap(), obstacle.getWidth(), SCREEN_HEIGHT);
                }
                g.setColor(Color.BLUE);
                g.fillRect(100, bird.getY(), 50, 50);
            }
        };
        return topPanel;
    }

    void startGame() {
        bird = new Bird();
        topPanel.add(bird);
        f.addKeyListener(new BirdInputs(bird));                     //asign components

        while (!gameOver) {
            try {
                Thread.sleep(50);                       //basic end command for thread manipulation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bird.move();
            createObstacle();
            moveObstacles();
            checkCollision();
            updateScore();
            topPanel.repaint();
        }

        JOptionPane.showMessageDialog(f, "Game Over! Your Score: " + score);
    }

    private void createObstacle() {
        if (Math.random() < 0.025) {            //0.025 probability to spawn
            obstacles.add(new Obstacle());
        }
    }

    private void moveObstacles() {
        for (Obstacle obstacle : obstacles) {
            obstacle.move();                                //obstacles are spawned drawn and moved
        }
        obstacles.removeIf(obstacle -> obstacle.getX() < -100);         //this clears the obstacles on the right to be drawn on left
    }

    private void checkCollision() {
        for (Obstacle obstacle : obstacles) {           //boundary conditions
            Rectangle birdBounds = bird.getBounds();
            Rectangle upperObstacleBounds = obstacle.getUpperBounds();
            Rectangle lowerObstacleBounds = obstacle.getLowerBounds();

            if (birdBounds.intersects(upperObstacleBounds) || birdBounds.intersects(lowerObstacleBounds) || bird.getY() >= SCREEN_HEIGHT) {
                gameOver = true;
                return;
            }
        }
    }


    private void updateScore() {
        for (Obstacle obstacle : obstacles) {                   //
            if (obstacle.getX() + obstacle.getWidth() < bird.getX() && !obstacle.isCounted()) {
                score++;
                obstacle.setCounted(true);
            }
        }
    }
}

class Bird extends JPanel {
    private int y = 200;
    private int dy = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(100, y, 50, 50);
    }

    void jump() {
        dy = -10;
    }

    void move() {
        y += dy;
        dy += 1;
        if (y < 0)
            y = 0;
        else if (y > 700)
            y = 700;
        repaint();
    }

    public int getX() {
        return 100;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(100, y, 50, 50);
    }
}

class BirdInputs implements KeyListener {
    private Bird bird;

    public BirdInputs(Bird bird) {
        this.bird = bird;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {               //only when released it is used
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.jump();
        }
    }
}

class Obstacle{
    private int x = 1500;
    private int gap = 200;
    private int height = 100;
    private int width = 100;
    private int speed = -20;
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private boolean counted = false;

    Rectangle getUpperBounds() {
        return new Rectangle(x, 0, width, height);
    }

    Rectangle getLowerBounds() {
        return new Rectangle(x, height + gap, width, SCREEN_HEIGHT - (height + gap));
    }

    public Obstacle() {
        Random rand = new Random();
        gap = rand.nextInt(300) + 100;
        height = rand.nextInt(400) + 50;
    }

    void move() {
        x += speed;
    }

    int getX() {
        return x;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getGap() {
        return gap;
    }

    boolean isCounted() {
        return counted;
    }

    void setCounted(boolean counted) {
        this.counted = counted;
    }

}
