package mazesND.animation;

import javafx.scene.paint.Color;

import java.util.List;

public class AllAnimationND implements AnimationND{
    private final double ANIMATE_TIME = 0.1;
    private Color color;

    public AllAnimationND(Color color) {
        this.color = color;
    }

    @Override
    public void animate(GraphicMazeND graphicMaze) {
        List<GraphicMazeND.GraphicTile> tileList = graphicMaze.collapseMaze();
        for(GraphicMazeND.GraphicTile tile : tileList) {
            tile.setColor(color);
        }
    }

    @Override
    public double getAnimateTime() {
        return ANIMATE_TIME;
    }
}
