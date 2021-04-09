package solvers;

import animation.Animation;
import animation.TileAnimation;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.MazeDirection;
import maze.MazeTile;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import static maze.MazeDirection.*;

public class MouseThread implements Runnable{
    // Queue of animations to animate
    private final BlockingQueue<Animation> ANIM_QUEUE;
    // The thread of the current MouseThread
    private final Thread THREAD;
    // The pool of threads
    private final ExecutorService POOL;
    // The maze object to solve the maze for
    private final Maze MAZE;
    // The previously traversed direction (no backtracking)
    private MazeDirection lastDirection;
    // The current row and column
    private int row, col;
    // The map of traversals to recreate the path from
    private static ConcurrentMap<MazeTile, MazeTile> traceMap;

    private MouseThread(int row, int col, ExecutorService pool, Maze maze, BlockingQueue<Animation> ANIM_QUEUE,
                        MazeDirection dir) {
        this.THREAD = new Thread(this);
        this.row = row;
        this.col = col;
        this.POOL = pool;
        this.MAZE = maze;
        this.ANIM_QUEUE = ANIM_QUEUE;
        lastDirection = flipDir(dir);
    }

    public static void createAndRun(int row, int col, ExecutorService pool, Maze maze,
                                           BlockingQueue<Animation> ANIM_QUEUE, MazeDirection dir) {
        MouseThread mouseThread = new MouseThread(row, col, pool, maze, ANIM_QUEUE, dir);
        pool.submit(mouseThread);
    }

    @Override
    public void run() {
        MazeDirection dir;
        MazeTile nextTile, curTile;

        MazeDirection lastDirection = this.lastDirection;
        int row = this.row;
        int col = this.col;

        List<MazeDirection> dirs;

        while(!traceMap.containsKey(MAZE.getEndTile())){
            dirs = MAZE.getTile(row, col).getOpenDirections(MAZE);

            curTile = MAZE.getTile(row, col);

            if(curTile != MAZE.getStartTile()) {
                try {
                    ANIM_QUEUE.put(new TileAnimation(row, col, Color.CYAN));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (lastDirection != null) dirs.remove(lastDirection);
            if(dirs.size() == 0) {
                break;
            }
            for(int i = 1; i < dirs.size(); i++) {
                dir = dirs.get(i);
                nextTile = MAZE.getTile(row, col, dir);
                traceMap.put(nextTile, curTile);
                synchronized (POOL){
                    if(nextTile == MAZE.getEndTile()) {
                        POOL.shutdownNow();
                    }
                    try {
                        POOL.submit(new MouseThread(nextTile.getRow(), nextTile.getCol(), POOL, MAZE, ANIM_QUEUE, dir));
                    }
                    catch (Exception e) { }
                }
            }
            dir = dirs.get(0);
            nextTile = MAZE.getTile(row, col, dir);
            row = nextTile.getRow();
            col = nextTile.getCol();

            traceMap.put(nextTile, curTile);

            synchronized (POOL) {
                if(nextTile == MAZE.getEndTile()) {
                    POOL.shutdownNow();
                }
            }

            lastDirection = flipDir(dir);
        }
    }

    public static void initMap() {
        traceMap = new ConcurrentHashMap<>();
    }

    public static ConcurrentMap<MazeTile, MazeTile> getMap() {
        return traceMap;
    }

    private MazeDirection flipDir(MazeDirection direction) {
        if(direction == null) return null;
        switch (direction) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            default:
                return null;
        }
    }
}
