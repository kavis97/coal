package authority.queries;

import authority.data.Boundary;
import authority.data.Entry;

import java.util.Collection;
import java.util.stream.Collectors;

public class SingleQuery implements Query {
    private final Boundary boundary;

    public SingleQuery(Boundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public Collection<Entry> apply(Collection<Entry> entries) {
        if (boundary == null) {
            return entries;
        }
        return applyQuery(entries, boundary).collect(Collectors.toSet());
    }
}
