public class SnakeBoard {

    static final int cols = 20;
    static final int rows = 20;
    
    @Override
    public String toString() {
        String brdstr = "";

        for (int row = 0; row < SnakeBoard.rows; row++) {
            brdstr += ".";
            for (int col = 0; col < SnakeBoard.cols; col++) {
                brdstr += ".";
            }
            brdstr += ".\n";
        }
    
        

        return brdstr;
    }
}
