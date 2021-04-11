/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The controller of random mouse solving thread
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MouseThreadSolver implements MazeSolver{
    /**
     * Starts a controller for the random mouse solving method
     */
    public MouseThreadSolver() {

    }

    @Override
    public List<Animation> solve(Maze maze) {
        // The queue of animations to animate
        BlockingQueue<Animation> animQueue = new LinkedBlockingQueue<>();

        // The pool of the threads in the solving
        ExecutorService pool = Executors.newFixedThreadPool(1000);

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        // The map is the traversal map
        MouseThread.initMap();
        // Start one thread at the beginning...
        MouseThread.createAndRun(startTile.getRow(), startTile.getCol(), pool, maze, animQueue, null);

        // Wait until the solution is found and all the threads have ended
        while (!pool.isTerminated()) {}

        List<Animation> animList = new ArrayList<>();

        // Insert all of the animations into a list
        for(Animation anim : animQueue) {
            animList.add(anim);
        }

        ConcurrentMap<MazeTile, MazeTile> traceMap = MouseThread.getMap();

        // Recreate the path from the traversal map
        MazeTile curTile = endTile;
        while (curTile != startTile) {
            if(curTile != startTile && curTile != endTile) {
                animList.add(new TileAnimation(curTile.getRow(), curTile.getCol(), Color.PINK));
            }
            curTile = traceMap.get(curTile);
        }

        return animList;
    }
}
