package mazesND.solvers;

import mazesND.animation.AnimationND;
import mazesND.maze.MazeND;

import java.util.List;

public interface MazeSolverND {
    List<AnimationND> solveMaze(MazeND maze);
}
