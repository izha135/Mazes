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

public class RoutingSolverND implements MazeSolverND{
    private int dimension;
    private int direction;
    private boolean leftDirection;

    public RoutingSolverND(int dimension) {
        this.dimension = dimension;
        direction = 1;
        leftDirection = true;
    }

    @Override
    public List<AnimationND> solveMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        MazeTileND thisTile = maze.getStartTile();
        MazeTileND nextTile = null;
        AnimationNDGroup animGroup;

        Stack<MazeTileND> path = new Stack<>();

        List<Integer> prodPaths, tempPos;
        int direction, mdBest;
        while(thisTile != maze.getEndTile()) {
            animGroup = new AnimationNDGroup();
            prodPaths = productivePaths(thisTile, maze);
            if(prodPaths.size() != 0) {
                Collections.shuffle(prodPaths);
                direction = prodPaths.get(0);
                tempPos = thisTile.getPosition();
                if (direction > 0) tempPos.set(direction-1, tempPos.get(direction-1) + 1);
                if (direction < 0) tempPos.set(-direction-1, tempPos.get(-direction-1) - 1);
                nextTile = maze.getTile(tempPos);

                if(thisTile != maze.getEndTile() && thisTile != maze.getStartTile()) {
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
                if(nextTile != maze.getEndTile() && nextTile != maze.getStartTile()) {
                    animGroup.add(new TileAnimationND(nextTile.getPosition(), Color.BLACK));
                }
            }
            // No productive paths
            else {
                mdBest = distance(thisTile.getPosition(), maze.getEndTile().getPosition());
                while (distance(thisTile.getPosition(), maze.getEndTile().getPosition()) != mdBest
                        || productivePaths(thisTile, maze).size() == 0) {
                    animGroup = new AnimationNDGroup();
                    nextTile = leftDirection? leftTile(thisTile, maze) : rightTile(thisTile, maze);

                    if(thisTile != maze.getEndTile() && thisTile != maze.getStartTile()) {
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
                    if(nextTile != maze.getEndTile() && nextTile != maze.getStartTile()) {
                        animGroup.add(new TileAnimationND(nextTile.getPosition(), Color.BLACK));
                    }

                    animList.add(animGroup);
                    thisTile = nextTile;
                }
            }

            if (!animList.contains(animGroup)) animList.add(animGroup);
            thisTile = nextTile;
        }

        return animList;
    }

    public int distance(List<Integer> pos1, List<Integer> pos2) {
        int dist = 0;
        for (int i = 0; i < pos1.size(); i++) {
            dist += Math.abs(pos1.get(i)-pos2.get(i));
        }
        return dist;
    }

    public List<Integer> productivePaths(MazeTileND tile, MazeND maze) {
        List<Integer> retList = new ArrayList<>();
        List<Integer> endPos = maze.getEndTile().getPosition();
        List<Integer> tilePos = tile.getPosition();

        for(int i = 1; i <= dimension; i++) {
            if (endPos.get(i-1)-tilePos.get(i-1) > 0) {
                if(!tile.hasPositiveWall(i)) {
                    retList.add(i);
                }
            }
        }
        for(int i = 1; i <= dimension; i++) {
            if (endPos.get(i-1)-tilePos.get(i-1) < 0) {
                if(!tile.hasNegativeWall(i)) {
                    retList.add(-i);
                }
            }
        }

        return retList;
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


    /*
    Point src, dst;// Source and destination coordinates
    // cur also indicates the coordinates of the current location
    int MD_best = MD(src, dst);// It stores the closest MD we ever had to dst
    // A productive path is the one that makes our MD to dst smaller
    while (cur != dst) {
        if (there exists a productive path) {
            Take the productive path;
        } else {
            MD_best = MD(cur, dst);
            Imagine a line between cur and dst;
            Take the first path in the left/right of the line; // The left/right selection affects the following hand rule
            while (MD(cur, dst) != MD_best || there does not exist a productive path) {
                Follow the right-hand/left-hand rule; // The opposite of the selected side of the line
        }
    }
     */
}
