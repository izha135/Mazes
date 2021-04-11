/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * Holds an animation changing the color of a given tile
 */

package mazesND.animation;

import javafx.scene.paint.Color;
import mazesND.animation.AnimationND;
import mazesND.animation.GraphicMazeND;

import java.util.ArrayList;
import java.util.List;

public class TileAnimationND implements AnimationND {
    // The amount of time the animation should take
    private static double animateTime = 0.1;

    // The row of the tile to change the color of
    private List<Integer> pos;
    // The color to change the tile to
    private Color color;
    private int dimension;

    public TileAnimationND(List<Integer> pos, Color color) {
        this.dimension = pos.size();
        this.pos = new ArrayList<>(pos);
        this.color = color;
    }

    /**
     * Change the color of the specified tile in the given maze
     * @param graphicMaze The maze to modify the specified tile
     */
    @Override
    public void animate(GraphicMazeND graphicMaze) {
        graphicMaze.get(pos).setColor(color);
    }

    /**
     * The length of time this animation should take
     * @return The length of time the animation should take
     */
    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
