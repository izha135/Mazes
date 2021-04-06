/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The data structure holding all of the relevant information about
 * the maze
 */

package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    // The 2D structure with the tiles in the maze
    private List<List<MazeTile>> maze;
    // The start tile of the maze
    private MazeTile startTile;
    // The end tile of the maze
    private MazeTile endTile;
    // The width of the maze
    private int width;

    /**
     * @param width The width of the board
     * @param fillWalls If all of the walls in the board should be filled
     */
    public Maze(int width, boolean fillWalls) {
        this.width = width;
        maze = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            maze.add(new ArrayList<>());
            for(int j = 0; j < width; j++) {
                MazeTile tile = new MazeTile(i, j, width);
                maze.get(i).add(tile);
                if(fillWalls) {
                    tile.setNorthWall(true);
                    tile.setEastWall(true);
                    tile.setSouthWall(true);
                    tile.setWestWall(true);
                }
                else {
                    tile.setNorthWall(false);
                    tile.setEastWall(false);
                    tile.setSouthWall(false);
                    tile.setWestWall(false);
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    /**
     * @param row The row of the desired tile
     * @param col The column of the desired tile
     * @return The tile at (row, col) in the maze
     */
    public MazeTile getTile(int row,int col){
        List<MazeTile> rowTile = maze.get(row);
        MazeTile colTile = rowTile.get(col);
        return colTile;
    }

    public List<MazeTile> collapseMaze() {
        List<MazeTile> retList = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                retList.add(getTile(i, j));
            }
        }
        return retList;
    }

    public List<MazeEdge> getEdges() {
        List<MazeEdge> edgeList = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width-1; j++) {
                if(getTile(i, j).hasEastWall()) {
                    edgeList.add(new MazeEdge(i, j, false));
                }
            }
        }

        for (int i = 0; i < width-1; i++) {
            for (int j = 0; j < width; j++) {
                if(getTile(i, j).hasSouthWall()) {
                    edgeList.add(new MazeEdge(i, j, true));
                }
            }
        }

        return edgeList;
    }

    public void removeWall(int row, int col, MazeDirection dir) {
        switch(dir) {
            case NORTH:
                if(col != 0){
                    removeWallNorthOf(row, col);
                    removeWallSouthOf(row, col-1);
                }
                break;
            case EAST:
                if(row != width-1) {
                    removeWallEastOf(row, col);
                    removeWallWestOf(row+1, col);
                }
                break;
            case SOUTH:
                if(col != width-1) {
                    removeWallSouthOf(row, col);
                    removeWallNorthOf(row, col+1);
                }
                break;
            case WEST:
                if(row != 0) {
                    removeWallWestOf(row, col);
                    removeWallEastOf(row-1, col);
                }
                break;
            default:
                break;
        }
    }

    public void addWall(int row, int col, MazeDirection dir) {
        switch (dir) {
            case NORTH:
                if(col != 0) {
                    addWallNorthOf(row, col);
                    addWallSouthOf(row, col-1);
                }
                break;
            case EAST:
                if(row != width-1) {
                    addWallEastOf(row, col);
                    addWallWestOf(row+1, col);
                }
                break;
            case SOUTH:
                if(col != width-1) {
                    addWallSouthOf(row, col);
                    addWallNorthOf(row, col+1);
                }
                break;
            case WEST:
                if(row != 0) {
                    addWallWestOf(row, col);
                    addWallEastOf(row-1, col);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Removes the wall to the north of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void removeWallNorthOf(int row, int col) {
        getTile(row,col).setNorthWall(false);
    }

    /**
     * Removes the wall to the east of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void removeWallEastOf(int row, int col) {
        getTile(row,col).setEastWall(false);
    }

    /**
     * Removes the wall to the south of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void removeWallSouthOf(int row, int col) {
        getTile(row,col).setSouthWall(false);
    }

    /**
     * Removes the wall to the west of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void removeWallWestOf(int row, int col) {
        getTile(row,col).setWestWall(false);
    }

    /**
     * Adds the wall to the north of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void addWallNorthOf(int row, int col){
        getTile(row,col).setNorthWall(true);
    }

    /**
     * Adds the wall to the east of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void addWallEastOf(int row, int col){
        getTile(row,col).setEastWall(true);
    }

    /**
     * Adds the wall to the south of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void addWallWestOf(int row, int col){
        getTile(row,col).setWestWall(true);
    }

    /**
     * Adds the wall to the west of the tile listed
     * @param row The desired row
     * @param col The desired column
     */
    public void addWallSouthOf(int row, int col){
        getTile(row,col).setSouthWall(true);
    }

    /**
     * Sets the start tile
     */
    public void setStartTile(int row, int col){
        this.startTile = getTile(row, col);
    }

    /**
     * Sets the end tile
     */
    public void setEndTile(int row, int col){
        this.endTile = getTile(row, col);
    }

    /**
     * Gives the start tile
     * @return The start tile of the maze
     */
    public MazeTile getStartTile() {
        return startTile;
    }

    /**
     * Gives the end tile
     * @return The end tile of the maze
     */
    public MazeTile getEndTile() {
        return endTile;
    }
}
