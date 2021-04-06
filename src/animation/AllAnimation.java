/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * An animation that sets all of the tiles in the maze to be the same color.
 * This is useful in clearing the board
 */

package animation;

import javafx.scene.paint.Color;

public class AllAnimation implements Animation{
    // The duration of the animation (in seconds)
    private static final double animTime = 0.1;
    // The color to set every tile to
    private Color color;

    /**
     * An animation that sets the color of every tile in the maze
     * @param color The color to set every tile to
     */
    public AllAnimation(Color color) {
        this.color = color;
    }

    /**
     * Execute the animation for every tile in the maze
     * @param graphicMaze The maze to execute the animations on
     */
    @Override
    public void animate(GraphicMaze graphicMaze) {
        for (int i = 0; i < graphicMaze.getWidth(); i++) {
            for (int j = 0; j < graphicMaze.getWidth(); j++) {
                graphicMaze.get(i, j).setColor(color);
            }
        }
    }

    /**
     * The length of time this animation should take
     * @return The length of time the animation should take
     */
    @Override
    public double getAnimateTime() {
        return animTime;
    }
}
