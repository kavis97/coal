package authority.service;

import authority.data.Entry;
import authority.queries.Query;

import java.util.Collection;
import java.util.Collections;

public class MiningFeatureService {
    public Collection<Entry> process(Collection<Entry> entries, Query query) {
        if (entries != null && !entries.isEmpty()) {
            if (query != null) {
                return query.apply(entries);
            } else {
                return entries;
            }
        }
        return Collections.emptySet();
    }
}
