/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The graphical representation of the maze, manipulated by animations
 */

package mazesND.animation;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphicMazeND {

    private enum Walls{
        SHOWING, OUTER, ALL
    }

    private Walls areWallsShown;
    // The number of pixels off the walls of the display the maze should be
    private final int DISPLAY_OFFSET = 5;
    // The internal representation of the maze
    private List<List<List<List<List<List<GraphicTile>>>>>> graphicMaze;

    /**
     * Creates a GraphicalMaze object with cells and edges, then
     * adds each of these elements, by reference, to a given Pane
     * @param root The Pane to add the maze to
     * @param mazeWidth The number of cells the maze is wide
     * @param cellWidth The pixel width of the cells
     */
    public GraphicMazeND(Pane root, int mazeWidth, int cellWidth, int dimension) {
        graphicMaze = new ArrayList<>();

        List<Integer> tempList = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0));
        int iLim = mazeWidth;
        int jLim = mazeWidth;
        int kLim = dimension > 2? mazeWidth: 1;
        int lLim = dimension > 3? mazeWidth: 1;
        int mLim = dimension > 4? mazeWidth: 1;
        int nLim = dimension > 5? mazeWidth: 1;

        GraphicTile gTile;

        for (int i = 0; i < iLim; i++) {
            graphicMaze.add(new ArrayList<>());
            tempList.set(0, i);
            for (int j = 0; j < jLim; j++) {
                graphicMaze.get(i).add(new ArrayList<>());
                tempList.set(1, j);
                for (int k = 0; k < kLim; k++) {
                    graphicMaze.get(i).get(j).add(new ArrayList<>());
                    tempList.set(2, k);
                    for (int l = 0; l < lLim; l++) {
                        graphicMaze.get(i).get(j).get(k).add(new ArrayList<>());
                        tempList.set(3, l);
                        for (int m = 0; m < mLim; m++) {
                            graphicMaze.get(i).get(j).get(k).get(l).add(new ArrayList<>());
                            tempList.set(4, m);
                            for (int n = 0; n < nLim; n++) {
                                tempList.set(5, n);
                                gTile = new GraphicTile(tempList, cellWidth, mazeWidth, dimension);
                                graphicMaze.get(i).get(j).get(k).get(l).get(m).add(gTile);
                                root.getChildren().add(gTile.getGroup());
                            }
                        }
                    }
                }
            }
        }

        areWallsShown = Walls.SHOWING;
    }

    public GraphicTile get(List<Integer> pos) {
        int X, Y, Z, W, V, T;
        X = pos.get(0);
        Y = pos.get(1);
        if (pos.size() >= 2) Z = pos.get(2);
        else                 Z = 0;
        if (pos.size() >= 3) W = pos.get(3);
        else                 W = 0;
        if (pos.size() >= 4) V = pos.get(4);
        else                 V = 0;
        if (pos.size() >= 5) T = pos.get(5);
        else                 T = 0;

        return graphicMaze.get(X).get(Y).get(Z).get(W).get(V).get(T);
    }

    public void flipWalls() {
        if(areWallsShown != Walls.ALL) {
            clearWalls();
            areWallsShown = Walls.ALL;
        }
        else {
            showWalls();
            areWallsShown = Walls.SHOWING;
        }
    }

    public void flipOuterWalls() {
        if(areWallsShown != Walls.OUTER) {
            showWalls();
            clearOuterWalls();
            areWallsShown = Walls.OUTER;
        }
        else {
            showWalls();
            areWallsShown = Walls.SHOWING;
        }
    }

    public void clearWalls() {
        for(List<List<List<List<List<GraphicTile>>>>> i : graphicMaze) {
            for(List<List<List<List<GraphicTile>>>> j : i) {
                for(List<List<List<GraphicTile>>> k : j) {
                    for(List<List<GraphicTile>> l : k) {
                        for(List<GraphicTile> m : l) {
                            for(GraphicTile g : m) {
                                g.clearWalls();
                            }
                        }
                    }
                }
            }
        }
    }

    public void clearOuterWalls() {
        for(List<List<List<List<List<GraphicTile>>>>> i : graphicMaze) {
            for(List<List<List<List<GraphicTile>>>> j : i) {
                for(List<List<List<GraphicTile>>> k : j) {
                    for(List<List<GraphicTile>> l : k) {
                        for(List<GraphicTile> m : l) {
                            for(GraphicTile g : m) {
                                g.clearOuterWalls();
                            }
                        }
                    }
                }
            }
        }
    }

    public void showWalls() {
        for(List<List<List<List<List<GraphicTile>>>>> i : graphicMaze) {
            for(List<List<List<List<GraphicTile>>>> j : i) {
                for(List<List<List<GraphicTile>>> k : j) {
                    for(List<List<GraphicTile>> l : k) {
                        for(List<GraphicTile> m : l) {
                            for(GraphicTile g : m) {
                                g.showWalls();
                            }
                        }
                    }
                }
            }
        }
    }

    public List<GraphicTile> collapseMaze() {
        List<Integer> tempList = new LinkedList<>(List.of(0, 0, 0, 0, 0, 0));
        List<GraphicTile> tempRetList = new ArrayList<>();
        for (int i = 0; i < graphicMaze.size(); i++) {
            tempList.set(0, i);
            for (int j = 0; j < graphicMaze.get(i).size(); j++) {
                tempList.set(1, j);
                for (int k = 0; k < graphicMaze.get(i).get(j).size(); k++) {
                    tempList.set(2, k);
                    for (int l = 0; l < graphicMaze.get(i).get(j).get(k).size(); l++) {
                        tempList.set(3, l);
                        for (int m = 0; m < graphicMaze.get(i).get(j).get(k).get(l).size(); m++) {
                            tempList.set(4, m);
                            for (int n = 0; n < graphicMaze.get(i).get(j).get(k).get(l).get(m).size(); n++) {
                                tempList.set(5, n);
                                tempRetList.add(get(tempList));
                            }
                        }
                    }
                }
            }
        }
        return tempRetList;
    }

    public class GraphicTile {
        private List<Integer> position;
        private int mazeWidth;
        // The line at the top of a given cell
        private MeshView northWall;
        // The line at the right of a given cell
        private MeshView eastWall;
        // The line at the bottom of a given cell
        private MeshView southWall;
        // The line at the left of a given cell
        private MeshView westWall;
        // The line at the bottom of a given cell
        private MeshView aboveWall;
        // The line at the left of a given cell
        private MeshView belowWall;
        // The cell's square
        private MeshView square;
        // The group of all of these elements to display
        private Group group;

        public GraphicTile(List<Integer> pos, int cellWidth, int mazeWidth, int dimension) {
            position = new ArrayList<>(pos);
            this.mazeWidth = mazeWidth;

            int X, Y, Z, W, V, T;
            X = pos.get(0);
            Y = pos.get(1);
            if (pos.size() >= 2) Z = pos.get(2);
            else                 Z = 0;
            if (pos.size() >= 3) W = pos.get(3);
            else                 W = 0;
            if (pos.size() >= 4) V = pos.get(4);
            else                 V = 0;
            if (pos.size() >= 5) T = pos.get(5);
            else                 T = 0;

            // Pixel indices of the walls
            int cubeOffset = cellWidth * (mazeWidth + 1);

            int topEdge = DISPLAY_OFFSET + (X * cellWidth) + (W * cubeOffset);
            int bottomEdge = DISPLAY_OFFSET + ((X+1) * cellWidth) + (W * cubeOffset);
            int leftEdge = DISPLAY_OFFSET + (Y * cellWidth) + (V * cubeOffset);
            int rightEdge = DISPLAY_OFFSET + ((Y+1) * cellWidth) + (V * cubeOffset);
            int aboveEdge = DISPLAY_OFFSET + (Z * cellWidth) + (T * cubeOffset);
            int belowEdge = DISPLAY_OFFSET + ((Z+1) * cellWidth) + (T * cubeOffset);


            // Create the walls of the cell/tile
            // The 0.5 shifts make the lines not overlap as much
            TriangleMesh tMesh;
            Color cellColor = new Color(0.5, 0.5, 0.5, 1);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(cellColor);
            material.setSpecularColor(cellColor);

            PhongMaterial transMaterial = new PhongMaterial();
            transMaterial.setDiffuseColor(Color.TRANSPARENT);
            transMaterial.setSpecularColor(Color.TRANSPARENT);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge, topEdge, aboveEdge,
                    rightEdge, topEdge, aboveEdge,
                    rightEdge, topEdge, belowEdge,
                    leftEdge, topEdge, belowEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            northWall = new MeshView(tMesh);
            northWall.setDrawMode(DrawMode.FILL);
            northWall.setMaterial(material);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge, bottomEdge, aboveEdge,
                    rightEdge, bottomEdge, aboveEdge,
                    rightEdge, bottomEdge, belowEdge,
                    leftEdge, bottomEdge, belowEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            southWall = new MeshView(tMesh);
            southWall.setDrawMode(DrawMode.FILL);
            southWall.setMaterial(material);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge, topEdge, aboveEdge,
                    leftEdge, bottomEdge, aboveEdge,
                    leftEdge, bottomEdge, belowEdge,
                    leftEdge, topEdge, belowEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            westWall = new MeshView(tMesh);
            westWall.setDrawMode(DrawMode.FILL);
            westWall.setMaterial(material);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    rightEdge, topEdge, aboveEdge,
                    rightEdge, bottomEdge, aboveEdge,
                    rightEdge, bottomEdge, belowEdge,
                    rightEdge, topEdge, belowEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            eastWall = new MeshView(tMesh);
            eastWall.setDrawMode(DrawMode.FILL);
            eastWall.setMaterial(material);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge, topEdge, aboveEdge,
                    rightEdge, topEdge, aboveEdge,
                    rightEdge, bottomEdge, aboveEdge,
                    leftEdge, bottomEdge, aboveEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            aboveWall = new MeshView(tMesh);
            aboveWall.setDrawMode(DrawMode.FILL);
            if (dimension != 2) aboveWall.setMaterial(material);
            else {
                aboveWall.setMaterial(transMaterial);
            }

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge, topEdge, belowEdge,
                    rightEdge, topEdge, belowEdge,
                    rightEdge, bottomEdge, belowEdge,
                    leftEdge, bottomEdge, belowEdge
            );
            tMesh.getFaces().addAll(
                    0,0, 1,0, 2,0,
                    2,0, 3,0, 0,0,
                    0,0, 2,0, 1,0,
                    2,0, 0,0, 3,0
            );
            belowWall = new MeshView(tMesh);
            belowWall.setDrawMode(DrawMode.FILL);
            if (dimension != 2) belowWall.setMaterial(material);
            else {
                belowWall.setMaterial(transMaterial);
            }

            float offset = cellWidth/4;
            PhongMaterial squareMaterial = new PhongMaterial();
            squareMaterial.setDiffuseColor(Color.RED);
            squareMaterial.setSpecularColor(Color.RED);

            tMesh = new TriangleMesh();
            tMesh.getTexCoords().clear();
            tMesh.getPoints().clear();
            tMesh.getPoints().clear();
            tMesh.getTexCoords().addAll(0,0);
            tMesh.getPoints().addAll(
                    leftEdge+offset, topEdge+offset, belowEdge-offset,
                    leftEdge+offset, topEdge+offset, aboveEdge+offset,
                    rightEdge-offset, topEdge+offset, belowEdge-offset,
                    rightEdge-offset, topEdge+offset, aboveEdge+offset,
                    leftEdge+offset, bottomEdge-offset, belowEdge-offset,
                    leftEdge+offset, bottomEdge-offset, aboveEdge+offset,
                    rightEdge-offset, bottomEdge-offset, belowEdge-offset,
                    rightEdge-offset, bottomEdge-offset, aboveEdge+offset
            );
            tMesh.getFaces().addAll(
                    1,0, 3,0, 2,0,
                    2,0, 0,0, 1,0,
                    5,0, 6,0, 7,0,
                    6,0, 5,0, 4,0,

                    5,0, 3,0, 1,0,
                    3,0, 5,0, 7,0,
                    4,0, 0,0, 2,0,
                    2,0, 6,0, 4,0,

                    1,0, 4,0, 5,0,
                    0,0, 4,0, 1,0,
                    3,0, 7,0, 6,0,
                    2,0, 3,0, 6,0
            );
            square = new MeshView(tMesh);
            square.setDrawMode(DrawMode.FILL);
            square.setMaterial(squareMaterial);


            group = new Group();
            group.getChildren().add(northWall);
            group.getChildren().add(eastWall);
            group.getChildren().add(southWall);
            group.getChildren().add(westWall);
            if (dimension != 2) {
                group.getChildren().add(aboveWall);
                group.getChildren().add(belowWall);
            }
            group.getChildren().add(square);
        }

        public Group getGroup() {
            return group;
        }

        public MeshView getEastWall() {
            return eastWall;
        }

        public MeshView getSouthWall() {
            return southWall;
        }

        public MeshView getNorthWall() {
            return northWall;
        }

        public MeshView getWestWall() {
            return westWall;
        }

        public MeshView getAboveWall() {
            return northWall;
        }

        public MeshView getBelowWall() {
            return westWall;
        }

        public void setColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            square.setMaterial(material);
            if(color.getOpacity() == 0.0) square.setOpacity(0.0);
            else                          square.setOpacity(1.0);
        }

        public void setNorthColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            northWall.setMaterial(material);
            if(color.getOpacity() == 0.0) northWall.setOpacity(0.0);
            else                          northWall.setOpacity(1.0);
        }

        public void setEastColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            eastWall.setMaterial(material);
            if(color.getOpacity() == 0.0) eastWall.setOpacity(0.0);
            else                          eastWall.setOpacity(1.0);
        }

        public void setSouthColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            southWall.setMaterial(material);
            if(color.getOpacity() == 0.0) southWall.setOpacity(0.0);
            else                          southWall.setOpacity(1.0);
        }

        public void setWestColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            westWall.setMaterial(material);
            if(color.getOpacity() == 0.0) westWall.setOpacity(0.0);
            else                          westWall.setOpacity(1.0);
        }

        public void setAboveColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            aboveWall.setMaterial(material);
            if(color.getOpacity() == 0.0) aboveWall.setOpacity(0.0);
            else                          aboveWall.setOpacity(1.0);

        }

        public void setBelowColor(Color color) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(color);
            material.setSpecularColor(color);
            belowWall.setMaterial(material);
            if(color.getOpacity() == 0.0) belowWall.setOpacity(0.0);
            else                          belowWall.setOpacity(1.0);
        }

        public void clearWalls() {
            northWall.setOpacity(0.0);
            southWall.setOpacity(0.0);
            eastWall.setOpacity(0.0);
            westWall.setOpacity(0.0);
            aboveWall.setOpacity(0.0);
            belowWall.setOpacity(0.0);
        }

        public void clearOuterWalls() {
            if(position.get(0) == 0) northWall.setOpacity(0.0);
            if(position.get(0) == mazeWidth-1) southWall.setOpacity(0.0);
            if(position.get(1) == mazeWidth-1) eastWall.setOpacity(0.0);
            if(position.get(1) == 0) westWall.setOpacity(0.0);
            if(position.get(2) == 0) aboveWall.setOpacity(0.0);
            if(position.get(2) == mazeWidth-1) belowWall.setOpacity(0.0);
        }

        public void showWalls() {
            if(((PhongMaterial) northWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                northWall.setOpacity(1.0);
            if(((PhongMaterial) southWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                southWall.setOpacity(1.0);
            if(((PhongMaterial) eastWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                eastWall.setOpacity(1.0);
            if(((PhongMaterial) westWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                westWall.setOpacity(1.0);
            if(((PhongMaterial) aboveWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                aboveWall.setOpacity(1.0);
            if(((PhongMaterial) belowWall.getMaterial()).getDiffuseColor().getOpacity() > 0.01)
                belowWall.setOpacity(1.0);
        }
    }
}
