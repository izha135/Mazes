/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * From a blank maze, remove walls using Prim's algorithm to
 * generate a maze
 */

package generators;

import animation.*;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeEdge;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PrimGenerator implements MazeGenerator {
    /**
     * Create a new maze generator
     */
    public PrimGenerator() {}

    /**
     * Generates a maze using Prim's algorithm, assuming every edge in the maze is present beforehand
     * @param maze The maze object to create the maze in
     * @return A list of animations that show the important parts of maze generation
     */
    @Override
    public List<Animation> generate(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        // --Start with a grid full of walls--
        List<MazeTile> tileList = maze.collapseMaze();
        // Create an animation group that instantly sets every tile to white
        AnimationGroup animGroup = new AnimationGroup();
        for(MazeTile tile : tileList) {
            // Tiles with index 0 are unmarked
            tile.setIndex(0);
            animGroup.add(new TileAnimation(tile.getRow(), tile.getCol(), Color.WHITE));
        }
        animList.add(animGroup);

        // --Pick a tile, mark it as part of the maze--
        Collections.shuffle(tileList);
        MazeTile currentTile = tileList.get(0);
        // Tiles with index 1 are marked
        currentTile.setIndex(1);
        // Set all marked tiles to blue
        animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BLUE));

        List<MazeEdge> wallList = currentTile.getWalls();

        MazeTile nextTile = null;
        // --While there are still walls in the list--
        while(wallList.size() != 0) {
            // --Pick a random wall from the list--
            int randIndex = ThreadLocalRandom.current().nextInt(wallList.size());
            MazeEdge edge = wallList.get(randIndex);

            currentTile = maze.getTile(edge.getRow(), edge.getCol());
            if(edge.isSouth()) {
                nextTile = maze.getTile(edge.getRow()+1, edge.getCol());
            }
            else {
                nextTile = maze.getTile(edge.getRow(), edge.getCol()+1);
            }
            // --If the wall divides two separated cells--
            if(nextTile.getIndex()==0) {
                // --Remove the wall, mark the unvisited cell--
                if(edge.isSouth()) {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.SOUTH);
                    animList.add(new EdgeAnimation(edge.getRow(), edge.getCol(), true, Color.TRANSPARENT));
                }
                else{
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.EAST);
                    animList.add(new EdgeAnimation(edge.getRow(), edge.getCol(), false, Color.TRANSPARENT));
                }
                // Mark the unvisited cell
                nextTile.setIndex(1);
                animList.add(new TileAnimation(nextTile.getRow(), nextTile.getCol(), Color.BLUE));

                wallList.addAll(nextTile.getWalls());
            }
            else if(currentTile.getIndex()==0) {
                // --Remove the wall, mark the unvisited cell--
                if(edge.isSouth()) {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.SOUTH);
                    animList.add(new EdgeAnimation(edge.getRow(), edge.getCol(), true, Color.TRANSPARENT));
                }
                else{
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.EAST);
                    animList.add(new EdgeAnimation(edge.getRow(), edge.getCol(), false, Color.TRANSPARENT));
                }
                // Mark the unvisited cell
                currentTile.setIndex(1);
                animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.BLUE));

                wallList.addAll(currentTile.getWalls());
            }

            // --Remove the wall from the list--
            wallList.remove(edge);
        }

        return animList;
    }
}
