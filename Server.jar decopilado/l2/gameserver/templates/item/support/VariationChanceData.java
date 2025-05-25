/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.templates.item.support;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class VariationChanceData {
    private final int GS;
    private final List<Pair<List<Pair<Integer, Double>>, Double>> dw;
    private final List<Pair<List<Pair<Integer, Double>>, Double>> dx;

    public VariationChanceData(int n, List<Pair<List<Pair<Integer, Double>>, Double>> list, List<Pair<List<Pair<Integer, Double>>, Double>> list2) {
        this.GS = n;
        this.dw = list;
        this.dx = list2;
    }

    public int getMineralItemId() {
        return this.GS;
    }

    public List<Pair<List<Pair<Integer, Double>>, Double>> getVariation1() {
        return this.dw;
    }

    public List<Pair<List<Pair<Integer, Double>>, Double>> getVariation2() {
        return this.dx;
    }
}
