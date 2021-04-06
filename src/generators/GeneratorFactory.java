package generators;

public class GeneratorFactory {
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
