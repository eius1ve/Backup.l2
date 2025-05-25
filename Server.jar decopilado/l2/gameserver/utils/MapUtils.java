/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.Builder
 */
package l2.gameserver.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.Builder;

public final class MapUtils {
    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder();
    }

    public static class MapBuilder<K, V>
    implements Builder<Map<K, V>> {
        private Map<K, V> ca = new LinkedHashMap();

        private MapBuilder() {
        }

        public MapBuilder<K, V> add(K k, V v) {
            this.ca.put(k, v);
            return this;
        }

        public MapBuilder<K, V> addKeys(V v, K ... KArray) {
            return this.addKeys(v, Arrays.asList(KArray));
        }

        public MapBuilder<K, V> addKeys(V v, List<K> list) {
            for (K k : list) {
                this.add(k, v);
            }
            return this;
        }

        public Map<K, V> build() {
            return Collections.unmodifiableMap(new HashMap<K, V>(this.ca));
        }
    }
}
