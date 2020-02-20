package authority.data;

public class Boundary {
    private Coordinate xStart;
    private Coordinate xEnd;
    private Coordinate yStart;
    private Coordinate yEnd;

    public Boundary(Coordinate xStart, Coordinate xEnd, Coordinate yStart, Coordinate yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public int getXLow() {
        return xStart.getLatitude();
    }

    public int getXHigh() {
        return xEnd.getLatitude();
    }

    public int getYLow() {
        return xStart.getLongitude();
    }

    public int getYHigh() {
        return yStart.getLongitude();
    }


}
