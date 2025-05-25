/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 *  gnu.trove.TIntObjectHashMap
 */
package l2.commons.util;

import gnu.trove.TIntArrayList;
import gnu.trove.TIntObjectHashMap;

public class TroveUtils {
    private static final TIntObjectHashMap b = new TIntObjectHashMapEmpty();
    public static final TIntArrayList EMPTY_INT_ARRAY_LIST = new TIntArrayListEmpty();

    public static <V> TIntObjectHashMap<V> emptyIntObjectMap() {
        return b;
    }

    private static class TIntObjectHashMapEmpty<V>
    extends TIntObjectHashMap<V> {
        TIntObjectHashMapEmpty() {
            super(0);
        }

        public V put(int n, V v) {
            throw new UnsupportedOperationException();
        }

        public V putIfAbsent(int n, V v) {
            throw new UnsupportedOperationException();
        }
    }

    private static class TIntArrayListEmpty
    extends TIntArrayList {
        TIntArrayListEmpty() {
            super(0);
        }

        public void add(int n) {
            throw new UnsupportedOperationException();
        }
    }
}
