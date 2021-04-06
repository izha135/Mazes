/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * From a blank maze, remove walls using Kruskal's algorithm to
 * generate a maze
 */

package generators;

import animation.Animation;
import animation.AnimationGroup;
import animation.EdgeAnimation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeEdge;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalGenerator implements MazeGenerator{
    /**
     * A maze generator that uses Kruskal's generation algorithm
     */
    public KruskalGenerator() { }

    /**
     * From a given maze with all edges present, generates a maze using Kruskal's algorithm
     * @param maze The maze object the change and put the generated maze in
     * @return The list of animations of the relevant actions in the generating process
     */
    @Override
    public List<Animation> generate(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        // --Create a list of all walls--
        List<MazeEdge> edgeList = maze.getEdges();

        // --Create a set for each cell--
        AnimationGroup animGroup = new AnimationGroup();
        int index = 0;
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                // Set the index and color of each cell to be different
                maze.getTile(i, j).setIndex(index);
                animGroup.add(new TileAnimation(i, j, getColor(index)));
                index++;
            }
        }
        animList.add(animGroup);

        // --For each wall, in some random order...--
        Collections.shuffle(edgeList);
        // These are the two cells on either side of the edge
        MazeTile currentTile, nextTile;
        for(MazeEdge edge : edgeList) {
            currentTile = maze.getTile(edge.getRow(), edge.getCol());
            if(edge.isSouth()) {
                // To the south
                nextTile = maze.getTile(edge.getRow()+1, edge.getCol());
            }
            else {
                // To the east
                nextTile = maze.getTile(edge.getRow(), edge.getCol()+1);
            }

            // --If the cells divided by this wall belong to distinct sets--
            if(currentTile.getIndex() != nextTile.getIndex()) {
                animGroup = new AnimationGroup();
                // --Remove the wall and connect the two sets--
                if(edge.isSouth()) {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.SOUTH);
                    animGroup.add(new EdgeAnimation(edge.getRow(), edge.getCol(), true, Color.TRANSPARENT));
                }
                else {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.EAST);
                    animGroup.add(new EdgeAnimation(edge.getRow(), edge.getCol(), false, Color.TRANSPARENT));
                }

                // Find the color that is more common
                int curCount = 0;
                int nexCount = 0;
                for(MazeTile tile : maze.collapseMaze()) {
                    if(tile.getIndex() == currentTile.getIndex()) curCount++;
                    if(tile.getIndex() == nextTile.getIndex()) nexCount++;
                }

                // Set the indices so that the larger portion of color isn't changed
                int minIndex = curCount > nexCount ? nextTile.getIndex() : currentTile.getIndex();
                int maxIndex = curCount > nexCount ? currentTile.getIndex() : nextTile.getIndex();
                for(MazeTile tile : maze.collapseMaze()) {
                    // Combine the sets
                    if(tile.getIndex() == minIndex) {
                        tile.setIndex(maxIndex);
                        animGroup.add(new TileAnimation(tile.getRow(), tile.getCol(), getColor(maxIndex)));
                    }
                }
                animList.add(animGroup);
            }
        }

        return animList;
    }

    /**
     * Gives a mostly random color for every given index
     * @param index The index of the color (the index of the set)
     * @return The psuedo-random color
     */
    public Color getColor(int index) {
        int i = 123 * index + 386;
        return new Color(Math.abs((i % 1000)/1000.0),
                Math.abs(((i*i) % 1000)/1000.0),
                Math.abs(((i*i*i) % 1000)/1000.0),
                1.0);
    }
}
