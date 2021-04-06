/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * General interface for animation classes
 */

package animation;

public interface Animation {
    void animate(GraphicMaze graphicMaze);
    double getAnimateTime();
}