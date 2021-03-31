package main;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GraphicMaze {

    private final int DISPLAY_OFFSET = 5;
    private List<List<GraphicTile>> graphicMaze;

    public GraphicMaze(Pane root, int mazeWidth, int cellWidth) {
        graphicMaze = new ArrayList<>();

        for (int i = 0; i < mazeWidth; i++) {
            graphicMaze.add(new ArrayList<>());
            for (int j = 0; j < mazeWidth; j++) {
                graphicMaze.get(i).add(new GraphicTile(i, j, cellWidth));
                root.getChildren().add(graphicMaze.get(i).get(j).getGroup());
            }
        }
    }

    public GraphicTile get(int row, int col) {
        return graphicMaze.get(row).get(col);
    }

    public class GraphicTile {
        private Line northWall;
        private Line eastWall;
        private Line southWall;
        private Line westWall;
        private Rectangle square;
        private Group group;

        public GraphicTile(int row, int col, int cellWidth) {
            int leftEdge = DISPLAY_OFFSET + (row * cellWidth);
            int rightEdge = DISPLAY_OFFSET + ((row+1) * cellWidth);
            int topEdge = DISPLAY_OFFSET + (col * cellWidth);
            int bottomEdge = DISPLAY_OFFSET + ((col+1) * cellWidth);

            northWall = new Line(leftEdge, topEdge, rightEdge, topEdge);
            eastWall = new Line(rightEdge, topEdge, rightEdge, bottomEdge);
            southWall = new Line(rightEdge, bottomEdge, leftEdge, bottomEdge);
            westWall = new Line(leftEdge, bottomEdge, leftEdge, topEdge);

            northWall.setStroke(Color.BLACK);
            eastWall.setStroke(Color.BLACK);
            southWall.setStroke(Color.BLACK);
            westWall.setStroke(Color.BLACK);

            square = new Rectangle(cellWidth, cellWidth, Color.WHITE);
            square.setTranslateX(rightEdge);
            square.setTranslateY(topEdge);

            group = new Group(northWall, eastWall, southWall, westWall, square);
        }

        public Group getGroup() {
            return group;
        }

        public Line getEastWall() {
            return eastWall;
        }

        public Line getSouthWall() {
            return southWall;
        }

        public Line getNorthWall() {
            return northWall;
        }

        public Line getWestWall() {
            return westWall;
        }

        public void setColor(Color color) {
            square.setFill(color);
        }

        public void setNorthColor(Color color) {
            northWall.setStroke(color);
        }

        public void setEastColor(Color color) {
            eastWall.setStroke(color);
        }

        public void setSouthColor(Color color) {
            southWall.setStroke(color);
        }

        public void setWestColor(Color color) {
            westWall.setStroke(color);
        }
    }
}
