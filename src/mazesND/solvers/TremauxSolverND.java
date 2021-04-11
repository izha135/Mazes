package mazesND.solvers;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.EdgeAnimationND;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TremauxSolverND implements MazeSolverND{
    private int dimension;

    public TremauxSolverND(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public List<AnimationND> solveMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();

        if (dimension > 2) {
            System.out.println("This solver is not supported with the given dimension");
            return  animList;
        }

        setWallTiles(maze);

        MazeTileND thisTile = maze.getStartTile();
        MazeTileND nextTile = null;
        List<Integer> tempPos;
        int lastDirection = 0;

        AnimationNDGroup animGroup;
        List<MazeTileND> tileList = maze.collapseMaze();
        List<Integer> nonEdges;
        List<Integer> traversable;
        while(thisTile != maze.getEndTile()) {
            animGroup = new AnimationNDGroup();

            traversable = getTraversable(thisTile);
            boolean isEmpty = traversable.contains(0);
            for(int i = 0; i < traversable.size(); i++) {
                if (i == directionToIndex(-lastDirection)) continue;
                if (traversable.get(i) == 1 || traversable.get(i) == 2) isEmpty = false;
            }
            if (isEmpty) {
                nonEdges = getEmptyDirection(thisTile);
                Collections.shuffle(nonEdges);
                lastDirection = nonEdges.get(0);

                tempPos = thisTile.getPosition();
                if (lastDirection > 0) tempPos.set(lastDirection-1, tempPos.get(lastDirection-1) + 1);
                if (lastDirection < 0) tempPos.set(-lastDirection-1, tempPos.get(-lastDirection-1) - 1);
                nextTile = maze.getTile(tempPos);
            }
            else {
                if (traversable.get(directionToIndex(-lastDirection)) == 1) {
                    lastDirection = -lastDirection;

                    tempPos = thisTile.getPosition();
                    if (lastDirection > 0) tempPos.set(lastDirection-1, tempPos.get(lastDirection-1) + 1);
                    if (lastDirection < 0) tempPos.set(-lastDirection-1, tempPos.get(-lastDirection-1) - 1);
                    nextTile = maze.getTile(tempPos);
                }
                else {
                    if (traversable.contains(0)) nonEdges = getEmptyDirection(thisTile);
                    else {
                        nonEdges = getTraversableDirections(thisTile);
                        nonEdges.removeAll(getEmptyDirection(thisTile));
                    }
                    Collections.shuffle(nonEdges);
                    lastDirection = nonEdges.get(0);

                    tempPos = thisTile.getPosition();
                    if (lastDirection > 0) tempPos.set(lastDirection-1, tempPos.get(lastDirection-1) + 1);
                    if (lastDirection < 0) tempPos.set(-lastDirection-1, tempPos.get(-lastDirection-1) - 1);
                    nextTile = maze.getTile(tempPos);
                }
            }

            incTravelled(thisTile, lastDirection);
            incTravelled(nextTile, -lastDirection);

            if(thisTile != maze.getEndTile() && thisTile != maze.getStartTile()) {
                if(getTraversableDirections(thisTile).size() == 0) {
                    if (dimension == 2) {
                        animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.WHITE));
                    }
                    else{
                        animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.TRANSPARENT));
                    }
                }
                else {
                    animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.VIOLET));
                }
            }
            if(nextTile != maze.getEndTile() && nextTile != maze.getStartTile()) {
                animGroup.add(new TileAnimationND(nextTile.getPosition(), Color.BLACK));
            }

            // Edge animations
            traversable = getTraversable(thisTile);
            if (lastDirection > 0) {
                if(traversable.get(directionToIndex(lastDirection)) == 1) {
                    animGroup.add(new EdgeAnimationND(thisTile.getPosition(), lastDirection, Color.YELLOW));
                }
                else{
                    animGroup.add(new EdgeAnimationND(thisTile.getPosition(), lastDirection, Color.TRANSPARENT));
                }
            }
            if (lastDirection < 0) {
                if(traversable.get(directionToIndex(lastDirection)) == 1) {
                    animGroup.add(new EdgeAnimationND(nextTile.getPosition(), -lastDirection, Color.YELLOW));
                }
                else{
                    animGroup.add(new EdgeAnimationND(nextTile.getPosition(), -lastDirection, Color.TRANSPARENT));
                }
            }

            animList.add(animGroup);

            thisTile = nextTile;
        }

        return animList;
    }

    public int directionToIndex(int direction) {
        return direction > 0? direction-1 : dimension-direction-1;
    }

    public List<Integer> getEmptyDirection(MazeTileND tile) {
        List<Integer> directionList = new ArrayList<>();
        List<Integer> retList = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) directionList.add(i);
        for (int i = 1; i <= dimension; i++) directionList.add(-i);

        int index = tile.getIndex();
        for(int i = 0; i < dimension*2; i++) {
            if(index % 4 == 0) retList.add(directionList.get(i));
            index = index/4;
        }

        return retList;
    }

    public List<Integer> getNonEdgeDirections(MazeTileND tile) {
        List<Integer> directionList = new ArrayList<>();
        List<Integer> retList = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) directionList.add(i);
        for (int i = 1; i <= dimension; i++) directionList.add(-i);

        int index = tile.getIndex();
        for(int i = 0; i < dimension*2; i++) {
            if(index % 4 != 3) retList.add(directionList.get(i));
            index = index/4;
        }

        return retList;
    }

    public void incTravelled(MazeTileND tile, int direction) {
        int index = 0;
        if (direction > 0) index = direction-1;
        if (direction < 0) index = dimension-direction-1;
        int mult = 1;
        for(int i = 0; i < index; i++) mult *= 4;
        tile.setIndex(tile.getIndex() + mult);
    }

    public List<Integer> getTraversableDirections(MazeTileND tile) {
        List<Integer> directionList = new ArrayList<>();
        List<Integer> retList = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) directionList.add(i);
        for (int i = 1; i <= dimension; i++) directionList.add(-i);

        int index = tile.getIndex();
        for(int i = 0; i < dimension*2; i++) {
            if(index % 4 < 2) retList.add(directionList.get(i));
            index = index/4;
        }

        return retList;
    }

    public List<Integer> getTraversable(MazeTileND tile) {
        List<Integer> traversable = new ArrayList<>();

        int index = tile.getIndex();

        for(int i = 0; i < dimension*2; i++) {
            traversable.add(index % 4);
            index = index/4;
        }

        return traversable;
    }

    public void setWallTiles(MazeND maze) {
        List<MazeTileND> tileList = maze.collapseMaze();

        int index;
        for(MazeTileND tile : tileList) {
            tile.setIndex(0);
            index = 0;
            for (int i = dimension; i >= 1; i--) {
                if(tile.hasNegativeWall(i)) index = (4*index) + 3;
                else                        index = (4*index);
            }
            for (int i = dimension; i >= 1; i--) {
                if(tile.hasPositiveWall(i)) index = (4*index) + 3;
                else                        index = (4*index);
            }
            tile.setIndex(index);
        }
    }
}
