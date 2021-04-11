package mazesND.solvers;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WallSolverND implements MazeSolverND{
    int dimension;
    boolean leftDirection = false;
    int direction;

    public WallSolverND(int dimension) {
        this.dimension = dimension;
        direction = 1;
    }

    @Override
    public List<AnimationND> solveMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();

        MazeTileND thisTile = maze.getStartTile();
        MazeTileND nextTile;
        Stack<MazeTileND> path = new Stack<>();

        AnimationNDGroup animGroup;

        while(thisTile != maze.getEndTile()) {
            animGroup = new AnimationNDGroup();
            nextTile = leftDirection? leftTile(thisTile, maze) : rightTile(thisTile, maze);
            if(thisTile != maze.getStartTile() && thisTile != maze.getEndTile()) {
                if(path.size() > 0 && path.peek() == nextTile) {
                    path.pop();
                    if (dimension == 2) {
                        animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.WHITE));
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

    private MazeTileND leftTile(MazeTileND thisTile, MazeND maze) {
        MazeTileND nextTile = null;
        direction = -direction;

        while(nextTile == null) {
            getNextLeftDirection();
            if (direction > 0) {
                if (!thisTile.hasPositiveWall(direction)) {
                    List<Integer> tempPos = thisTile.getPosition();
                    tempPos.set(direction-1, tempPos.get(direction-1) + 1);
                    nextTile = maze.getTile(tempPos);
                }
            }
            else {
                if (!thisTile.hasNegativeWall(-direction)) {
                    List<Integer> tempPos = thisTile.getPosition();
                    tempPos.set(-1-direction, tempPos.get(-1-direction) - 1);
                    nextTile = maze.getTile(tempPos);
                }
            }
        }

        return nextTile;
    }

    private MazeTileND rightTile(MazeTileND thisTile, MazeND maze) {
        MazeTileND nextTile = null;
        direction = -direction;

        while(nextTile == null) {
            getNextRightDirection();
            if (direction > 0) {
                if (!thisTile.hasPositiveWall(direction)) {
                    List<Integer> tempPos = thisTile.getPosition();
                    tempPos.set(direction-1, tempPos.get(direction-1) + 1);
                    nextTile = maze.getTile(tempPos);
                }
            }
            else {
                if (!thisTile.hasNegativeWall(-direction)) {
                    List<Integer> tempPos = thisTile.getPosition();
                    tempPos.set(-1-direction, tempPos.get(-1-direction) - 1);
                    nextTile = maze.getTile(tempPos);
                }
            }
        }

        return nextTile;
    }

    private void getNextLeftDirection() {
        if (direction == 1) direction = -dimension;
        else if (direction == -1) direction = dimension;
        else if (direction > 1) direction -= 1;
        else if (direction < -1) direction += 1;
    }

    private void getNextRightDirection() {
        if (direction == dimension) direction = -1;
        else if (direction == -dimension) direction = 1;
        else if (direction > 0) direction += 1;
        else if (direction < 0) direction -= 1;
    }
}
