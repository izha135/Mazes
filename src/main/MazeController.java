/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The controller class of the maze generation, solving, and animation
 */

package main;

import animation.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MazeController extends Application {
    static List<Animation> animationList;
    static int screenWidth;
    static int screenHeight;
    static int cellWidth;
    static int mazeCellWidth;

    static final double scale = 0.1;

    public static void main(String[] args) {
        animationList = new LinkedList<>();

        setDefaultValues();

        // THIS IS TEMPORARY vvv

        for (int i = 0; i < mazeCellWidth-1; i++) {
            animationList.add(new TileAnimation(i, (i*17)%39, Color.AQUA));
            for (int j = 0; j < mazeCellWidth; j++) {
                animationList.add(new EdgeAnimation(i, j, true, Color.TRANSPARENT));
                animationList.add(new EdgeAnimation(j, i, false, Color.TRANSPARENT));
            }
        }

        // THIS IS TEMPORARY ^^^

        launch(args);
    }

    public static void setDefaultValues() {
        screenHeight = 400;
        screenWidth = screenHeight;
        cellWidth = 10;
        mazeCellWidth = screenWidth / cellWidth - 1;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        GraphicMaze graphicMaze = new GraphicMaze(root, mazeCellWidth, cellWidth);

        Scene scene = new Scene(root, screenWidth, screenHeight, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Animation");

        primaryStage.show();

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
