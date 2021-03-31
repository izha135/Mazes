/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * Holds an animation changing the color of a given edge
 */

package animation;

import javafx.scene.paint.Color;

public class EdgeAnimation implements Animation{
    // The amount of time the animation should take
    private static double animateTime = 0.1;

    // The row of the relevant cell
    private int row;
    // The column of the relevant cell
    private int col;
    // true  means the relevant edge is to the south of the relevant cell
    // false means the relevant edge is to the east  of the relevant cell
    private boolean toTheSouth;
    // The color to change this edge to
    private Color color;

    /**
     * Create a color-changing animation of an edge ofa maze
     * @param row The row of the cell to the west or north
     * @param col The column of the cell to the west or north
     * @param toTheSouth true if the edge is to the south. false if it is to the east
     * @param color The color to set the edge to
     */
    public EdgeAnimation(int row, int col, boolean toTheSouth, Color color) {
        this.row = row;
        this.col = col;
        this.toTheSouth = toTheSouth;
        this.color = color;
    }

    /**
     * Change the color of the specified edge in the given maze
     * @param graphicMaze The maze to modify the specified edge
     */
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

    /**
     * The length of time this animation should take
     * @return The length of time the animation should take
     */
    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
