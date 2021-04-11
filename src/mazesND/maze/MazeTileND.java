package mazesND.maze;

import java.util.ArrayList;
import java.util.List;

public class MazeTileND {

    List<Boolean> positiveEdges;
    List<Boolean> negativeEdges;
    List<Integer> position;
    int dimension;
    int mazeWidth;
    int index;

    public MazeTileND(List<Integer> pos, int dimension, int index, int width) {
        this.mazeWidth = width;
        positiveEdges = new ArrayList<>();
        negativeEdges = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            positiveEdges.add(true);
            negativeEdges.add(true);
        }

        position = new ArrayList<>(pos);
        this.dimension = dimension;
        this.index = index;
    }

    public List<Integer> getPosition() {
        return new ArrayList<>(position);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setPositiveEdge(int direction, boolean exists) {
        positiveEdges.set(direction-1, exists);
    }

    public void setNegativeEdge(int direction, boolean exists) {
        negativeEdges.set(direction-1, exists);
    }

    public boolean hasPositiveWall(int direction) {
        return positiveEdges.get(direction-1);
    }

    public boolean hasNegativeWall(int direction) {
        return negativeEdges.get(direction-1);
    }

    public List<MazeEdgeND> getEdges() {
        List<MazeEdgeND> tempRetList = new ArrayList<>();
        List<Integer> p = new ArrayList<>(position);

        for (int i = 1; i <= dimension; i++) {
            if(hasPositiveWall(i) && p.get(i-1) < mazeWidth-1) {
                tempRetList.add(new MazeEdgeND(p, i));
            }
            if(hasNegativeWall(i) && p.get(i-1) > 0) {
                tempRetList.add(new MazeEdgeND(p, -i));
            }
        }

        return tempRetList;
    }

    public List<MazeTileND> getNeighbors(MazeND maze) {
        List<MazeTileND> tempRetList = new ArrayList<>();
        List<Integer> tempPosition = getPosition();
        List<Integer> tempPos;

        for (int i = 1; i <= dimension; i++) {
            if(tempPosition.get(i-1) < mazeWidth-1) {
                tempPos = new ArrayList<>(tempPosition);
                tempPos.set(i-1, tempPos.get(i-1) + 1);
                tempRetList.add(maze.getTile(tempPos));
            }
            if(tempPosition.get(i-1) > 0) {
                tempPos = new ArrayList<>(tempPosition);
                tempPos.set(i-1, tempPos.get(i-1) - 1);
                tempRetList.add(maze.getTile(tempPos));
            }
        }

        return tempRetList;
    }

    public List<MazeTileND> getOpenNeighbors(MazeND maze) {
        List<MazeTileND> tempRetList = new ArrayList<>();
        List<Integer> tempPosition = getPosition();
        List<Integer> tempPos;

        for (int i = 1; i <= dimension; i++) {
            if(tempPosition.get(i-1) < mazeWidth-1 && !hasPositiveWall(i)) {
                tempPos = new ArrayList<>(tempPosition);
                tempPos.set(i-1, tempPos.get(i-1) + 1);
                tempRetList.add(maze.getTile(tempPos));
            }
            if(tempPosition.get(i-1) > 0 && !hasNegativeWall(i)) {
                tempPos = new ArrayList<>(tempPosition);
                tempPos.set(i-1, tempPos.get(i-1) - 1);
                tempRetList.add(maze.getTile(tempPos));
            }
        }

        return tempRetList;
    }
}
