package mazesND.animation;

public class WaitAnimationND implements AnimationND{
    private int animTime;

    public WaitAnimationND(int animTime) {
        this.animTime = animTime;
    }

    @Override
    public void animate(GraphicMazeND graphicMaze) {
        return;
    }

    @Override
    public double getAnimateTime() {
        return animTime;
    }
}
