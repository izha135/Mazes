/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The individual cells of the maze
 */

package maze;

import java.util.ArrayList;
import java.util.List;

public class MazeTile {
    // true if the wall to the north of the tile is present
    private boolean northWall;
    // true if the wall to the south of the tile is present
    private boolean southWall;
    // true if the wall to the west of the tile is present
    private boolean westWall;
    // true if the wall to the east of the tile is present
    private boolean eastWall;
    // A useful index/value in creating
    private int index;
    private int row, col;
    private int width;

    /**
     * Creates a maze tile
     */
    public MazeTile(int row, int col, int width) {
        this.width = width;
        this.row = row;
        this.col = col;
        northWall = true;
        eastWall = true;
        southWall = true;
        westWall = true;
        index = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * @return The index of the tile
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index The index/relevant value of the tile
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return true if the tile has a north wall
     */
    public boolean hasNorthWall() {
        return northWall;
    }

    /**
     * @return true if the tile has a east wall
     */
    public boolean hasEastWall() {
        return eastWall;
    }

    /**
     * @return true if the tile has a south wall
     */
    public boolean hasSouthWall() {
        return southWall;
    }

    /**
     * @return true if the tile has a west wall
     */
    public boolean hasWestWall() {
        return westWall;
    }

    /**
     * Sets the value of the east wall of the given tile
     * @param eastWall The new value for if the east wall is present
     */
    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    /**
     * Sets the value of the north wall of the given tile
     * @param northWall The new value for if the north wall is present
     */
    public void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    /**
     * Sets the value of the south wall of the given tile
     * @param southWall The new value for if the south wall is present
     */
    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    /**
     * Sets the value of the west wall of the given tile
     * @param westWall The new value for if the west wall is present
     */
    public void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }

    public List<MazeEdge> getWalls() {
        List<MazeEdge> retList = new ArrayList<>();

        if (hasNorthWall() && row != 0) retList.add(new MazeEdge(row-1, col, true));
        if (hasSouthWall() && row != width-1) retList.add(new MazeEdge(row, col, true));
        if (hasWestWall() && col != 0) retList.add(new MazeEdge(row, col-1, false));
        if (hasEastWall() && col != width-1) retList.add(new MazeEdge(row, col, false));

        return retList;
    }
}
