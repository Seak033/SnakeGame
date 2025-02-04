
import java.awt.Graphics;
import javax.swing.*;

public class GameBoard extends JPanel {

  private static final long serialVersionUID = 1L;

  static final int originX = 73;
  static final int originY = 97;
  static final int cellSize = 20;

  public static void main(String[] args) {
    SnakeBoard board = new SnakeBoard();
    System.out.println(board);

    GameBoard gameBoard = new GameBoard();
    JFrame frame = new JFrame();
    frame.add(gameBoard);
    frame.setTitle("Snake Game");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);

    frame.setVisible(true);

  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    for (int i = 0; i < SnakeBoard.rows + 1; i++) {
      g.drawLine(originX, originY + i * cellSize, originX + SnakeBoard.cols * cellSize, originY + i * cellSize);
    }

    for (int i = 0; i < SnakeBoard.cols + 1; i++) {
      g.drawLine(originX + i * cellSize, originY, originX + i * cellSize, originY + SnakeBoard.rows * cellSize);
    }

  }

}
