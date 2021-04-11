/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The thread for each of the wall-following threads
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeTile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class WallThread implements Runnable {
    private Thread thread;
    // The last traversed direction
    private MazeDirection direction;
    // If the wall followed should be the left or the right one
    private boolean isLeftWall = true;
    // The maze object to solve
    private Maze maze;
    // The queue of animations to animate
    private BlockingQueue animQueue;
    // The start and end tile for this thread
    private MazeTile startTile, endTile;
    // An indexing value for the thread
    private int index;

    private WallThread(BlockingQueue<Animation> animQueue, Maze maze,
                       MazeTile start, MazeTile end, int index) {
        direction = MazeDirection.NORTH;
        this.animQueue = animQueue;
        this.maze = maze;
        startTile = start;
        endTile = end;
        this.index = index;
    }

    /**
     * Create a new MazeThread and add it to the pool
     * @param pool Thread pool
     * @param animQueue Queue of animations to display after
     * @param maze Maze to solve
     * @param start Tile to start the solution on
     * @param end Tile to end the solution on
     * @param index The index of the thread
     */
    public static void createAndSubmit(ExecutorService pool, BlockingQueue<Animation> animQueue, Maze maze,
                                       MazeTile start, MazeTile end, int index) {
        WallThread newThread = new WallThread(animQueue, maze, start, end, index);
        pool.submit(newThread);
    }

    /**
     * Run the thread
     */
    @Override
    public void run() {
        // Start at the start of the maze
        MazeTile currentTile = startTile;
        currentTile.setIndex(index);
        MazeTile nextTile;

        // While not at the end of the maze
        while(currentTile != endTile) {
            // Move to the next direction, following the relevant wall
            findNextDirection(currentTile);

            // Travel in that direction
            nextTile = maze.getTile(currentTile.getRow(), currentTile.getCol(), direction);

            // If this condition isn't satified, then the next tile has been marked by another thread
            if(!(nextTile.getIndex() == index || nextTile.getIndex() == 0)) break;

            // Clear cells if the solver is backtracking
            if(nextTile.getIndex() != 0) {
                currentTile.setIndex(0);
                try{
                    animQueue.put(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BEIGE));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            nextTile.setIndex(index);
            // Set current path to pink
            if(nextTile != startTile && nextTile != endTile) {
                try{
                    animQueue.put(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.PINK));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Set the current tile to the traversed tile
            currentTile = nextTile;
        }
    }

    /**
     * Modifies the direction to the next direction that should be traversed to
     * @param tile The current tile of the solver
     */
    private void findNextDirection(MazeTile tile) {
        if(isLeftWall) findNextLeftDirection(tile);
        else           findNextRightDirection(tile);
    }

    /**
     * Modifies the direction to the next direction that should be traversed to,
     * following the left wall
     * @param tile The current tile of the solver
     */
    private void findNextLeftDirection(MazeTile tile) {
        // Start by turning left
        nextLeftDirection();
        // Then keep turning right until there isn't a wall
        // This is the same as following the left wall
        while(getWall(tile, direction)) {
            nextRightDirection();
        }
    }

    /**
     * Modifies the direction to the next direction that should be traversed to,
     * following the right wall
     * @param tile The current tile of the solver
     */
    private void findNextRightDirection(MazeTile tile) {
        // Start by turning right
        nextRightDirection();
        // The keep turning left until there isn't a wall
        // This is the same as following the right wall
        while(getWall(tile, direction)) {
            nextLeftDirection();
        }
    }

    /**
     * Test if there is a wall in the specified direction
     * @param tile The tile to look at the walls of
     * @param dir The direction to test for a wall
     * @return true if there is a wall in that direction, false otherwise
     */
    private boolean getWall(MazeTile tile, MazeDirection dir) {
        switch (dir) {
            case NORTH:
                return tile.hasNorthWall();
            case EAST:
                return tile.hasEastWall();
            case SOUTH:
                return tile.hasSouthWall();
            case WEST:
                return tile.hasWestWall();
            default:
                return true;
        }
    }

    /**
     * Set the direction to the next direction clockwise
     */
    private void nextRightDirection() {
        switch (direction) {
            case NORTH:
                direction = MazeDirection.EAST;
                break;
            case EAST:
                direction = MazeDirection.SOUTH;
                break;
            case SOUTH:
                direction = MazeDirection.WEST;
                break;
            case WEST:
                direction = MazeDirection.NORTH;
                break;
            default:
                break;
        }
    }

    /**
     * Set the direction to the next direction counter-clockwise
     */
    private void nextLeftDirection() {
        switch (direction) {
            case NORTH:
                direction = MazeDirection.WEST;
                break;
            case EAST:
                direction = MazeDirection.NORTH;
                break;
            case SOUTH:
                direction = MazeDirection.EAST;
                break;
            case WEST:
                direction = MazeDirection.SOUTH;
                break;
            default:
                break;
        }
    }
}
