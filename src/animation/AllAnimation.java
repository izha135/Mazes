package animation;

import javafx.scene.paint.Color;

public class AllAnimation implements Animation{
    private static final double animTime = 0.1;
    private Color color;

    public AllAnimation(Color color) {
        this.color = color;
    }

    @Override
    public void animate(GraphicMaze graphicMaze) {
        for (int i = 0; i < graphicMaze.getWidth(); i++) {
            for (int j = 0; j < graphicMaze.getWidth(); j++) {
                graphicMaze.get(i, j).setColor(color);
            }
        }
    }

    @Override
    public double getAnimateTime() {
        return animTime;
    }
}
