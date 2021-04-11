package mazesND.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MazeEdgeND {
    private List<Integer> position;
    private int direction;

    public MazeEdgeND(List<Integer> pos, int direction) {
        position = new ArrayList<>(pos);
        this.direction = direction;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public void setPosition(List<Integer> position) {
        this.position = position;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeEdgeND that = (MazeEdgeND) o;
        if (direction != that.direction) return false;
        for(int i = 0; i < position.size(); i++){
            if(position.get(i) != that.position.get(i)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}
