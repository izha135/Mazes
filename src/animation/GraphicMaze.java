/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The graphical representation of the maze, manipulated by animations
 */

package animation;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GraphicMaze {

    // The number of pixels off the walls of the display the maze should be
    private final int DISPLAY_OFFSET = 5;
    // The internal representation of the maze
    private List<List<GraphicTile>> graphicMaze;
    // The width (number of cells) in the maze
    private int mazeWidth;

    /**
     * Creates a GraphicalMaze object with cells and edges, then
     * adds each of these elements, by reference, to a given Pane
     * @param root The Pane to add the maze to
     * @param mazeWidth The number of cells the maze is wide
     * @param cellWidth The pixel width of the cells
     */
    public GraphicMaze(Pane root, int mazeWidth, int cellWidth) {
        this.mazeWidth = mazeWidth;
        graphicMaze = new ArrayList<>();

        for (int i = 0; i < mazeWidth; i++) {
            graphicMaze.add(new ArrayList<>());
            for (int j = 0; j < mazeWidth; j++) {
                graphicMaze.get(i).add(new GraphicTile(i, j, cellWidth));
                root.getChildren().add(graphicMaze.get(i).get(j).getGroup());
            }
        }
    }

    /**
     * @return The width (number of cells) in the maze
     */
    public int getWidth() {
        return mazeWidth;
    }

    /**
     * Returns the reference of a specific cell in the board
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The reference of the cell at (row,col)
     */
    public GraphicTile get(int row, int col) {
        return graphicMaze.get(row).get(col);
    }

    public class GraphicTile {
        // The line at the top of a given cell
        private Line northWall;
        // The line at the right of a given cell
        private Line eastWall;
        // The line at the bottom of a given cell
        private Line southWall;
        // The line at the left of a given cell
        private Line westWall;
        // The cell's square
        private Rectangle square;
        // The group of all of these elements to display
        private Group group;

        /**
         * Creates a tile/cell of the maze
         * @param row The row index of the cell
         * @param col The column index of the cell
         * @param cellWidth The pixel width of the cell
         */
        public GraphicTile(int row, int col, int cellWidth) {
            // Pixel indices of the walls
            int topEdge = DISPLAY_OFFSET + (row * cellWidth);
            int bottomEdge = DISPLAY_OFFSET + ((row+1) * cellWidth);
            int leftEdge = DISPLAY_OFFSET + (col * cellWidth);
            int rightEdge = DISPLAY_OFFSET + ((col+1) * cellWidth);

            // Create the walls of the cell/tile
            // The 0.5 shifts make the lines not overlap as much
            northWall = new Line(leftEdge+0.5, topEdge, rightEdge-0.5, topEdge);
            eastWall = new Line(rightEdge, topEdge+0.5, rightEdge, bottomEdge-0.5);
            southWall = new Line(rightEdge-0.5, bottomEdge, leftEdge+0.5, bottomEdge);
            westWall = new Line(leftEdge, bottomEdge-0.5, leftEdge, topEdge+0.5);

            // Make all the walls black
            northWall.setStroke(Color.BLACK);
            eastWall.setStroke(Color.BLACK);
            southWall.setStroke(Color.BLACK);
            westWall.setStroke(Color.BLACK);

            // Sets the background square on the cell/tile
            square = new Rectangle(cellWidth, cellWidth, Color.WHITE);
            square.setTranslateX(leftEdge);
            square.setTranslateY(topEdge);

            group = new Group(northWall, eastWall, southWall, westWall, square);
        }

        /**
         * @return The group of the cell square and the edges
         */
        public Group getGroup() {
            return group;
        }

        /**
         * Sets the color of the cell
         * @param color The specified color
         */
        public void setColor(Color color) {
            square.setFill(color);
        }

        /**
         * Set the north wall at a specified color
         * @param color The specified color
         */
        public void setNorthColor(Color color) {
            northWall.setStroke(color);
        }

        /**
         * Set the east wall at a specified color
         * @param color The specified color
         */
        public void setEastColor(Color color) {
            eastWall.setStroke(color);
        }

        /**
         * Set the south wall at a specified color
         * @param color The specified color
         */
        public void setSouthColor(Color color) {
            southWall.setStroke(color);
        }

        /**
         * Set the west wall at a specified color
         * @param color The specified color
         */
        public void setWestColor(Color color) {
            westWall.setStroke(color);
        }
    }
}
