package authority.queries;

import authority.data.Boundary;
import authority.data.Entry;

import java.util.Collection;
import java.util.stream.Stream;

public interface Query {
    Collection<Entry> apply(Collection<Entry> entries);

    default Stream<Entry> applyQuery(Collection<Entry> entries, Boundary boundary) {
        return entries.stream()
                .filter(e -> entryWithInBoundary(e, boundary));
    }

    default boolean entryWithInBoundary(Entry e, Boundary boundary) {
        int latitude = e.getCoordinate().getLatitude();
        int longitude = e.getCoordinate().getLongitude();
        return
                latitude >= boundary.getXLow() &&
                        latitude <= boundary.getXHigh() &&
                        longitude >= boundary.getYLow() &&
                        longitude <= boundary.getYHigh();
    }
}
