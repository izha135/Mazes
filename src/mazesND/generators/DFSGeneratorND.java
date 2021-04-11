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
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DFSGeneratorND implements MazeGeneratorND {
    private int dimension;

    public DFSGeneratorND(int dimension) {
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

        Stack<MazeTileND> tileStack = new Stack<>();
        MazeTileND thisTile = tileList.remove(0);
        thisTile.setIndex(1);
        animList.add(new TileAnimationND(thisTile.getPosition(), Color.GREEN));
        tileStack.push(thisTile);

        MazeEdgeND currentEdge;
        MazeTileND nextTile = null;
        List<Integer> tempPos = null, tempPos2;
        List<MazeEdgeND> edgeList;
        while(!tileStack.isEmpty()){
            thisTile = tileStack.pop();
            tempPos2 = thisTile.getPosition();

            edgeList = thisTile.getEdges();
            Collections.shuffle(edgeList);
            currentEdge = null;
            for(MazeEdgeND edge : edgeList) {
                int d = edge.getDirection();
                tempPos = new ArrayList<>(tempPos2);
                if (d > 0) tempPos.set(d-1, tempPos.get(d-1) + 1);
                else       tempPos.set(-1-d, tempPos.get(-1-d) - 1);
                nextTile = maze.getTile(tempPos);
                if (nextTile.getIndex() == 0) {
                    currentEdge = edge;
                    break;
                }
            }
            if(currentEdge == null) continue;

            int D = currentEdge.getDirection();
            if (D > 0) {
                maze.removeWall(tempPos2, currentEdge.getDirection());
                animList.add(new EdgeAnimationND(tempPos2, currentEdge.getDirection(), Color.TRANSPARENT));
            }
            else {
                maze.removeWall(tempPos, -currentEdge.getDirection());
                animList.add(new EdgeAnimationND(tempPos, -currentEdge.getDirection(), Color.TRANSPARENT));
            }

            animList.add(new TileAnimationND(nextTile.getPosition(), Color.GREEN));

            nextTile.setIndex(1);
            tileStack.push(thisTile);
            tileStack.push(nextTile);
        }

        return animList;
    }
}
