import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        game.requestFocus();
    }

}