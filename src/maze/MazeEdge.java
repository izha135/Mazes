package maze;

public class MazeEdge {
    private int row, col;
    private boolean isSouth;

    public MazeEdge(int row, int col, boolean isSouth) {
        this.row = row;
        this.col = col;
        this.isSouth = isSouth;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isSouth() {
        return isSouth;
    }
}
