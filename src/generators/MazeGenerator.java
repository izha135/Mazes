package generators;

import animation.Animation;
import maze.Maze;

import java.util.List;

public interface MazeGenerator {
    List<Animation> generate(Maze maze);
}
