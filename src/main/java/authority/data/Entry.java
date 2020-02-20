package authority.data;

import java.util.Objects;

public abstract class Entry {
    private final Coordinate coordinate;
    private final int value;

    public Entry(Coordinate coordinate, int value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return value == entry.value &&
                coordinate.equals(entry.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, value);
    }

}
