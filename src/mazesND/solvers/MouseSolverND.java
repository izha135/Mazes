package mazesND.solvers;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MouseSolverND implements MazeSolverND {
    int dimension;

    public MouseSolverND(int dimension) {
        this.dimension = dimension;
    }
    @Override
    public List<AnimationND> solveMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();

        MazeTileND thisTile = maze.getStartTile();
        MazeTileND nextTile;
        Stack<MazeTileND> path = new Stack<>();

        AnimationNDGroup animGroup;
        List<MazeTileND> tileList;
        while(thisTile != maze.getEndTile()) {
            animGroup = new AnimationNDGroup();
            tileList = thisTile.getOpenNeighbors(maze);
            Collections.shuffle(tileList);
            nextTile = tileList.get(0);
            if(thisTile != maze.getStartTile() && thisTile != maze.getEndTile()) {
                if(path.size() > 0 && path.peek() == nextTile) {
                    path.pop();
                    if (dimension == 2) {
                        animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.BEIGE));
                    }
                    else {
                        animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.TRANSPARENT));
                    }
                }
                else {
                    path.push(thisTile);
                    animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.SALMON));
                }
            }
            if(nextTile != maze.getStartTile() && nextTile != maze.getEndTile()) {
                animGroup.add(new TileAnimationND(nextTile.getPosition(), Color.BLACK));
            }
            animList.add(animGroup);
            thisTile = nextTile;
        }

        return animList;
    }
}
