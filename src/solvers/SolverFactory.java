/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * A factory that generates solvers based off of the passed enum
 */

package solvers;

public class SolverFactory {
    /**
     * Generate a solver based off an enumeration
     * @param solver The enum of the solver to generate
     * @return The solver based on the enum. Will return null if not supported
     */
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
                return new WallThreadSolver();
            case MOUSETHREAD:
                return new MouseThreadSolver();
            default:
                return null;
        }
    }
}
