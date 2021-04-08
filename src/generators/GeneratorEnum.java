/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 *  An enumeration for the different maze generators
 */

package generators;

public enum GeneratorEnum {
    DFS, KRUSTAL, PRIM, ALDOUS, RECDIV;

    /**
     * Return the enum of the same name as the string passed
     * @param str The string to find the enum for
     * @return The enum
     */
    public static GeneratorEnum getEnum(String str) {
        if (str.charAt(0) == 'a') return ALDOUS;
        if (str.charAt(0) == 'd') return DFS;
        if (str.charAt(0) == 'k') return KRUSTAL;
        if (str.charAt(0) == 'p') return PRIM;
        if (str.charAt(0) == 'r') return RECDIV;
        return null;
    }
}
