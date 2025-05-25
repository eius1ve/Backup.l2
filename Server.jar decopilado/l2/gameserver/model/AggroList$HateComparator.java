/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Comparator;
import l2.gameserver.model.AggroList;

public static class AggroList.HateComparator
implements Comparator<AggroList.DamageHate> {
    private static Comparator<AggroList.DamageHate> c = new AggroList.HateComparator();

    public static Comparator<AggroList.DamageHate> getInstance() {
        return c;
    }

    AggroList.HateComparator() {
    }

    @Override
    public int compare(AggroList.DamageHate damageHate, AggroList.DamageHate damageHate2) {
        int n = damageHate2.hate - damageHate.hate;
        return n == 0 ? damageHate2.damage - damageHate.damage : n;
    }
}
