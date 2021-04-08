/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * The maze solver using the A* algorithm for solving
 */

package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver implements MazeSolver{
    /**
     * Create a solver that will solver the maze using A*
     */
    public AStarSolver() {

    }

    /**
     * Solves a given maze using A*
     * @param maze The maze to solve
     * @return A list of animations of the relevant actions of the solver
     */
    @Override
    public List<Animation> solve(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        MazeTile currentTile;

        // Initialize the open set to only contain the startTile
        List<MazeTile> openSet = new ArrayList<>();
        openSet.add(startTile);

        // Initialize the cameFrom map to be empty
        Map<MazeTile, MazeTile> cameFrom = new HashMap<>();

        // gScore is the distance travelled so far to this tile
        // Set the gScore of start to 0
        Map<MazeTile, Double> gScore = new HashMap<>();
        gScore.put(startTile, 0.0);

        // fScore is a weight on tiles to decide which tile to traverse next
        // Initialize fScore of the start to the h functino value
        Map<MazeTile, Double> fScore = new HashMap<>();
        fScore.put(startTile, h(startTile, maze));

        double tGScore;
        // While the open set isn't empty
        while(!openSet.isEmpty()) {
            // Set the current cell to the cell with the lowest fScore
            currentTile = openSet.get(0);
            for(MazeTile tile : openSet) {
                if(fScore.get(tile) < fScore.get(currentTile)) currentTile = tile;
            }
            // If we are at the goal, break
            if(currentTile == endTile) {
                animList.addAll(reconstruct(cameFrom, maze));
                return animList;
            }

            // Remove the current tile
            openSet.remove(currentTile);
            // For all the neighbors of the current tile
            for(MazeTile neighbor : currentTile.getOpenNeighbors(maze)) {
                tGScore = gScore.get(currentTile) + 1;
                if (!gScore.containsKey(neighbor) || tGScore < gScore.get(neighbor)) {
                    // Set the scores of the neighbors
                    cameFrom.put(neighbor, currentTile);
                    gScore.put(neighbor, tGScore);
                    fScore.put(neighbor, tGScore + h(neighbor, maze));
                    // Add it to the open set if the neighbor is not in the open set
                    if(!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        animList.add(new TileAnimation(neighbor.getRow(), neighbor.getCol(), getColor(neighbor, maze)));
                    }
                }
            }
        }

        return animList;
    }

    /**
     * Reconstruct the path from the cameFrom map
     * @param cameFrom A map that represents a tree of traversable connections
     * @param maze The maze to reconstruct the path for
     * @return The list of animations displaying the path
     */
    public List<Animation> reconstruct(Map<MazeTile, MazeTile> cameFrom, Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        // Start with the end tile
        MazeTile currentTile = endTile;

        // Traverse the tree until the current is back to the start
        while(true) {
            currentTile = cameFrom.get(currentTile);
            if(currentTile == startTile) break;
            animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.PINK));
        }

        return animList;
    }

    /**
     * Takes a tile and gives a color representing its h score
     * @param tile The relevant tile
     * @param maze The maze being solved
     * @return The color that is associated with that hScore
     */
    public Color getColor(MazeTile tile, Maze maze) {
        int d = 2*maze.getWidth();
        double h = h(tile, maze);
        // h/d normalizes the values to be in [0, 1]
        return new Color(h/d, h/d, h/d, 1.0);
    }

    /**
     * The h score of a given tile (currently the L1 distance to the end)
     * @param tile The relevant tile
     * @param maze The maze being solved
     * @return The h score of the tile
     */
    public double h(MazeTile tile, Maze maze) {
        MazeTile endTile = maze.getEndTile();
        return Math.abs(tile.getCol()- endTile.getCol())+Math.abs(tile.getRow()- endTile.getRow());
    }
}
