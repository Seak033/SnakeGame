import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    private static final int gameWidth = 560;
    private static final int gameHeight = 520;
    private static final int x1 = 50;
    private static final int y1 = 40;
    private static int tileSize = 20; // this is the number of squares within the game area

    public class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Snake (start)
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food logic
    Tile food;
    Random random;

    // Game logic
    Timer gameLoop;
    int directionX; // Snake speeds X
    int directionY; // Snake speeds Y
    boolean gameOver = false;
    boolean gameStarted = false;

    public SnakeGame() {
        super();
        setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        directionX = 1;
        directionY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Game area
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x1, y1, gameWidth, gameHeight);

        // Grid lines
        // g2d.setColor(Color.LIGHT_GRAY);
        // for (int i = 0; i < gameWidth / tileSize; i++) {
        // g.drawLine(x1 + i * tileSize, y1, x1 + i * tileSize, y1 + gameHeight);
        // }

        // for (int i = 0; i < gameHeight / tileSize; i++) {
        // g.drawLine(x1, y1 + i * tileSize, x1 + gameWidth, y1 + i * tileSize);
        // }

        // Food
        g2d.setColor(Color.RED);
        g2d.fillRect(x1 + food.x * tileSize, y1 + food.y * tileSize, tileSize, tileSize);

        // Snake head
        g2d.setColor(Color.BLUE);
        g2d.fill3DRect(x1 + snakeHead.x * tileSize, y1 + snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g2d.fill3DRect(x1 + snakePart.x * tileSize, y1 + snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Game over pop-up screen
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 50));
            String gameOverText = "Game Over";
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(gameOverText)) / 2;
            int y = DEFAULT_SCREEN_HEIGHT / 3;
            g2d.drawString(gameOverText, x, y);

            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            String restartText = "Press 'R' to restart";
            metrics = g2d.getFontMetrics();
            x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(restartText)) / 2;
            y = DEFAULT_SCREEN_HEIGHT / 2;
            g2d.drawString(restartText, x, y);
        } 

        // highscore 
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + String.valueOf(snakeBody.size()), (DEFAULT_SCREEN_WIDTH) / 10, DEFAULT_SCREEN_HEIGHT - y1); // this shows the score while playing
        

        if (!gameStarted) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            String startMessage = "Press 'SPACE' to start";
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(startMessage)) / 2;
            int y = DEFAULT_SCREEN_HEIGHT / 2;
            g2d.drawString(startMessage, x, y);
        }
    }

    // Generates random food on the game area
    public void placeFood() {
        food.x = random.nextInt(gameWidth / tileSize);
        food.y = random.nextInt(gameHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // When the snake eats food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // move Snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake head
        snakeHead.x += directionX;
        snakeHead.y += directionY;

        // Game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Snake eats itself
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= gameWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize >= gameHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            move();
            repaint();
            if (gameOver) {
                gameLoop.stop();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {   
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameLoop.start();
            gameStarted = true;
            return; // Don't process any other key press until the game starts
        }
        
        if (gameStarted) {
            if (e.getKeyCode() == KeyEvent.VK_UP && directionY != 1) {
                directionX = 0;
                directionY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && directionY != -1) {
                directionX = 0;
                directionY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && directionX != 1) {
                directionX = -1;
                directionY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && directionX != -1) {
                directionX = 1;
                directionY = 0;
            }
        }
           

        if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            gameOver = false;
            snakeHead = new Tile(5, 5);
            snakeBody.clear();
            placeFood();
            directionX = 1;
            directionY = 0;
            gameLoop.start();
        }

    }

    // NOT NEEDED
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
