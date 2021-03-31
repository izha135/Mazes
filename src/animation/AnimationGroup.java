package animation;

import java.util.ArrayList;
import java.util.List;

public class AnimationGroup implements Animation{
    private static double animateTime = 0.2;

    private List<Animation> animationList;

    public AnimationGroup() {
        animationList = new ArrayList<>();
    }

    public AnimationGroup(List<Animation> animationList) {
        this.animationList = new ArrayList<>(animationList);
    }

    @Override
    public void animate(GraphicMaze graphicMaze) {
        for(Animation animation : animationList) {
            animation.animate(graphicMaze);
        }
    }

    @Override
    public double getAnimateTime() {
        return animateTime;
    }
}
