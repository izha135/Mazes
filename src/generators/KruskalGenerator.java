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
    public KruskalGenerator() {

    }

    @Override
    public List<Animation> generate(Maze maze) {
        List<Animation> animList = new ArrayList<>();

        // Create a list of all walls
        List<MazeEdge> edgeList = maze.getEdges();

        // Create a set for each cell
        AnimationGroup animGroup = new AnimationGroup();
        int index = 0;
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                maze.getTile(i, j).setIndex(index);
                animGroup.add(new TileAnimation(i, j, getColor(index)));
                index++;
            }
        }
        animList.add(animGroup);

        // For each wall, in some random order
        Collections.shuffle(edgeList);
        MazeTile currentTile, nextTile = null;
        for(MazeEdge edge : edgeList) {
            currentTile = maze.getTile(edge.getRow(), edge.getCol());
            // To the south
            if(edge.isSouth()) {
                nextTile = maze.getTile(edge.getRow()+1, edge.getCol());
            }
            // to the east
            else {
                nextTile = maze.getTile(edge.getRow(), edge.getCol()+1);
            }

            // If the cells divided by this wall belong to distinct sets
            if(currentTile.getIndex() != nextTile.getIndex()) {
                animGroup = new AnimationGroup();
                if(edge.isSouth()) {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.SOUTH);
                    animGroup.add(new EdgeAnimation(edge.getRow(), edge.getCol(), true, Color.TRANSPARENT));
                }
                else {
                    maze.removeWall(edge.getRow(), edge.getCol(), MazeDirection.EAST);
                    animGroup.add(new EdgeAnimation(edge.getRow(), edge.getCol(), false, Color.TRANSPARENT));
                }
                int minIndex = Math.min(currentTile.getIndex(), nextTile.getIndex());
                int maxIndex = Math.max(currentTile.getIndex(), nextTile.getIndex());
                for(MazeTile tile : maze.collapseMaze()) {
                    if(tile.getIndex() == maxIndex) {
                        tile.setIndex(minIndex);
                        animGroup.add(new TileAnimation(tile.getRow(), tile.getCol(), getColor(minIndex)));
                    }
                }
                animList.add(animGroup);
            }
        }

        return animList;
    }

    public Color getColor(int index) {
        int offset = 387;
        int mult = 123;
        int i = mult * index + offset;
        return new Color(Math.abs((i % 1000)/1000.0),
                Math.abs(((i*i) % 1000)/1000.0),
                Math.abs(((i*i*i) % 1000)/1000.0),
                1.0);
    }
}
