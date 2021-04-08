package solvers;

import generators.GeneratorEnum;

public enum SolverEnum {
    MOUSE, WALL, PLEDGE, TREMAUX, ROUTING, ASTAR, MOUSETHREAD, WALLTHREAD;

    public static SolverEnum getEnum(String str) {
        if (str.charAt(0) == 'p') return PLEDGE;
        if (str.charAt(0) == 't') return TREMAUX;
        if (str.charAt(0) == 'r') return ROUTING;
        if (str.charAt(0) == 'a') return ASTAR;
        if (str.charAt(0) == 'm' && str.length() == 5) return MOUSE;
        if (str.charAt(0) == 'm' && str.length() > 5) return MOUSETHREAD;
        if (str.charAt(0) == 'w' && str.length() == 4) return WALL;
        if (str.charAt(0) == 'w' && str.length() > 4) return WALLTHREAD;
        return null;
    }
}
