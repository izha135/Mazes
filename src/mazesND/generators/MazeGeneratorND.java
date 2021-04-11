package mazesND.generators;

import mazesND.animation.AnimationND;
import mazesND.maze.MazeND;

import java.util.List;

public interface MazeGeneratorND {
    List<AnimationND> generateMaze(MazeND maze);
}
