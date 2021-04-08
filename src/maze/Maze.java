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
                // All walls are usually set to be present. If not filling
                // the walls, set all to false
                if(!fillWalls) {
                    tile.setNorthWall(false);
                    tile.setEastWall(false);
                    tile.setSouthWall(false);
                    tile.setWestWall(false);
                }
            }
        }
    }

    /**
     * @return Width (number of tiles) of the maze
     */
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

    /**
     * Give a 1-dimensional list of all of the tiles in the maze
     * @return The list of all tiles in the maze
     */
    public List<MazeTile> collapseMaze() {
        List<MazeTile> retList = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                retList.add(getTile(i, j));
            }
        }
        return retList;
    }

    /**
     * Give the total list of all edges in the maze
     * @return The edges in the maze
     */
    public List<MazeEdge> getEdges() {
        List<MazeEdge> edgeList = new ArrayList<>();

        // Add all of the vertical walls
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width-1; j++) {
                if(getTile(i, j).hasEastWall()) {
                    edgeList.add(new MazeEdge(i, j, false));
                }
            }
        }

        // Add all of the horizontal walls
        for (int i = 0; i < width-1; i++) {
            for (int j = 0; j < width; j++) {
                if(getTile(i, j).hasSouthWall()) {
                    edgeList.add(new MazeEdge(i, j, true));
                }
            }
        }

        return edgeList;
    }

    /**
     * Return the tile in the given direction from the specified coordinates
     * @param row The row of the relevant tile
     * @param col The column of the relevant tile
     * @param dir The direction to get the next tile
     * @return The tile in the specified direction. Null if no tile exists there
     */
    public MazeTile getTile(int row, int col, MazeDirection dir) {
        switch(dir) {
            case NORTH:
                if (row != 0) {
                    return getTile(row-1, col);
                }
                break;
            case EAST:
                if(col != width-1) {
                    return getTile(row, col+1);
                }
                break;
            case SOUTH:
                if(row != width-1) {
                    return getTile(row+1, col);
                }
                break;
            case WEST:
                if(col != 0) {
                    return getTile(row, col-1);
                }
                break;
            default:
                return null;
        }
        return null;
    }

    /**
     * Removes a wall from the specified tile in the specified directions.
     * This removes the wall from both sides
     * @param row The row of the tile
     * @param col The column of the tile
     * @param dir The direction of the wall to remove
     */
    public void removeWall(int row, int col, MazeDirection dir) {
        switch(dir) {
            case NORTH:
                // Don't remove edges to the north if on the top edge
                if(row != 0){
                    removeWallNorthOf(row, col);
                    removeWallSouthOf(row-1, col);
                }
                break;
            case EAST:
                // Don't remove edges to to the east if on the right edge
                if(col != width-1) {
                    removeWallEastOf(row, col);
                    removeWallWestOf(row, col+1);
                }
                break;
            case SOUTH:
                // Don't remove edges to the south if on the bottom edge
                if(row != width-1) {
                    removeWallSouthOf(row, col);
                    removeWallNorthOf(row+1, col);
                }
                break;
            case WEST:
                // Don't remove edges to the west if on the left edge
                if(col != 0) {
                    removeWallWestOf(row, col);
                    removeWallEastOf(row, col-1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Adds a wall on the specified tile in the specified direction.
     * Also adds the wall on the other side of the edge.
     * @param row The row of the tile
     * @param col The column of the tile
     * @param dir The direction to add the wall in
     */
    public void addWall(int row, int col, MazeDirection dir) {
        switch (dir) {
            case NORTH:
                // Don't change the north wall if the wall is on the top edge
                if(col != 0) {
                    addWallNorthOf(row, col);
                    addWallSouthOf(row, col-1);
                }
                break;
            case EAST:
                // Don't change the east wall if the wall is on the right edge
                if(row != width-1) {
                    addWallEastOf(row, col);
                    addWallWestOf(row+1, col);
                }
                break;
            case SOUTH:
                // Don't change the south wall if the wall is on the bottom edge
                if(col != width-1) {
                    addWallSouthOf(row, col);
                    addWallNorthOf(row, col+1);
                }
                break;
            case WEST:
                // Don't change to the west wall if the wall is on the left edge
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
