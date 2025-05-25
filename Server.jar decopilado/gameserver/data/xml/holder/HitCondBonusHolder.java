/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.base.HitCondBonusType;

public final class HitCondBonusHolder
extends AbstractHolder {
    private static final HitCondBonusHolder a = new HitCondBonusHolder();
    private final Map<Integer, Double> T = new HashMap<Integer, Double>();

    public static HitCondBonusHolder getInstance() {
        return a;
    }

    public void addHitCondBonus(HitCondBonusType hitCondBonusType, double d) {
        this.T.put(hitCondBonusType.ordinal(), d);
    }

    public double getHitCondBonus(HitCondBonusType hitCondBonusType) {
        return this.T.get(hitCondBonusType.ordinal());
    }

    @Override
    public int size() {
        return this.T.size();
    }

    @Override
    public void clear() {
        this.T.clear();
    }
}
