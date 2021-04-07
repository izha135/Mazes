package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MouseSolver implements MazeSolver{
    public MouseSolver() {

    }

    @Override
    public List<Animation> solve(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        for(MazeTile tile : maze.collapseMaze()) {
            tile.setIndex(0);
        }

        MazeTile currentTile = startTile;
        currentTile.setIndex(1);
        MazeTile nextTile;
        List<MazeTile> neighbors;
        while (currentTile != endTile) {
            neighbors = currentTile.getOpenNeighbors(maze);
            Collections.shuffle(neighbors);
            nextTile = neighbors.get(0);

            if(nextTile.getIndex() == 1) {
                currentTile.setIndex(0);
                animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BEIGE));
            }

            nextTile.setIndex(1);
            if(nextTile != startTile && nextTile != endTile) {
                animList.add(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.PINK));
            }

            currentTile = nextTile;
        }

        return animList;
    }
}
