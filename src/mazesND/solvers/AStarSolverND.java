package mazesND.solvers;

import mazesND.animation.AllAnimationND;
import mazesND.animation.AnimationND;
import mazesND.animation.AnimationNDGroup;
import mazesND.animation.TileAnimationND;
import javafx.scene.paint.Color;
import mazesND.maze.MazeND;
import mazesND.maze.MazeTileND;
import mazesND.solvers.MazeSolverND;

import java.util.*;
import java.util.List;

public class AStarSolverND implements MazeSolverND {
    private int dimension;
    private final boolean SHOW_ATTEMPTS = false;

    public AStarSolverND(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public List<AnimationND> solveMaze(MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();

        List<MazeTileND> openSet = new LinkedList<>();
        openSet.add(maze.getStartTile());

        Map<MazeTileND, MazeTileND> cameFrom = new HashMap<>();

        Map<MazeTileND, Integer> fScore = new HashMap<>();
        fScore.put(maze.getStartTile(), h(maze.getStartTile(), maze));
        Map<MazeTileND, Integer> gScore = new HashMap<>();
        gScore.put(maze.getStartTile(), 0);

        MazeTileND currentTile;
        List<MazeTileND> tileList;
        while(!openSet.isEmpty()){
            currentTile = openSet.get(0);
            for(MazeTileND tile : openSet) {
                if(fScore.get(currentTile) > fScore.get(tile)) {
                    currentTile = tile;
                }
            }
            if (currentTile == maze.getEndTile()) {
                if(dimension != 2 && !SHOW_ATTEMPTS){
                    AnimationNDGroup animGroup = new AnimationNDGroup();
                    animGroup.add(new AllAnimationND(Color.TRANSPARENT));
                    animGroup.add(new TileAnimationND(maze.getStartTile().getPosition(), Color.BLUE));
                    animGroup.add(new TileAnimationND(maze.getEndTile().getPosition(), Color.RED));
                    animList.add(animGroup);
                }

                animList.addAll(reconstructPath(cameFrom, currentTile, maze));
                break;
            }

            openSet.remove(currentTile);
            tileList = currentTile.getOpenNeighbors(maze);
            for(MazeTileND neighbor : tileList) {
                int tenGScore = gScore.get(currentTile) + 1; // 1 is the distance between tiles
                if (!gScore.containsKey(neighbor) || tenGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, currentTile);
                    gScore.put(neighbor, tenGScore);
                    fScore.put(neighbor, tenGScore+h(neighbor, maze));
                    if (!openSet.contains(neighbor)) {
                        if (neighbor != maze.getEndTile() && neighbor != maze.getStartTile()) {
                            double temp = 1 - (h(neighbor, maze) / (1.0 * maze.getWidth() * maze.getWidth()
                                    * dimension * dimension));
                            Color color = new Color(temp, temp, temp, 1);
                            animList.add(new TileAnimationND(neighbor.getPosition(), color));
                        }
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return animList;
    }

    public List<AnimationND> reconstructPath(Map<MazeTileND, MazeTileND> cameFrom, MazeTileND current, MazeND maze) {
        List<AnimationND> animList = new ArrayList<>();

        MazeTileND tempTile = current;
        while(cameFrom.containsKey(tempTile)) {
            if(tempTile != maze.getEndTile() && tempTile != maze.getStartTile()) {
                animList.add(new TileAnimationND(tempTile.getPosition(), Color.HOTPINK));
            }
            tempTile = cameFrom.get(tempTile);
        }

        return animList;
    }

    public int h(MazeTileND tile, MazeND maze) {
        MazeTileND end = maze.getEndTile();

        List<Integer> pos1 = tile.getPosition();
        List<Integer> pos2 = end.getPosition();

        int dist = 0;
        for (int i = 0; i < pos1.size(); i++) {
            dist += Math.abs(pos1.get(i)-pos2.get(i));
        }
        return dist*dist;
    }
}
