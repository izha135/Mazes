/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The maze solver using the pledge algorithm for solving.
 * This is VERY similar to wall solving
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.List;

public class PledgeSolver implements MazeSolver{
    // true if the solver follows the left wall. false for the right wall
    private static boolean isLeftWall = false;
    // The current direction the solver is going
    private MazeDirection direction;
    // The number of rotations (it is 0 if the left turns equals the number of right turns)
    public int turns;

    /**
     * Create a maze solver that solves using wall following
     */
    public PledgeSolver() {
        // Start trying to move north
        direction = MazeDirection.NORTH;
        // At the beginning of solving, the number of turns is 0
        turns = 0;
    }

    @Override
    public List<Animation> solve(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        // Initialize all the tiles to be unvisited
        for(MazeTile tile : maze.collapseMaze()) {
            tile.setIndex(0);
        }

        // Start at the start of the maze
        MazeTile currentTile = startTile;
        currentTile.setIndex(1);
        MazeTile nextTile;

        while(currentTile != endTile) {
            // Move to the next direction, following the relevant wall
            if(turns != 0 || getWall(currentTile, direction)) {
                findNextDirection(currentTile);
            }
            // else, don't change the direction we are moving

            // Travel in that direction
            nextTile = maze.getTile(currentTile.getRow(), currentTile.getCol(), direction);

            // Clear cells if the solver is backtracking
            if(nextTile.getIndex() == 1) {
                currentTile.setIndex(0);
                animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BEIGE));
            }

            // Set current path to pink
            nextTile.setIndex(1);
            if(nextTile != startTile && nextTile != endTile) {
                animList.add(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.PINK));
            }

            // Set the current tile to the traversed tile
            currentTile = nextTile;
        }

        return animList;
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
        // When turning right, increment the number of rotations
        turns += 1;
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
        // When turning left, decrement the number of rotations
        turns -= 1;
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
