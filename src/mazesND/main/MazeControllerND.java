/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The controller class of the maze generation, solving, and animation
 */

package mazesND.main;

import mazesND.animation.AllAnimationND;
import mazesND.animation.TileAnimationND;
import mazesND.animation.WaitAnimationND;
import mazesND.generators.GeneratorEnumND;
import mazesND.generators.GeneratorFactoryND;
import mazesND.generators.MazeGeneratorND;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;
import mazesND.animation.AnimationND;
import mazesND.animation.GraphicMazeND;
import mazesND.solvers.MazeSolverND;
import mazesND.solvers.SolverEnumND;
import mazesND.solvers.SolverFactoryND;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MazeControllerND extends Application {
    static List<AnimationND> animationList;
    static int screenWidth;
    static int screenHeight;
    static int cellWidth;
    static int mazeCellWidth;
    static int dimension;
    static GeneratorEnumND generator;
    static SolverEnumND solver;

    static final double scale = 0.03;

    public static void main(String[] args) {
        animationList = new LinkedList<>();

        setDefaultValues();
        /*if(args.length == 0) {
            System.out.println("Error: Please include filename as argument");
            return;
        }
        setFileValues(args[0]);*/

        MazeND maze = new MazeND(mazeCellWidth, dimension);

        MazeGeneratorND generatorND = GeneratorFactoryND.getGenerator(generator, dimension);
        MazeSolverND solverND = SolverFactoryND.getSolver(solver, dimension);

        animationList.addAll(generatorND.generateMaze(maze));
        animationList.add(new AllAnimationND(Color.TRANSPARENT));
        animationList.addAll(setStartAndEnd(maze));
        animationList.add(new WaitAnimationND(10));
        animationList.addAll(solverND.solveMaze(maze));

        launch(args);
    }

    public static void setDefaultValues() {
        dimension = 6;
        screenHeight = 800;
        cellWidth = 100;
        // KRUSTAL, ALDOUS, PRIM, PRIMMOD, DFS;
        generator = GeneratorEnumND.KRUSTAL;
        // ASTAR, MOUSE, PLEDGE, ROUTING, TREMAUX, WALL
        solver = SolverEnumND.ASTAR;


        screenWidth = screenHeight;
        mazeCellWidth = screenWidth / (cellWidth * 2) - 1;
        System.out.println("Maze width: " + mazeCellWidth);
    }

    public static void setFileValues(String filename) {
        String str = "";
        Scanner fileScan = null;
        try {
            fileScan = new Scanner(
                    new BufferedReader(
                            new FileReader(filename)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        dimension = fileScan.nextInt();
        screenHeight = fileScan.nextInt();
        cellWidth = fileScan.nextInt();

        // KRUSTAL, ALDOUS, PRIM, PRIMMOD, DFS, (REC)
        str = fileScan.next();
        if(str.equals("krustal")) generator = GeneratorEnumND.KRUSTAL;
        if(str.equals("aldous"))  generator = GeneratorEnumND.ALDOUS;
        if(str.equals("prim"))    generator = GeneratorEnumND.PRIM;
        if(str.equals("primmod")) generator = GeneratorEnumND.PRIMMOD;
        if(str.equals("dfs"))     generator = GeneratorEnumND.DFS;

        // ASTAR, MOUSE, PLEDGE, ROUTING, TREMAUX, WALL
        str = fileScan.next();
        if(str.equals("astar"))   solver = SolverEnumND.ASTAR;
        if(str.equals("mouse"))   solver = SolverEnumND.MOUSE;
        if(str.equals("pledge"))  solver = SolverEnumND.PLEDGE;
        if(str.equals("routing")) solver = SolverEnumND.ROUTING;
        if(str.equals("tremaux")) solver = SolverEnumND.TREMAUX;
        if(str.equals("wall"))    solver = SolverEnumND.WALL;

        screenWidth = screenHeight;
        mazeCellWidth = screenWidth / (cellWidth * 2) - 1;
        System.out.println("Maze width: " + mazeCellWidth);
    }

    public static List<AnimationND> setStartAndEnd(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        List<MazeTileND> tileList = maze.collapseMaze();
        Collections.shuffle(tileList);

        boolean hasStart = false;
        int component = -1;
        boolean isZero = true;
        for(MazeTileND tile : tileList) {
            if (hasStart) {
                if(isZero) {
                    if(tile.getPosition().get(component) == mazeCellWidth-1) {
                        maze.setEndTile(tile.getPosition());
                        animList.add(new TileAnimationND(tile.getPosition(), Color.RED));
                        break;
                    }
                }
                else {
                    if(tile.getPosition().get(component) == 0) {
                        maze.setEndTile(tile.getPosition());
                        animList.add(new TileAnimationND(tile.getPosition(), Color.RED));
                        break;
                    }
                }
            }
            else {
                if(tile.getPosition().contains(0) && tile.getPosition().indexOf(0) < dimension) {
                    component = tile.getPosition().indexOf(0);
                    isZero = true;
                    hasStart = true;
                    maze.setStartTile(tile.getPosition());
                    animList.add(new TileAnimationND(tile.getPosition(), Color.BLUE));
                }
                if(tile.getPosition().contains(mazeCellWidth-1) && tile.getPosition().indexOf(mazeCellWidth-1) < dimension) {
                    component = tile.getPosition().indexOf(mazeCellWidth-1);
                    isZero = false;
                    hasStart = true;
                    maze.setStartTile(tile.getPosition());
                    animList.add(new TileAnimationND(tile.getPosition(), Color.BLUE));
                }
            }
        }

        return animList;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        GraphicMazeND graphicMaze = new GraphicMazeND(root, mazeCellWidth, cellWidth, dimension);

        // MAKE THE CAMERA

        boolean is3DSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        if(!is3DSupported) {
            System.out.println("Sorry, 3D is not supported in JavaFX on this platform.");
            return;
        }

        // Create and position camera
        float shiftZ = dimension >= 3? screenWidth/4 : 0;

        PerspectiveCamera camera = new PerspectiveCamera(false);
        Rotate rotateX = new Rotate(120, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        Translate translate = new Translate(screenWidth/4, screenHeight/4, shiftZ);
        Translate translate2 = new Translate(-screenWidth/2, -screenHeight/2, 200);
        camera.getTransforms().addAll (
                translate,
                rotateX,
                rotateY,
                translate2
        );

        // \MAKE THE CAMERA

        Scene scene = new Scene(root, screenWidth, screenHeight, true);
        scene.setCamera(camera);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Animation");

        primaryStage.show();

        // SET MOUSE CONTROLS

        scene.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {
                graphicMaze.flipWalls();
            }
            else {
                graphicMaze.flipOuterWalls();
            }
        });

        scene.setOnMouseMoved(event -> {
            rotateX.setAngle(-(200.0/screenHeight) * (event.getSceneY() - screenWidth/2));
            rotateY.setAngle((400.0/screenWidth) * (event.getSceneX() - screenWidth/2));
        });

        scene.setOnMouseDragged(event -> {
            rotateX.setAngle(-(200.0/screenHeight) * (event.getSceneY() - screenWidth/2));
            rotateY.setAngle((400.0/screenWidth) * (event.getSceneX() - screenWidth/2));
        });

        scene.setOnScroll(event -> {
            translate2.setZ(translate2.getZ() + event.getDeltaY() * 2);
        });

        // \SET MOUSE CONTROLS

        AnimationTimer at = new AnimationTimer() {
            double animTime;
            private long lastEventTime = -1;

            @Override
            public void handle(long now) {
                if (lastEventTime == -1) lastEventTime = now;

                if (!animationList.isEmpty()) animTime = 1_000_000_000l * scale * animationList.get(0).getAnimateTime();
                while (!animationList.isEmpty() && now > animTime + lastEventTime) {
                    animationList.get(0).animate(graphicMaze);
                    animationList.remove(0);
                    lastEventTime += animTime;
                    if(!animationList.isEmpty()) {
                        animTime = 1_000_000_000l * scale * animationList.get(0).getAnimateTime();
                    }
                }
            }
        };

        at.start();
    }
}
