package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver implements MazeSolver{
    public AStarSolver() {

    }

    @Override
    public List<Animation> solve(Maze maze) {

        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        MazeTile currentTile;

        List<MazeTile> openSet = new ArrayList<>();
        openSet.add(startTile);

        Map<MazeTile, MazeTile> cameFrom = new HashMap<>();

        Map<MazeTile, Double> gScore = new HashMap<>();
        gScore.put(startTile, 0.0);

        Map<MazeTile, Double> fScore = new HashMap<>();
        fScore.put(startTile, h(startTile, maze));

        double tGScore;
        while(!openSet.isEmpty()) {
            currentTile = openSet.get(0);
            for(MazeTile tile : openSet) {
                if(fScore.get(tile) < fScore.get(currentTile)) currentTile = tile;
            }
            if(currentTile == endTile) {
                animList.addAll(reconstruct(cameFrom, maze));
                return animList;
            }

            openSet.remove(currentTile);
            for(MazeTile neighbor : currentTile.getOpenNeighbors(maze)) {
                tGScore = gScore.get(currentTile) + 1;
                if (!gScore.containsKey(neighbor) || tGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, currentTile);
                    gScore.put(neighbor, tGScore);
                    fScore.put(neighbor, tGScore + h(neighbor, maze));
                    if(!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        animList.add(new TileAnimation(neighbor.getRow(), neighbor.getCol(), getColor(neighbor, maze)));
                    }
                }
            }
        }

        return animList;
    }

    public List<Animation> reconstruct(Map<MazeTile, MazeTile> cameFrom, Maze maze) {
        List<Animation> animList = new ArrayList<>();

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        MazeTile currentTile = endTile;

        while(true) {
            currentTile = cameFrom.get(currentTile);
            if(currentTile == startTile) break;
            animList.add(new TileAnimation(currentTile.getRow(), currentTile.getCol(), Color.PINK));
        }

        return animList;
    }

    public Color getColor(MazeTile tile, Maze maze) {
        int d = 2*maze.getWidth();
        double h = h(tile, maze);
        return new Color(h/d, h/d, h/d, 1.0);
    }

    public double h(MazeTile tile, Maze maze) {
        MazeTile endTile = maze.getEndTile();
        return Math.abs(tile.getCol()- endTile.getCol())+Math.abs(tile.getRow()- endTile.getRow());
    }
}
