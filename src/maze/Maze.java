package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private List<List<MazeTile>> maze;
    private MazeTile startTile;
    private MazeTile endTile;
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

    // Things with startTile and endTile

    // Add wall methods, like the remove wall ones

    public MazeTile getTile(int row,int col){
        List<MazeTile> rowTile = maze.get(row);
        MazeTile colTile = rowTile.get(col);
        return colTile;
    }

    public void removeWallNorthOf(int row, int col) {
        getTile(row,col).setNorthWall(false);
    }

    public void removeWallEastOf(int row, int col) {
        getTile(row,col).setEastWall(false);
    }

    public void removeWallSouthOf(int row, int col) {
        getTile(row,col).setSouthWall(false);

    }

    public void removeWallWestOf(int row, int col) {
        getTile(row,col).setWestWall(false);

    }

}
