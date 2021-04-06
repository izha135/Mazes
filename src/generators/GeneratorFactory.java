/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * A factory that creates the maze generators
 */

package generators;

public class GeneratorFactory {
    /**
     * Returns a maze generator based off the enumeration passed
     * @param genEnum The enum for which generator to create
     * @return The maze generator
     */
    public static MazeGenerator getGenerator(GeneratorEnum genEnum) {
        switch (genEnum) {
            case KRUSTAL:
                return new KruskalGenerator();
            case ALDOUS:
                return null;
            case PRIM:
                return new PrimGenerator();
            case DFS:
                return new DFSGenerator();
            case RECDIV:
                return null;
            default:
                return null;
        }
    }
}
