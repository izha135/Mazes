package mazesND.generators;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.EdgeAnimationND;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AldousGeneratorND implements MazeGeneratorND {
    private int dimension;

    public AldousGeneratorND(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public List<AnimationND> generateMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        List<MazeTileND> tileList = maze.collapseMaze();
        Collections.shuffle(tileList);
        AnimationNDGroup animGroup = new AnimationNDGroup();
        for(MazeTileND tile : tileList) {
            tile.setIndex(0);
            animGroup.add(new TileAnimationND(tile.getPosition(), Color.RED));
        }
        animList.add(animGroup);

        MazeTileND thisTile = tileList.remove(0);
        thisTile.setIndex(1);
        animList.add(new TileAnimationND(thisTile.getPosition(), Color.BLACK));

        List<MazeTileND> neighborList;
        MazeTileND nextTile;
        while(!tileList.isEmpty()) {
            animGroup = new AnimationNDGroup();
            neighborList = thisTile.getNeighbors(maze);
            Collections.shuffle(neighborList);
            nextTile = neighborList.get(0);

            animGroup.add(new TileAnimationND(thisTile.getPosition(), Color.YELLOW));
            animGroup.add(new TileAnimationND(nextTile.getPosition(), Color.BLACK));

            if(nextTile.getIndex() == 0) {
                int direction = 0;
                List<Integer> position = null;
                for(int i = 0; i < thisTile.getPosition().size(); i++) {
                    if (thisTile.getPosition().get(i) != nextTile.getPosition().get(i)) {
                        direction = i+1;
                        if(thisTile.getPosition().get(i) < nextTile.getPosition().get(i)) {
                            position = thisTile.getPosition();
                        }
                        else{
                            position = nextTile.getPosition();
                        }
                        break;
                    }
                }
                maze.removeWall(position, direction);
                animGroup.add(new EdgeAnimationND(position, direction, Color.TRANSPARENT));

                tileList.remove(nextTile);
                nextTile.setIndex(1);
            }

            animList.add(animGroup);

            thisTile = nextTile;
        }

        return animList;
    }
}
