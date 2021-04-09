package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeTile;
import solvers.MouseThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MouseThreadSolver implements MazeSolver{
    public MouseThreadSolver() {

    }

    @Override
    public List<Animation> solve(Maze maze) {
        BlockingQueue<Animation> animQueue = new LinkedBlockingQueue<>();

        ExecutorService pool = Executors.newFixedThreadPool(1000);

        MazeTile startTile = maze.getStartTile();
        MazeTile endTile = maze.getEndTile();

        MouseThread.initMap();
        MouseThread.createAndRun(startTile.getRow(), startTile.getCol(), pool, maze, animQueue, null);

        while (!pool.isShutdown()) {}

        List<Animation> animList = new ArrayList<>();

        for(Animation anim : animQueue) {
            animList.add(anim);
        }

        ConcurrentMap<MazeTile, MazeTile> traceMap = MouseThread.getMap();

        MazeTile curTile = endTile;
        while (curTile != startTile) {
            if(curTile != startTile && curTile != endTile) {
                animList.add(new TileAnimation(curTile.getRow(), curTile.getCol(), Color.PINK));
            }
            curTile = traceMap.get(curTile);
        }

        return animList;
    }
}
