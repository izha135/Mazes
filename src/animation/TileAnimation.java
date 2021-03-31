/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * Holds an animation changing the color of a given tile
 */

package animation;

import javafx.scene.paint.Color;

public class TileAnimation implements Animation{
    // The amount of time the animation should take
    private static double animateTime = 0.1;

    // The row of the tile to change the color of
    private int row;
    // The column of the tile to change the color of
    private int col;
    // The color to change the tile to
    private Color color;

    /**
     * Create a tile-color changing animation
     * @param row The index of the row
     * @param col The index of the column
     * @param color The color to change the tile to
     */
    public TileAnimation(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    /**
     * Change the color of the specified tile in the given maze
     * @param graphicMaze The maze to modify the specified tile
     */
    @Override
    public void animate(GraphicMaze graphicMaze) {
        graphicMaze.get(row, col).setColor(color);
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
