/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Comparator;
import l2.gameserver.model.AggroList;

public static class AggroList.DamageComparator
implements Comparator<AggroList.DamageHate> {
    private static Comparator<AggroList.DamageHate> c = new AggroList.DamageComparator();

    public static Comparator<AggroList.DamageHate> getInstance() {
        return c;
    }

    AggroList.DamageComparator() {
    }

    @Override
    public int compare(AggroList.DamageHate damageHate, AggroList.DamageHate damageHate2) {
        return damageHate2.damage - damageHate.damage;
    }
}
