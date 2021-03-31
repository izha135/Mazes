package animation;

import javafx.scene.paint.Color;

public class TileAnimation implements Animation{
    private static double animateTime = 0.2;

    private int row;
    private int col;
    private Color color;

    public TileAnimation(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    @Override
    public void animate(GraphicMaze graphicMaze) {
        graphicMaze.get(row, col).setColor(color);
    }

    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
