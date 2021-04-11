/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * Holds an animation changing the color of a given edge
 */

package mazesND.animation;

import mazesND.animation.GraphicMazeND;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class EdgeAnimationND implements AnimationND {
    // The amount of time the animation should take
    private static double animateTime = 0.1;

    // Dimension of the game
    int dimension;
    // The position of the relevant cell
    private List<Integer> pos;
    // 1 - South
    // 2 - East
    // 3 - Below
    private int direction;
    // The color to change this edge to
    private Color color;

    public EdgeAnimationND(List<Integer> pos, int direction, Color color) {
        this.dimension = pos.size();
        this.pos = new ArrayList<>(pos);
        this.direction = direction;
        this.color = color;
    }

    /**
     * Change the color of the specified edge in the given maze
     * @param graphicMaze The maze to modify the specified edge
     */
    @Override
    public void animate(GraphicMazeND graphicMaze) {
        List<Integer> posCopy = new ArrayList<>(pos);
        // To the south
        if(direction == 1) {
            graphicMaze.get(posCopy).setSouthColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getSouthWall().setOpacity(0.0);
            posCopy.set(0, posCopy.get(0) + 1);
            graphicMaze.get(posCopy).setNorthColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getNorthWall().setOpacity(0.0);
        }
        // To the east
        if(direction == 2) {
            graphicMaze.get(posCopy).setEastColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getEastWall().setOpacity(0.0);
            posCopy.set(1, posCopy.get(1) + 1);
            graphicMaze.get(posCopy).setWestColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getWestWall().setOpacity(0.0);
        }
        // Below
        if(direction == 3) {
            graphicMaze.get(posCopy).setBelowColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getBelowWall().setOpacity(0.0);
            posCopy.set(2, posCopy.get(2) + 1);
            graphicMaze.get(posCopy).setAboveColor(color);
            if(color.getOpacity() == 0) graphicMaze.get(posCopy).getAboveWall().setOpacity(0.0);
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
