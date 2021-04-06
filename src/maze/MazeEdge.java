/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * A data-structure useful in representing the edges of the maze since
 * the standard internal representation refers to cells, not walls
 */

package maze;

public class MazeEdge {
    // The row and column of the tile above or to the left of the tile
    private int row, col;
    // true if the edge is below the specified tile. false if the edge to the right
    private boolean isSouth;

    /**
     * The data structure to specify an edge in the maze
     * @param row The row of the tile above or to the left of the tile
     * @param col The column of the tile above or to the left of the tile
     * @param isSouth true if the edge is below the specified tile. false if the edge to the right
     */
    public MazeEdge(int row, int col, boolean isSouth) {
        this.row = row;
        this.col = col;
        this.isSouth = isSouth;
    }

    /**
     * @return The column of the tile above or to the left of the tile
     */
    public int getCol() {
        return col;
    }

    /**
     * @return The row of the tile above or to the left of the tile
     */
    public int getRow() {
        return row;
    }

    /**
     * @return true if the edge is below the specified tile. false if the edge to the right
     */
    public boolean isSouth() {
        return isSouth;
    }
}
