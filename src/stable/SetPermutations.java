package stable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class SetPermutations { 

    private SetPermutations() {
        // no state.
    }

    public static <T> Set<Set<T>> getSetPermutations(final Collection<? extends Collection<T>> input) {
        if (input == null) {
            throw new IllegalArgumentException("Input not provided");
        }
        final List<Set<T>> saved = new ArrayList<Set<T>>();
        for (Collection<T> c : input) {
            Set<T> s = new HashSet<T>(c);
            c.remove(null);
            if (c.size() >= 1) {
                saved.add(s);
            } else {
                throw new IllegalArgumentException("null/empty collection");
            }
        }

        return permute(new HashSet<T>(), saved);
    } 

    private static <T> Set<Set<T>> permute(final Set<T> initial, final List<Set<T>> itemSets) {

        if (itemSets.isEmpty()) {
            return Collections.singleton(initial);
        }

        final Set<T> items = itemSets.get(0);
        final List<Set<T>> remaining = itemSets.subList(1, itemSets.size());
        final int computedSetSize = initial.size() * items.size() * remaining.size();
        final Set<Set<T>> computed = new HashSet<Set<T>>(computedSetSize, 1);

        for (T item : items) {
            if (!initial.contains(item)) {
                Set<T> permutation = new HashSet<T>(initial);
                permutation.add(item);
                computed.addAll(permute(permutation, remaining));
            }
        }

        return computed;
    }

} 