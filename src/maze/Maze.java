package maze;

import java.util.List;

public class Maze <Tile extends MazeTile> {
    List<List<Tile>> maze;
    Tile startTile;
    Tile endTile;

    /**
     * @param width The width of the board
     * @param fillWalls If all of the walls in the board should be filled
     */
    public Maze(int width, boolean fillWalls) {

    }

    // Things with startTile and endTile

    // Add wall methods, like the remove wall ones

    public void removeWallNorthOf(int row, int col) {

    }

    public void removeWallEastOf(int row, int col) {

    }

    public void removeWallSouthOf(int row, int col) {

    }

    public void removeWallWestOf(int row, int col) {

    }

}
