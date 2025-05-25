/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.commons.util;

import gnu.trove.TIntObjectHashMap;

private static class TroveUtils.TIntObjectHashMapEmpty<V>
extends TIntObjectHashMap<V> {
    TroveUtils.TIntObjectHashMapEmpty() {
        super(0);
    }

    public V put(int n, V v) {
        throw new UnsupportedOperationException();
    }

    public V putIfAbsent(int n, V v) {
        throw new UnsupportedOperationException();
    }
}
