/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * A solver that uses multiple threads and follows the walls of the maze
 */

package solvers;

import animation.Animation;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class WallThreadSolver implements MazeSolver{
    /**
     * Create a new wall-following solver, using two threads
     */
    public WallThreadSolver() {

    }

    /**
     * Solve the maze using the multi-threaded wall-following technique
     * @param maze
     * @return
     */
    @Override
    public List<Animation> solve(Maze maze) {
        // List of animations to animate after
        List<Animation> animList = new ArrayList<>();

        // Clear all the indices of the maze tiles
        // Label them as they are visited
        for(MazeTile tile : maze.collapseMaze()) {
            tile.setIndex(0);
        }

        // The queue of animations to animate
        BlockingQueue<Animation> animQueue = new LinkedBlockingQueue<>();

        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Add two threads, starting at the two ends of the maze and on different walls
        WallThread.createAndSubmit(pool, animQueue, maze, maze.getStartTile(),
                maze.getEndTile(), 1, true);
        WallThread.createAndSubmit(pool, animQueue, maze, maze.getEndTile(),
                maze.getStartTile(), 2, false);

        // Wait until the threads are both done executing
        pool.shutdown();
        while (!pool.isTerminated()) {}

        // Transfer all of the animations in the queue to the list to return
        for(Animation anim : animQueue) {
            animList.add(anim);
        }

        return animList;
    }
}
