package mazesND.generators;

import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.EdgeAnimationND;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeEdgeND;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecDivGeneratorND implements MazeGeneratorND{
    private int dimension;

    public RecDivGeneratorND(int dimension) {
        this.dimension = dimension;
    }
    @Override
    public List<AnimationND> generateMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        List<MazeTileND> tileList = maze.collapseMaze();
        List<MazeEdgeND> edgeList = maze.listOfEdges();
        Collections.shuffle(tileList);
        AnimationNDGroup animGroup = new AnimationNDGroup();
        for(MazeTileND tile : tileList) {
            tile.setIndex(0);
            animGroup.add(new TileAnimationND(tile.getPosition(), Color.TRANSPARENT));
        }

        // Remove all of teh edges
        List<Integer> tempPos;
        for(MazeEdgeND edge : edgeList) {
            tempPos = edge.getPosition();
            if(edge.getDirection() > 0) {
                maze.removeWall(tempPos, edge.getDirection());
                animGroup.add(new EdgeAnimationND(tempPos, edge.getDirection(), Color.TRANSPARENT));
            }
            else {
                tempPos.set(-1-edge.getDirection(), tempPos.get(-1- edge.getDirection()) - 1);
                maze.removeWall(tempPos, -edge.getDirection());
                animGroup.add(new EdgeAnimationND(tempPos, -edge.getDirection(), Color.TRANSPARENT));
            }
        }
        animList.add(animGroup);

        System.out.println("From the complexity of recursive division in n dimensions, this is not supported");

        return animList;
    }
}
