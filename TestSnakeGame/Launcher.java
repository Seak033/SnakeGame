import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Launcher extends JFrame {

    JButton startButton;
    JButton highScore;

    public static void main(String[] args) {

        Launcher launcher = new Launcher();
        launcher.homePage();
        
    }

    public void homePage() {
        JFrame frame = new JFrame();
        frame.setTitle("Home - Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);

        ImagePanel backgroundImage = new ImagePanel("C:\\Users\\Sakpo\\Desktop\\Projects\\SnakeGame\\Images\\snake-background.jpg");
        layeredPane.add(backgroundImage, Integer.valueOf(0));
        
        JLabel label = new JLabel();
        label.setText("Welcome to my Snake Game!");
        label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        label.setForeground(Color.decode("#000000"));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setOpaque(false);
        label.setBounds(0, 50, frame.getWidth(), 250);
        label.setLayout(new FlowLayout());
        layeredPane.add(label, Integer.valueOf(1));


        startButton = new CustomButton("Start Game", "C:\\Users\\Sakpo\\Desktop\\Projects\\SnakeGame\\Images\\snake.png");
        layeredPane.add(startButton, Integer.valueOf(1));
        startButton.setBounds(300, 225, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(e -> System.out.println("Let the Snake begin!"));

        highScore = new CustomButton("Highscore", "C:\\Users\\Sakpo\\Desktop\\Projects\\SnakeGame\\Images\\high-score.png");
        layeredPane.add(highScore, Integer.valueOf(1));
        highScore.setBounds(300, 300, 200, 50);
        highScore.setFont(new Font("Arial", Font.BOLD, 20));
        highScore.setFocusable(false);

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                backgroundImage.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });


        frame.setVisible(true);
    }

    static class CustomButton extends JButton {
        private Image image;

        public CustomButton(String text, String imagePath) {
            super(text);
            try {
                this.image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
            }

            setHorizontalAlignment(SwingConstants.RIGHT);
            setHorizontalTextPosition(SwingConstants.LEFT);
            setVerticalAlignment(SwingConstants.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(getHeight(), getHeight(), Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 10, 0, this);
            }
        }
    }

    static class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }


}
