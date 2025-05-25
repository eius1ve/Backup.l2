/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util;

import java.util.Comparator;
import l2.commons.util.NaturalOrderComparator;

class NaturalOrderComparator.1
implements Comparator<String> {
    NaturalOrderComparator.1() {
    }

    @Override
    public int compare(String string, String string2) {
        return NaturalOrderComparator.b(string, string2);
    }
}
