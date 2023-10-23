package ca.jrvs.practice.codingChallenge;

import java.util.Map;
import java.util.Objects;

public class CompareMaps {
    /**
     * Big-O: O(n)
     * Justification: In the worst case, this method needs to iterate through all the entries
     * in both input maps, where 'n' is the number of entries in the larger of the two maps.
     */
    public <K, V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2) {
        if (m1 == null || m2 == null) {
            return false; // If either map is null, they cannot be equal
        }

        if (m1.size() != m2.size()) {
            return false; // If the sizes are different, the maps cannot be equal
        }

        for (Map.Entry<K, V> entry : m1.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            // Check if the key exists in m2 and the corresponding values are equal
            if (!m2.containsKey(key) || !Objects.equals(value, m2.get(key))) {
                return false;
            }
        }

        return true; // If all keys and values match, the maps are equal
    }

    /**
     * Big-O: O(n)
     * Justification: The 'equals' method of the Map interface typically iterates through all entries.
     */
    public <K, V> boolean compareMapsWIthEquals(Map<K, V> m1, Map<K, V> m2) {
        if (m1 == null || m2 == null) {
            return false; // If either map is null, they cannot be equal
        }

        return m1.equals(m2);
    }
}
