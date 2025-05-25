/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;

public final class KarmaIncreaseDataHolder
extends AbstractHolder {
    private static final KarmaIncreaseDataHolder a = new KarmaIncreaseDataHolder();
    private final Map<Integer, Double> U = new HashMap<Integer, Double>();

    public static KarmaIncreaseDataHolder getInstance() {
        return a;
    }

    public void addData(int n, double d) {
        this.U.put(n, d);
    }

    public double getData(int n) {
        return this.U.get(n);
    }

    @Override
    public int size() {
        return this.U.size();
    }

    @Override
    public void clear() {
        this.U.clear();
    }
}
