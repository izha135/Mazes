/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The threads that are used to solve the maze with
 * (using random mouse moving)
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeTile;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import static maze.MazeDirection.*;

public class MouseThread implements Runnable{
    // Queue of animations to animate
    private final BlockingQueue<Animation> ANIM_QUEUE;
    // The thread of the current MouseThread
    private final Thread THREAD;
    // The pool of threads
    private final ExecutorService POOL;
    // The maze object to solve the maze for
    private final Maze MAZE;
    // The previously traversed direction (no backtracking)
    private MazeDirection lastDirection;
    // The current row and column
    private int row, col;
    // The map of traversals to recreate the path from
    private static ConcurrentMap<MazeTile, MazeTile> traceMap;

    /**
     * Privately create a thread
     * @param row The row the thread is currently on
     * @param col The column the thread is currently on
     * @param pool The pool of threads being run
     * @param maze The maze to be solved
     * @param animQueue The queue of animations to display
     * @param dir The last direction that the thread moved
     */
    private MouseThread(int row, int col, ExecutorService pool, Maze maze, BlockingQueue<Animation> animQueue,
                        MazeDirection dir) {
        this.THREAD = new Thread(this);
        this.row = row;
        this.col = col;
        this.POOL = pool;
        this.MAZE = maze;
        this.ANIM_QUEUE = animQueue;
        lastDirection = flipDir(dir);
    }

    /**
     * Start a thread in the pool
     * @param row The row of the starting thread
     * @param col The column of the starting thread
     * @param pool The pool of all of the threads
     * @param maze The maze to solve
     * @param animQueue The queue of animations to display
     * @param dir The starting direction (null is there is no direction)
     */
    public static void createAndRun(int row, int col, ExecutorService pool, Maze maze,
                                           BlockingQueue<Animation> animQueue, MazeDirection dir) {
        MouseThread mouseThread = new MouseThread(row, col, pool, maze, animQueue, dir);
        // Put the starting thread in the thread pool
        pool.submit(mouseThread);
    }

    @Override
    public void run() {
        MazeDirection dir;
        MazeTile nextTile, curTile;

        List<MazeDirection> dirs;

        // Stop executing when the end has been reached
        while(!traceMap.containsKey(MAZE.getEndTile())){
            // Get the next traversable directions
            dirs = MAZE.getTile(row, col).getOpenDirections(MAZE);

            curTile = MAZE.getTile(row, col);

            // Add an animation for every tile reached
            if(curTile != MAZE.getStartTile()) {
                try {
                    ANIM_QUEUE.put(new TileAnimation(row, col, Color.CYAN));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Don't traverse in the previous direction
            if (lastDirection != null) dirs.remove(lastDirection);
            // Stop if there is no traversable directions
            if(dirs.size() == 0) {
                break;
            }
            // Spawn threads for every direction that the current thread isn't moving in
            for(int i = 1; i < dirs.size(); i++) {
                dir = dirs.get(i);
                nextTile = MAZE.getTile(row, col, dir);
                // Record the traversals
                traceMap.put(nextTile, curTile);
                // Add those threads to the thread pool
                synchronized (POOL){
                    if(nextTile == MAZE.getEndTile()) {
                        POOL.shutdownNow();
                    }
                    try {
                        POOL.submit(new MouseThread(nextTile.getRow(), nextTile.getCol(), POOL, MAZE, ANIM_QUEUE, dir));
                    }
                    catch (Exception e) { }
                }
            }
            // Move the current thread
            dir = dirs.get(0);
            nextTile = MAZE.getTile(row, col, dir);
            row = nextTile.getRow();
            col = nextTile.getCol();

            // Record the current thread's traversal
            traceMap.put(nextTile, curTile);

            // If the traversal is at the end, shutdown the pool
            synchronized (POOL) {
                if(nextTile == MAZE.getEndTile()) {
                    POOL.shutdownNow();
                }
            }

            lastDirection = flipDir(dir);
        }
    }

    /**
     * Initialize the class's traversal map
     */
    public static void initMap() {
        traceMap = new ConcurrentHashMap<>();
    }

    /**
     * @return The traversal map
     */
    public static ConcurrentMap<MazeTile, MazeTile> getMap() {
        return traceMap;
    }

    /**
     * Returns the opposite direction to what is provided
     * @param direction A direction
     * @return The direction in the exact opposite direction to the provided one
     */
    private MazeDirection flipDir(MazeDirection direction) {
        if(direction == null) return null;
        switch (direction) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            default:
                return null;
        }
    }
}
