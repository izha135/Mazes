/**
 * CS351 Project 4 - Mazes
 * Authors : John Cooper && Isha Chauhan
 *
 * An animation that does nothing, only waits a certain amount of time
 */

package animation;

public class WaitAnimation implements Animation{
    // The amount of time the animation should pause for (seconds)
    private double animTime;

    /**
     * Create an animation that waits for a specified amount of time
     * @param waitTime The amount of time to wait (seconds)
     */
    public WaitAnimation(double waitTime) {
        this.animTime = waitTime;
    }

    /**
     * Does nothing
     */
    @Override
    public void animate(GraphicMaze graphicMaze) {
    }

    /**
     * @return The amount of time to pause in seconds
     */
    @Override
    public double getAnimateTime() {
        return animTime;
    }
}
