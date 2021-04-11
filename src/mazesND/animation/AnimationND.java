/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 */

package mazesND.animation;

import mazesND.animation.GraphicMazeND;

public interface AnimationND {
    void animate(GraphicMazeND graphicMaze);
    double getAnimateTime();
}