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
        maze = new ArrayList<>();
        ArrayList list = new ArrayList();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < width; j++) {
                MazeTile tile = new MazeTile();
                list.add(tile);
            }
        }
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
    public void setStartTile(){
        this.startTile = startTile;
    }

    /**
     * Sets the end tile
     */
    public void setEndTile(){
        this.endTile = endTile;
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
