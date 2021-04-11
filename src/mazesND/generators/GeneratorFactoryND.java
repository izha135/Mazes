package mazesND.generators;

import generators.*;
import mazesND.generators.KrustalGeneratorND;
import mazesND.generators.MazeGeneratorND;
import mazesND.generators.PrimGeneratorND;
import mazesND.generators.RecDivGeneratorND;

public class GeneratorFactoryND {
    public static MazeGeneratorND getGenerator(GeneratorEnumND generatorEnumND, int dimension) {
        switch (generatorEnumND) {
            case DFS:
                return new DFSGeneratorND(dimension);
            case PRIM:
                return new PrimGeneratorND(dimension);
            case ALDOUS:
                return new AldousGeneratorND(dimension);
            case KRUSTAL:
                return new KrustalGeneratorND(dimension);
            case REC:
                return new RecDivGeneratorND(dimension);
            case PRIMMOD:
                return new PrimModGeneratorND(dimension);
            default:
                return null;
        }
    }
}
