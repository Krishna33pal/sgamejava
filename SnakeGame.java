import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 75;
    private final ArrayList<Integer> snakeX = new ArrayList<>();
    private final ArrayList<Integer> snakeY = new ArrayList<>();
    private int foodX;
    private int foodY;
    private int score = 0;
    private boolean isMoving = false;
    private Direction direction = Direction.RIGHT;
    private boolean gameOver = false;
    private final Timer timer;
    private Random random;

    public SnakeGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        snakeX.add(WIDTH / 2);
        snakeY.add(HEIGHT / 2);

        placeFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void placeFood() {
        foodX = random.nextInt((WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = snakeX.size() - 1; i > 0; i--) {
            snakeX.set(i, snakeX.get(i - 1));
            snakeY.set(i, snakeY.get(i - 1));
        }

        switch (direction) {
            case UP:
                snakeY.set(0, snakeY.get(0) - UNIT_SIZE);
                break;
            case DOWN:
                snakeY.set(0, snakeY.get(0) + UNIT_SIZE);
                break;
            case LEFT:
                snakeX.set(0, snakeX.get(0) - UNIT_SIZE);
                break;
            case RIGHT:
                snakeX.set(0, snakeX.get(0) + UNIT_SIZE);
                break;
        }
    }

    public void checkFoodCollision() {
        if (snakeX.get(0) == foodX && snakeY.get(0) == foodY) {
            snakeX.add(foodX);
            snakeY.add(foodY);
            score++;
            placeFood();
        }
    }

    public void checkCollision() {
        for (int i = 1; i < snakeX.size(); i++) {
            if (snakeX.get(0) == snakeX.get(i) && snakeY.get(0) == snakeY.get(i)) {
                gameOver = true;
                timer.stop();
            }
        }

        if (snakeX.get(0) < 0 || snakeX.get(0) >= WIDTH || snakeY.get(0) < 0 || snakeY.get(0) >= HEIGHT) {
            gameOver = true;
            timer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (!gameOver) {
            for (int i = 0; i < snakeX.size(); i++) {
                g.setColor(Color.green);
                g.fillRect(snakeX.get(i), snakeY.get(i), UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Score: " + score, 30, 40);
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 75));
            g.drawString("Game Over", 150, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            isMoving = true;
            move();
            checkFoodCollision();
            checkCollision();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            }
            if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
            if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            }
            if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
