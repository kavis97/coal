package authority.data;

public class MineEntry extends Entry {

    public MineEntry(Coordinate coordinate, int value) {
        super(coordinate, value);
    }

    @Override
    public String toString() {
        return "MineEntry{" +
                "coordinate=" + getCoordinate() +
                ", Depth=" + getValue() +
                '}';
    }
}
