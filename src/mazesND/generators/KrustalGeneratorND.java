package mazesND.generators;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.EdgeAnimationND;
import mazesND.animation.TileAnimationND;
import mazesND.generators.MazeGeneratorND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeEdgeND;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.List;

public class KrustalGeneratorND implements MazeGeneratorND {
    private int dimension;

    public KrustalGeneratorND(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public List<AnimationND> generateMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        List<MazeEdgeND> edgeList = maze.listOfEdges();
        List<MazeTileND> tileList = maze.collapseMaze();

        int index = 0;
        AnimationNDGroup tempAnimList = new AnimationNDGroup();
        for(MazeTileND tile : tileList) {
            tile.setIndex(index);
            index += 1;
            tempAnimList.add(new TileAnimationND(tile.getPosition(), getColor(tile)));
        }
        animList.add(tempAnimList);

        List<Integer> tempPos, tempPos2;
        MazeTileND thisTile, nextTile;
        int nextVal, thisVal;
        for(MazeEdgeND edge : edgeList) {
            tempPos = edge.getPosition();    // Position of the 'new' tile
            tempPos2 = new ArrayList<>(tempPos); // Position of the 'current' tile
            int d = edge.getDirection();
            if (d > 0) tempPos.set(d-1, tempPos.get(d-1) + 1);
            else       tempPos.set(-1-d, tempPos.get(-1-d) - 1);
            nextTile = maze.getTile(tempPos);
            nextVal = nextTile.getIndex();
            thisTile = maze.getTile(tempPos2);
            thisVal = thisTile.getIndex();
            if(nextVal != thisVal) {
                if (edge.getDirection() > 0) {
                    maze.removeWall(tempPos2, edge.getDirection());
                    animList.add(new EdgeAnimationND(tempPos2, edge.getDirection(), Color.TRANSPARENT));
                }
                else {
                    maze.removeWall(tempPos, -edge.getDirection());
                    animList.add(new EdgeAnimationND(tempPos, -edge.getDirection(), Color.TRANSPARENT));
                }

                int min = 0, max = 0;
                Color color = null;
                thisVal = thisTile.getIndex();
                nextVal = nextTile.getIndex();
                if (nextVal < thisVal) {
                    min = nextVal;
                    max = thisVal;
                    color = getColor(nextTile);
                }
                else {
                    min = thisVal;
                    max = nextVal;
                    color = getColor(thisTile);
                }

                tempAnimList = new AnimationNDGroup();
                tileList = maze.collapseMaze();
                for(MazeTileND tile : tileList) {
                    if (tile.getIndex() == max) {
                        tile.setIndex(min);
                        tempAnimList.add(new TileAnimationND(tile.getPosition(), color));
                    }
                    //if (tile.getIndex() == min) {
                    //    tempAnimList.add(new TileAnimationND(tile.getPosition(), color));
                    //}
                }
                animList.add(tempAnimList);
            }
        }

        return animList;
    }

    public Color getColor(MazeTileND tile) {
        double num = 95 + tile.getIndex() * 45;
        return new Color((num % 1000) / 1000,
                ((num*num) % 1000) / 1000,
                ((num*num*num) % 1000) / 1000,
                1);
    }
}
