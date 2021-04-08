/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * An enumeration of all of the possible solvers
 */

package solvers;

public enum SolverEnum {
    MOUSE, WALL, PLEDGE, TREMAUX, ROUTING, ASTAR, MOUSETHREAD, WALLTHREAD;

    /**
     * Return the enum of the same name as the string passed
     * @param str The string to find the enum for
     * @return The enum
     */
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
