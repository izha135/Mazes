package maze;

public class MazeTile {
    private boolean northWall;
    private boolean southWall;
    private boolean westWall;
    private boolean eastWall;
    private int index;

    public MazeTile() {
        northWall = true;
        eastWall = true;
        southWall = true;
        westWall = true;
        index = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean hasNorthWall() {
        return northWall;
    }

    public boolean hasEastWall() {
        return eastWall;
    }

    public boolean hasSouthWall() {
        return southWall;
    }

    public boolean hasWestWall() {
        return westWall;
    }

    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    public void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    public void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }
}
