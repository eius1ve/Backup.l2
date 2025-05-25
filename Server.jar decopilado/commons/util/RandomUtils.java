/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import l2.commons.util.Rnd;
import org.apache.commons.lang3.tuple.Pair;

public class RandomUtils {
    public static final Comparator<Pair<?, Double>> DOUBLE_GROUP_COMPARATOR = new Comparator<Pair<?, Double>>(){

        @Override
        public int compare(Pair<?, Double> pair, Pair<?, Double> pair2) {
            double d = (Double)pair.getRight() - (Double)pair2.getRight();
            return d > 0.0 ? 1 : (d < 0.0 ? -1 : 0);
        }
    };

    public static <G> G pickRandomSortedGroup(Collection<Pair<G, Double>> collection, double d) {
        double d2 = d * Rnd.get();
        double d3 = 0.0;
        for (Pair<G, Double> pair : collection) {
            if (!(d2 <= (d3 += ((Double)pair.getRight()).doubleValue()))) continue;
            return (G)pair.getLeft();
        }
        return null;
    }

    public static <G> G pickRandomGroup(Collection<Pair<G, Double>> collection, double d) {
        ArrayList<Pair<G, Double>> arrayList = new ArrayList<Pair<G, Double>>(collection);
        arrayList.sort(DOUBLE_GROUP_COMPARATOR);
        return RandomUtils.pickRandomSortedGroup(arrayList, d);
    }

    public static <G> G pickRandomGroup(Collection<Pair<G, Double>> collection) {
        double d = 0.0;
        for (Pair<G, Double> pair : collection) {
            d += ((Double)pair.getRight()).doubleValue();
        }
        return RandomUtils.pickRandomGroup(collection, d);
    }
}
