package animation;

import javafx.scene.paint.Color;

public class EdgeAnimation implements Animation{
    private static double animateTime = 0.2;

    private int row;
    private int col;
    private boolean toTheSouth; // false means it is to the east
    private Color color;

    public EdgeAnimation(int row, int col, boolean toTheSouth, Color color) {
        this.row = row;
        this.col = col;
        this.toTheSouth = toTheSouth;
        this.color = color;
    }

    @Override
    public void animate(GraphicMaze graphicMaze) {
        if(toTheSouth) {
            graphicMaze.get(row, col).setSouthColor(color);
            graphicMaze.get(row+1, col).setNorthColor(color);
        }
        else {
            graphicMaze.get(row, col).setEastColor(color);
            graphicMaze.get(row, col+1).setWestColor(color);
        }
    }

    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
