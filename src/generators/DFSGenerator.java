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
    public DFSGenerator() {

    }

    @Override
    public List<Animation> generate(Maze maze) {
        List<Animation> animList = new ArrayList<>();
        Stack<MazeTile> tileStack = new Stack<>();

        List<MazeTile> tileList = maze.collapseMaze();
        AnimationGroup animGroup = new AnimationGroup();
        for(MazeTile tile : tileList) {
            tile.setIndex(0);
            animGroup.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.WHITE));
        }
        animList.add(animGroup);

        Collections.shuffle(tileList);
        MazeTile currentTile = tileList.get(0);
        currentTile.setIndex(1);
        animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BLUE));
        tileStack.push(currentTile);

        MazeTile nextCell;

        List<Pair<MazeTile, MazeDirection>> neighborList;
        // While the stack if not empty
        while(!tileStack.isEmpty()) {
            // Pop a cell from the stack and make it the current cell
            currentTile = tileStack.pop();
            neighborList = unvisitedNeighbors(maze, currentTile);
            // If the current cell has any neighbours which have not be visited
            if(neighborList.size() > 0) {
                // Push the current cell to the stack
                tileStack.push(currentTile);
                // Choose one of the unvisited neighbours
                Collections.shuffle(neighborList);
                nextCell = neighborList.get(0).getKey();
                // Remove the wall between the current cell and the chosen cell
                maze.removeWall(currentTile.getRow(), currentTile.getCol(), neighborList.get(0).getValue());
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

                // Mark the chosen cell as visited and push it to the stack
                nextCell.setIndex(1);
                tileStack.push(nextCell);
            }
        }

        return animList;
    }

    public List<Pair<MazeTile, MazeDirection>> unvisitedNeighbors(Maze maze, MazeTile tile) {
        List<Pair<MazeTile, MazeDirection>> retList = new ArrayList<>();
        int row = tile.getRow();
        int col = tile.getCol();
        int width = maze.getWidth();

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
