/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The controller class of the maze generation, solving, and animation
 */

package main;

import animation.*;
import generators.GeneratorEnum;
import generators.GeneratorFactory;
import generators.MazeGenerator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import maze.Maze;
import maze.MazeTile;
import solvers.MazeSolver;
import solvers.SolverEnum;
import solvers.SolverFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MazeController extends Application {
    // The list of animations to animate
    private static List<Animation> animationList;
    // The width of the screen (pixels)
    private static int screenWidth;
    // The height of the screen (pixels)
    private static int screenHeight;
    // The width of each cell (pixels)
    private static int cellWidth;
    // The width of the maze (number of cells)
    private static int mazeCellWidth;
    // The maze object to generate on and solve
    private static Maze maze;
    // The generator that decides which generator to use
    private static GeneratorEnum generator;
    // The value which decides which solver to use
    private static SolverEnum solver;

    // The speed multiplier of the animations
    static double scale = 0.1;

    public static void main(String[] args) {
        animationList = new LinkedList<>();

        // Set the variables for what generator to use, the width, etc.
        setDefaultValues();
        /*if(args.length == 0) {
            System.out.println("Please include the filename as argument 0");
            return;
        }
        setFileValues(args[0]);*/

        // Create the objects for generation and soling
        maze = new Maze(mazeCellWidth, true);
        MazeGenerator gen = GeneratorFactory.getGenerator(generator);
        if(gen == null) {
            System.out.println("That generator is not supported");
            // Stop executing
            return;
        }
        MazeSolver solve = SolverFactory.getSolver(solver);
        if(solve == null) {
            System.out.println("That solver is not supported");
            // Stop executing
            return;
        }

        // Generate and solve the maze, saving all of the animations
        animationList.addAll(gen.generate(maze));
        animationList.add(new AllAnimation(Color.WHITE));
        animationList.addAll(setStartEnd(maze));
        animationList.add(new WaitAnimation(10));
        animationList.addAll(solve.solve(maze));

        launch(args);
    }

    /**
     * Sets the parameters of the maze, solver, and generator to default values
     */
    private static void setDefaultValues() {
        screenHeight = 700;
        screenWidth = screenHeight;
        cellWidth = 4;
        mazeCellWidth = screenWidth / cellWidth;

        generator = GeneratorEnum.KRUSTAL;
        solver = SolverEnum.ASTAR;
        scale = 3.0 / mazeCellWidth;
    }

    /**
     * Set the parameters of the maze, solver, and generator based on values in the file
     * @param fileName The file name with the parameters
     */
    private static void setFileValues(String fileName) {
        Scanner fileScan = null;
        try {
            fileScan = new Scanner (
                    new BufferedReader(
                            new FileReader(fileName)));
            //                new FileReader("resources/params.txt")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        screenHeight = fileScan.nextInt() + 10;
        screenWidth = screenHeight;
        cellWidth = fileScan.nextInt();
        mazeCellWidth = screenWidth / cellWidth - 1;

        generator = GeneratorEnum.getEnum(fileScan.next());
        solver = SolverEnum.getEnum(fileScan.next());

        scale = 3.0 / mazeCellWidth;
    }

    /**
     * Sets the start tile and end tile of the maze.
     * Black - The start
     * Red - The end
     * @param maze The maze to set the start and end tile of
     * @return The animations to set the colors of the start and end
     */
    private static List<Animation> setStartEnd(Maze maze) {
        List<Animation> animList = new ArrayList<>();
        List<MazeTile> tileList = maze.collapseMaze();
        Collections.shuffle(tileList);
        int width = maze.getWidth();

        boolean hasSetStart = false;
        boolean isNorthSouth = true;
        boolean isMaxValue = true;

        for(MazeTile tile : tileList) {
            if(hasSetStart) {
                // Finding the end cell on the opposite edge of the start cell
                if(tile.getCol() == 0 && !isNorthSouth && isMaxValue) {
                    maze.setEndTile(tile.getRow(), tile.getCol());
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.RED));
                    return animList;
                }
                if(tile.getCol() == width-1 && !isNorthSouth && !isMaxValue) {
                    maze.setEndTile(tile.getRow(), tile.getCol());
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.RED));
                    return animList;
                }
                if(tile.getRow() == 0 && isNorthSouth && isMaxValue) {
                    maze.setEndTile(tile.getRow(), tile.getCol());
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.RED));
                    return animList;
                }
                if(tile.getRow() == width-1 && isNorthSouth && !isMaxValue) {
                    maze.setEndTile(tile.getRow(), tile.getCol());
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.RED));
                    return animList;
                }
            }
            else {
                // Finding the start cell
                if(tile.getCol() == 0) {
                    hasSetStart = true;
                    maze.setStartTile(tile.getRow(), tile.getCol());
                    isNorthSouth = false;
                    isMaxValue = false;
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.BLACK));
                }
                if(tile.getCol() == width-1) {
                    hasSetStart = true;
                    maze.setStartTile(tile.getRow(), tile.getCol());
                    isNorthSouth = false;
                    isMaxValue = true;
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.BLACK));
                }
                if(tile.getRow() == 0) {
                    hasSetStart = true;
                    maze.setStartTile(tile.getRow(), tile.getCol());
                    isNorthSouth = true;
                    isMaxValue = false;
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.BLACK));
                }
                if(tile.getRow() == width-1) {
                    hasSetStart = true;
                    maze.setStartTile(tile.getRow(), tile.getCol());
                    isNorthSouth = true;
                    isMaxValue = true;
                    animList.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.BLACK));
                }
            }
        }
        return animList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the pane
        Pane root = new Pane();

        // Initialize the graphical maze
        GraphicMaze graphicMaze = new GraphicMaze(root, mazeCellWidth, cellWidth);

        Scene scene = new Scene(root, screenWidth+10, screenHeight+10, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Animation");

        primaryStage.show();

        AnimationTimer at = new AnimationTimer() {
            double animTime;
            private long lastEventTime = -1;

            @Override
            public void handle(long now) {
                // Set the start of the animation
                if (lastEventTime == -1) lastEventTime = now;

                // Play the animations
                if (!animationList.isEmpty()) animTime = 1_000_000_000l * scale * animationList.get(0).getAnimateTime();
                // Play all animations that needed to be run in the past frame
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
