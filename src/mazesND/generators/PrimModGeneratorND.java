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
import java.util.concurrent.ThreadLocalRandom;

public class PrimModGeneratorND implements MazeGeneratorND{
    private int dimension;
    // Between 0 and 1. Larger values make closer edges more weighted
    private double weightPreference = 0.6;

    public PrimModGeneratorND(int dim) {
        // Dimension must be 2-6
        dimension = dim;
    }

    @Override
    public List<AnimationND> generateMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();
        List<MazeEdgeND> edgeList = new ArrayList<>();
        List<MazeTileND> remainingList = maze.collapseMaze();
        Collections.shuffle(remainingList);

        // Set all of the cells to red
        AnimationNDGroup group = new AnimationNDGroup();
        for(MazeTileND m : remainingList) {
            group.add(new TileAnimationND(adjustPosition(m.getPosition()), Color.RED));
            m.setIndex(0);
        }
        animList.add(group);

        MazeTileND nextTile = remainingList.remove(0);
        MazeTileND startTile = nextTile;
        nextTile.setIndex(1);
        edgeList.addAll(nextTile.getEdges());
        animList.add(new TileAnimationND(adjustPosition(nextTile.getPosition()), Color.CYAN));

        List<MazeEdgeND> edgeList2;
        double edgeWeightSum;
        MazeTileND tempTile, temp2Tile;
        List<Integer> tempPos, tempPos2;
        MazeEdgeND nextEdge = null;
        double randDouble;
        while(!remainingList.isEmpty()) {
            edgeWeightSum = 0;
            edgeList2 = new ArrayList<>(edgeList);
            Collections.shuffle(edgeList2);
            for (MazeEdgeND edge : edgeList2) {
                edgeWeightSum += edgeWeight(edge, startTile);
            }
            randDouble = ThreadLocalRandom.current().nextDouble(edgeWeightSum);
            for (MazeEdgeND edge : edgeList2) {
                randDouble -= edgeWeight(edge, startTile);
                if(randDouble < 0.0) {
                    nextEdge = edge;
                    break;
                }
            }
            edgeList.remove(nextEdge);

            tempPos = nextEdge.getPosition();    // Position of the 'new' tile
            tempPos2 = new ArrayList<>(tempPos); // Position of the 'current' tile
            int d = nextEdge.getDirection();
            if (d > 0) tempPos.set(d-1, tempPos.get(d-1) + 1);
            else       tempPos.set(-1-d, tempPos.get(-1-d) - 1);
            nextTile = maze.getTile(tempPos);

            if (d > 0) animList.add(new EdgeAnimationND(adjustPosition(tempPos2), d, Color.TRANSPARENT));
            else       animList.add(new EdgeAnimationND(adjustPosition(tempPos), -d, Color.TRANSPARENT));
            animList.add(new TileAnimationND(adjustPosition(nextTile.getPosition()), Color.CYAN));

            maze.removeWall(nextEdge.getPosition(), -nextEdge.getDirection());
            nextTile.setIndex(1);

            // Add and remove edges to the list
            edgeList.addAll(nextTile.getEdges());
            List<MazeEdgeND> removeEdges = new ArrayList<>();
            for (MazeEdgeND edge : edgeList) {
                tempPos = new ArrayList<>(edge.getPosition());
                tempPos2 = new ArrayList<>(tempPos);
                d = edge.getDirection();
                if (d > 0) tempPos2.set(d-1, tempPos2.get(d-1) + 1);
                else       tempPos2.set(-1-d, tempPos2.get(-1-d) - 1);
                tempTile = maze.getTile(tempPos);
                temp2Tile = maze.getTile(tempPos2);
                if(tempTile.getIndex()!=0 && temp2Tile.getIndex()!=0) {
                    removeEdges.add(edge);
                }
            }
            edgeList.removeAll(removeEdges);

            remainingList.remove(nextTile);
        }

        return animList;
    }

    private double edgeWeight(MazeEdgeND edge, MazeTileND start) {
        int distance = 0;
        for (int i = 0; i < edge.getPosition().size(); i++)
            distance += Math.abs(edge.getPosition().get(i)-start.getPosition().get(i));
        return Math.pow(distance, -weightPreference);
    }

    private boolean containsPosition(List<Integer> pos, List<MazeTileND> tiles) {
        List<Integer> tempPos = adjustPosition(pos);
        for(MazeTileND tile : tiles) {
            for (int i = 0; i < 6; i++) {
                if(tile.getPosition().get(i) != tempPos.get(i)) continue;
            }
            return true;
        }
        return false;
    }

    private List<Integer> adjustPosition(List<Integer> mPos) {
        List<Integer> tempPos = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0));
        for(int i = 0; i < tempPos.size(); i++) {
            if(mPos.size() > i) tempPos.set(i, mPos.get(i));
            else                tempPos.set(i, 0);
        }
        return tempPos;
    }
}
