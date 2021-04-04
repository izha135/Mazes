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
        for(int i = 0; i < width; i++) {

        }
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
