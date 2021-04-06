/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The base interface of a maze solver
 */

package solvers;

import animation.Animation;
import maze.Maze;

import java.util.List;

public interface MazeSolver {
    List<Animation> solve(Maze maze);
}
