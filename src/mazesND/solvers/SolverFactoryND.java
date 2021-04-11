package mazesND.solvers;

public class SolverFactoryND {
    public static MazeSolverND getSolver(SolverEnumND solverEnum, int dimension) {
        switch (solverEnum) {
            case WALL:
                return new WallSolverND(dimension);
            case ASTAR:
                return new AStarSolverND(dimension);
            case MOUSE:
                return new MouseSolverND(dimension);
            case PLEDGE:
                return new PledgeSolverND(dimension);
            case ROUTING:
                return new RoutingSolverND(dimension);
            case TREMAUX:
                return new TremauxSolverND(dimension);
            case MOUSE_THREAD:
                return null;
            default:
                return null;
        }
    }
}
