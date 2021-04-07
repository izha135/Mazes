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
    private static boolean isLeftWall = false;
    private MazeDirection direction;
    public int turns;

    public PledgeSolver() {
        direction = MazeDirection.NORTH;
        turns = 0;
    }

    @Override
    public List<Animation> solve(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        for(MazeTile tile : maze.collapseMaze()) {
            tile.setIndex(0);
        }

        MazeTile currentTile = startTile;
        currentTile.setIndex(1);
        MazeTile nextTile = null;

        while(currentTile != endTile) {
            if(turns != 0 || getWall(currentTile, direction)) {
                findNextDirection(currentTile);
            }
            // else, don't change the direction we are moving

            nextTile = maze.getTile(currentTile.getRow(), currentTile.getCol(), direction);

            if(nextTile.getIndex() == 1) {
                currentTile.setIndex(0);
                animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BEIGE));
            }

            nextTile.setIndex(1);
            if(nextTile != startTile && nextTile != endTile) {
                animList.add(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.PINK));
            }

            currentTile = nextTile;
        }

        return animList;
    }

    public void findNextDirection(MazeTile tile) {
        if(isLeftWall) findNextLeftDirection(tile);
        else           findNextRightDirection(tile);
    }

    public void findNextLeftDirection(MazeTile tile) {
        nextLeftDirection();
        while(getWall(tile, direction)) {
            nextRightDirection();
        }
    }

    public void findNextRightDirection(MazeTile tile) {
        nextRightDirection();
        while(getWall(tile, direction)) {
            nextLeftDirection();
        }
    }

    public boolean getWall(MazeTile tile, MazeDirection dir) {
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

    public void nextRightDirection() {
        turns -= 1;
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

    public void nextLeftDirection() {
        turns += 1;
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
