/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * Holds a collection of animations to occur simultaneously
 */

package mazesND.animation;

import mazesND.animation.GraphicMazeND;

import java.util.ArrayList;
import java.util.List;

public class AnimationNDGroup implements AnimationND {
    // The amount of time the animation should take
    private static double animateTime = 0.1;
    // The list of animations to occur simultaneously
    private List<AnimationND> animationList;

    /**
     * Creates an empty collection of animations
     */
    public AnimationNDGroup() {
        animationList = new ArrayList<>();
    }

    /**
     * Initialize the group of animations to a given list of animations
     * @param animationList The list of animations to occur simultaneously
     */
    public AnimationNDGroup(List<AnimationND> animationList) {
        this.animationList = new ArrayList<>(animationList);
    }

    /**
     * Add an animation to this animation group
     * @param animation The animation to add to the group
     */
    public void add(AnimationND animation) {
        animationList.add(animation);
    }

    /**
     * Execute the animations in the group at once
     * @param graphicMaze The maze to execute the animations on
     */
    @Override
    public void animate(GraphicMazeND graphicMaze) {
        for(AnimationND animation : animationList) {
            animation.animate(graphicMaze);
        }
    }

    /**
     * The length of time this animation should take
     * @return The length of time the animation should take
     */
    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
