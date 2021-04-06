/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * From a blank maze, remove walls using depth first search to
 * generate a maze
 */

package generators;

import animation.Animation;
import animation.AnimationGroup;
import animation.EdgeAnimation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DFSGenerator implements MazeGenerator{
    /**
     * Create a maze generator that will create mazes based on DFS
     */
    public DFSGenerator() {}

    /**
     * From the provided maze with all edges present, removes
     * edges using depth first search to change the maze
     * @param maze The maze to modify and create a maze within
     * @return The list of animations of relevant actions to generate the maze
     */
    @Override
    public List<Animation> generate(Maze maze) {
        List<Animation> animList = new ArrayList<>();
        Stack<MazeTile> tileStack = new Stack<>();

        // Set all of the tiles to white
        List<MazeTile> tileList = maze.collapseMaze();
        AnimationGroup animGroup = new AnimationGroup();
        for(MazeTile tile : tileList) {
            // An index of 0 indicates the cell is not part of the maze
            tile.setIndex(0);
            animGroup.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.WHITE));
        }
        animList.add(animGroup);

        // --Select a random tile, mark it as visited, and push it to the stack--
        Collections.shuffle(tileList);
        MazeTile currentTile = tileList.get(0);
        // An index of one states the cell is visited
        currentTile.setIndex(1);
        animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BLUE));
        tileStack.push(currentTile);

        MazeTile nextCell;

        List<Pair<MazeTile, MazeDirection>> neighborList;
        // --While the stack if not empty--
        while(!tileStack.isEmpty()) {
            // --Pop a cell from the stack and make it the current cell--
            currentTile = tileStack.pop();
            neighborList = unvisitedNeighbors(maze, currentTile);
            // --If the current cell has any neighbours which have not be visited--
            if(neighborList.size() > 0) {
                // --Push the current cell to the stack--
                tileStack.push(currentTile);
                // --Choose one of the unvisited neighbours--
                Collections.shuffle(neighborList);
                nextCell = neighborList.get(0).getKey();
                // --Remove the wall between the current cell and the chosen cell--
                maze.removeWall(currentTile.getRow(), currentTile.getCol(), neighborList.get(0).getValue());
                // Create the animation that removes the cell
                switch (neighborList.get(0).getValue()) {
                    case NORTH:
                        animList.add(new EdgeAnimation(nextCell.getRow(), nextCell.getCol(),
                                true, Color.TRANSPARENT));
                        break;
                    case EAST:
                        animList.add(new EdgeAnimation(currentTile.getRow(), currentTile.getCol(),
                                false, Color.TRANSPARENT));
                        break;
                    case SOUTH:
                        animList.add(new EdgeAnimation(currentTile.getRow(), currentTile.getCol(),
                                true, Color.TRANSPARENT));
                        break;
                    case WEST:
                        animList.add(new EdgeAnimation(nextCell.getRow(), nextCell.getCol(),
                                false, Color.TRANSPARENT));
                        break;
                    default:
                        break;
                }
                animList.add(new TileAnimation(nextCell.getRow(), nextCell.getCol(), Color.BLUE));

                // --Mark the chosen cell as visited and push it to the stack--
                nextCell.setIndex(1);
                tileStack.push(nextCell);
            }
        }

        return animList;
    }

    /**
     * For a given tile, returns a list of all the unvisited neighbors along with
     * the direction they are from the given tile
     * @param maze The maze to search in
     * @param tile The tile to find neighbors of
     * @return A list of pairs, of the neighbor tiles paired with their direction
     */
    private List<Pair<MazeTile, MazeDirection>> unvisitedNeighbors(Maze maze, MazeTile tile) {
        List<Pair<MazeTile, MazeDirection>> retList = new ArrayList<>();
        int row = tile.getRow();
        int col = tile.getCol();
        int width = maze.getWidth();

        // Look at each of the directions from the current cell and add
        // that cell to the return list if it is unvisited
        if(row > 0 && maze.getTile(row-1, col).getIndex() == 0) {
            retList.add(new Pair<>(maze.getTile(row-1, col), MazeDirection.NORTH));
        }
        if(col > 0 && maze.getTile(row, col-1).getIndex() == 0) {
            retList.add(new Pair<>(maze.getTile(row, col-1), MazeDirection.WEST));
        }
        if(row < width-1 && maze.getTile(row+1, col).getIndex() == 0) {
            retList.add(new Pair<>(maze.getTile(row+1, col), MazeDirection.SOUTH));
        }
        if(col < width-1 && maze.getTile(row, col+1).getIndex() == 0){
            retList.add(new Pair<>(maze.getTile(row, col+1), MazeDirection.EAST));
        }

        return retList;
    }
}
