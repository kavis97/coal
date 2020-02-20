package authority.data;

public class Claim extends Entry {

    public Claim(Coordinate coordinate, int value) {
        super(coordinate, value);
    }

    @Override
    public String toString() {
        return "Claim{" +
                "coordinate=" + getCoordinate() +
                ", Value=" + getValue() +
                '}';
    }
}
