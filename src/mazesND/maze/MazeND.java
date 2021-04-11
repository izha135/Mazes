package mazesND.maze;

import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MazeND {
    List<List<List<List<List<List<MazeTileND>>>>>> maze;
    MazeTileND startTile;
    MazeTileND endTile;
    int width;

    /**
     * @param width The width of the board
     * @param dimension The dimension of the board
     */
    public MazeND(int width, int dimension) {
        maze = new ArrayList<>();
        this.width = width;

        List<Integer> tempList = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0));
        int iLim = width;
        int jLim = width;
        int kLim = dimension > 2? width: 1;
        int lLim = dimension > 3? width: 1;
        int mLim = dimension > 4? width: 1;
        int nLim = dimension > 5? width: 1;

        MazeTileND tile;

        for (int i = 0; i < iLim; i++) {
            maze.add(new ArrayList<>());
            tempList.set(0, i);
            for (int j = 0; j < jLim; j++) {
                maze.get(i).add(new ArrayList<>());
                tempList.set(1, j);
                for (int k = 0; k < kLim; k++) {
                    maze.get(i).get(j).add(new ArrayList<>());
                    tempList.set(2, k);
                    for (int l = 0; l < lLim; l++) {
                        maze.get(i).get(j).get(k).add(new ArrayList<>());
                        tempList.set(3, l);
                        for (int m = 0; m < mLim; m++) {
                            maze.get(i).get(j).get(k).get(l).add(new ArrayList<>());
                            tempList.set(4, m);
                            for (int n = 0; n < nLim; n++) {
                                tempList.set(5, n);
                                tile = new MazeTileND(tempList, dimension, 0, width);
                                maze.get(i).get(j).get(k).get(l).get(m).add(tile);
                            }
                        }
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    private int valueOf(List<Integer> pos) {
        int temp = pos.get(5);
        for (int i = 4; i >= 0; i--) {
            temp = pos.get(i) + (width * temp);
        }
        return temp;
    }

    public List<MazeTileND> collapseMaze() {
        List<Integer> tempList = new LinkedList<>(List.of(0, 0, 0, 0, 0, 0));
        List<MazeTileND> tempRetList = new ArrayList<>();
        for (int i = 0; i < maze.size(); i++) {
            tempList.set(0, i);
            for (int j = 0; j < maze.get(i).size(); j++) {
                tempList.set(1, j);
                for (int k = 0; k < maze.get(i).get(j).size(); k++) {
                    tempList.set(2, k);
                    for (int l = 0; l < maze.get(i).get(j).get(k).size(); l++) {
                        tempList.set(3, l);
                        for (int m = 0; m < maze.get(i).get(j).get(k).get(l).size(); m++) {
                            tempList.set(4, m);
                            for (int n = 0; n < maze.get(i).get(j).get(k).get(l).get(m).size(); n++) {
                                tempList.set(5, n);
                                tempRetList.add(getTile(tempList));
                            }
                        }
                    }
                }
            }
        }
        return tempRetList;
    }

    public List<MazeEdgeND> listOfEdges() {
        List<Integer> tempList = new LinkedList<>(List.of(0, 0, 0, 0, 0, 0));
        List<MazeEdgeND> tempRetList = new ArrayList<>();
        for (int i = 0; i < maze.size(); i++) {
            tempList.set(0, i);
            for (int j = 0; j < maze.get(i).size(); j++) {
                tempList.set(1, j);
                for (int k = 0; k < maze.get(i).get(j).size(); k++) {
                    tempList.set(2, k);
                    for (int l = 0; l < maze.get(i).get(j).get(k).size(); l++) {
                        tempList.set(3, l);
                        for (int m = 0; m < maze.get(i).get(j).get(k).get(l).size(); m++) {
                            tempList.set(4, m);
                            for (int n = 0; n < maze.get(i).get(j).get(k).get(l).get(m).size(); n++) {
                                tempList.set(5, n);
                                tempRetList.addAll(getTile(tempList).getEdges());
                            }
                        }
                    }
                }
            }
        }
        Collections.shuffle(tempRetList);
        return tempRetList;
    }

    // Things with startTile and endTile

    public MazeTileND getTile(List<Integer> pos) {
        return maze.get(pos.get(0)).get(pos.get(1)).get(pos.get(2))
                .get(pos.get(3)).get(pos.get(4)).get(pos.get(5));
    }

    public MazeTileND getStartTile() { return startTile; }

    public MazeTileND getEndTile() { return endTile; }

    public void setStartTile(List<Integer> pos) {
        startTile = getTile(pos);
    }

    public void setEndTile(List<Integer> pos) {
        endTile = getTile(pos);
    }

    public void removeWall(List<Integer> pos, int direction) {
        List<Integer> posCopy = new ArrayList<>(pos);
        if (direction > 0) {
            posCopy.set(direction-1, posCopy.get(direction-1)+1);
            getTile(pos).setPositiveEdge(direction, false);
            getTile(posCopy).setNegativeEdge(direction, false);
        }
        else {
            posCopy.set(-direction-1, posCopy.get(-direction-1)-1);
            getTile(pos).setNegativeEdge(-direction, false);
            getTile(posCopy).setPositiveEdge(-direction, false);
        }
    }

    public void addWall(List<Integer> pos, int direction) {
        List<Integer> posCopy = new ArrayList<>(pos);
        if (direction > 0) {
            posCopy.set(direction-1, posCopy.get(direction-1)+1);
            getTile(pos).setPositiveEdge(direction, true);
            getTile(posCopy).setNegativeEdge(direction, true);
        }
        else {
            posCopy.set(-direction+1, posCopy.get(-direction+1)-1);
            getTile(pos).setNegativeEdge(-direction, true);
            getTile(posCopy).setPositiveEdge(-direction, true);
        }
    }

}
