/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 */
package l2.commons.util;

import gnu.trove.TIntArrayList;

private static class TroveUtils.TIntArrayListEmpty
extends TIntArrayList {
    TroveUtils.TIntArrayListEmpty() {
        super(0);
    }

    public void add(int n) {
        throw new UnsupportedOperationException();
    }
}
