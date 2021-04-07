package solvers;

public class SolverFactory {
    public static MazeSolver getSolver(SolverEnum solver) {
        switch (solver) {
            case TREMAUX:
                return null;
            case ROUTING:
                return null;
            case PLEDGE:
                return new PledgeSolver();
            case MOUSE:
                return new MouseSolver();
            case ASTAR:
                return new AStarSolver();
            case WALL:
                return new WallSolver();
            case WALLTHREAD:
                return null;
            case MOUSETHREAD:
                return null;
            default:
                return null;
        }
    }
}
