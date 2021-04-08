/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The maze solver using the random mouse algorithm for solving
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MouseSolver implements MazeSolver{
    /**
     * Create a solver that will solve with a random-mouse algorithm
     */
    public MouseSolver() {

    }

    /**
     * Solves the maze with the random-mouse algorithm
     * @param maze The maze to solve
     * @return The list of animations to display the solving
     */
    @Override
    public List<Animation> solve(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        // Set every tile as unvisited (for animating)
        for(MazeTile tile : maze.collapseMaze()) {
            tile.setIndex(0);
        }

        // Start at the start tile
        MazeTile currentTile = startTile;
        currentTile.setIndex(1);
        MazeTile nextTile;
        List<MazeTile> neighbors;
        // While not at the end
        while (currentTile != endTile) {
            // Randomly select a neighbor
            neighbors = currentTile.getOpenNeighbors(maze);
            Collections.shuffle(neighbors);
            nextTile = neighbors.get(0);

            // Clear tiles if backtracking
            if(nextTile.getIndex() == 1) {
                currentTile.setIndex(0);
                animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BEIGE));
            }

            // Set the current tile to pink
            nextTile.setIndex(1);
            if(nextTile != startTile && nextTile != endTile) {
                animList.add(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.PINK));
            }

            // Move to the neighbor
            currentTile = nextTile;
        }

        return animList;
    }
}
