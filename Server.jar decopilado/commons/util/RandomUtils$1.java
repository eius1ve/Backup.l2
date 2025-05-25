/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.commons.util;

import java.util.Comparator;
import org.apache.commons.lang3.tuple.Pair;

class RandomUtils.1
implements Comparator<Pair<?, Double>> {
    RandomUtils.1() {
    }

    @Override
    public int compare(Pair<?, Double> pair, Pair<?, Double> pair2) {
        double d = (Double)pair.getRight() - (Double)pair2.getRight();
        return d > 0.0 ? 1 : (d < 0.0 ? -1 : 0);
    }
}
