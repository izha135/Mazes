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
    // The row and column of the tile in the maze
    private int row, col;
    // The width of the maze (for bounds checking)
    private int width;

    /**
     * Creates a maze tile. Edges are set
     */
    public MazeTile(int row, int col, int width) {
        this.width = width;
        this.row = row;
        this.col = col;
        // Preset the edges to all be present
        northWall = true;
        eastWall = true;
        southWall = true;
        westWall = true;
        index = 0;
    }

    /**
     * @return The row of the tile in the maze
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The column of the tile in the maze
     */
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
     *
     * @param eastWall The new value for if the east wall is present
     */
    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    /**
     * Sets the value of the north wall of the given tile
     *
     * @param northWall The new value for if the north wall is present
     */
    public void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    /**
     * Sets the value of the south wall of the given tile
     *
     * @param southWall The new value for if the south wall is present
     */
    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    /**
     * Sets the value of the west wall of the given tile
     *
     * @param westWall The new value for if the west wall is present
     */
    public void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }

    /**
     * Returns a list of all of the walls in the maze
     *
     * @return The list of walls in the maze
     */
    public List<MazeEdge> getWalls() {
        List<MazeEdge> retList = new ArrayList<>();

        // Don't add the north wall if the tile is on the top edge
        if (hasNorthWall() && row != 0) retList.add(new MazeEdge(row - 1, col, true));
        // Don't add the south wall if the tile in on the bottom edge
        if (hasSouthWall() && row != width - 1) retList.add(new MazeEdge(row, col, true));
        // Don't add the west wall if the tile is on the left edge
        if (hasWestWall() && col != 0) retList.add(new MazeEdge(row, col - 1, false));
        // Don't add the west wall if the tile is on the right edge
        if (hasEastWall() && col != width - 1) retList.add(new MazeEdge(row, col, false));

        return retList;
    }

    /**
     * Returns the cells that can be traversed from the current
     * cell
     * @param maze The maze to find the neighboring cells in
     * @return The list of neighbors
     */
    public List<MazeTile> getOpenNeighbors(Maze maze) {
        List<MazeTile> retList = new ArrayList<>();

        // Add cells if there aren't walls between the cells
        if (!hasNorthWall()) retList.add(maze.getTile(row - 1, col));
        if (!hasSouthWall()) retList.add(maze.getTile(row + 1, col));
        if (!hasWestWall()) retList.add(maze.getTile(row, col - 1));
        if (!hasEastWall()) retList.add(maze.getTile(row, col + 1));

        return retList;
    }
}