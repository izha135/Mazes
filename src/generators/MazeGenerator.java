/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The base interface of a maze generator
 */

package generators;

import animation.Animation;
import maze.Maze;

import java.util.List;

public interface MazeGenerator {
    List<Animation> generate(Maze maze);
}
