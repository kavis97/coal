package authority.queries;

import authority.data.Boundary;
import authority.data.Claim;
import authority.data.Entry;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MultipleQuery implements Query {

    private Collection<Boundary> boundaries = new HashSet<>();
    private Integer boundValue;

    public MultipleQuery boundary(Boundary boundary) {
        boundaries.add(boundary);
        return this;
    }

    public MultipleQuery boundValue(Integer value) {
        this.boundValue = value;
        return this;
    }

    @Override
    public Collection<Entry> apply(Collection<Entry> entries) {
        if (boundaries == null || boundaries.isEmpty()) {
            return entries;
        }

        return boundaries.stream()
                .flatMap(b -> applyQuery(entries, b))
                .filter(this::filterForBoundValue)
                .collect(Collectors.toSet());
    }

    private boolean filterForBoundValue(Entry entry) {
        return entry instanceof Claim && boundValue != null ? entry.getValue() > boundValue : true;
    }
}
