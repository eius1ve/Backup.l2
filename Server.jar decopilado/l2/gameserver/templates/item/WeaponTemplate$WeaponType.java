/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.stats.Stats;
import l2.gameserver.templates.item.ItemType;

public static final class WeaponTemplate.WeaponType
extends Enum<WeaponTemplate.WeaponType>
implements ItemType {
    public static final /* enum */ WeaponTemplate.WeaponType NONE = new WeaponTemplate.WeaponType(1, "Shield", null);
    public static final /* enum */ WeaponTemplate.WeaponType SWORD = new WeaponTemplate.WeaponType(2, "Sword", Stats.SWORD_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType BLUNT = new WeaponTemplate.WeaponType(3, "Blunt", Stats.BLUNT_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType DAGGER = new WeaponTemplate.WeaponType(4, "Dagger", Stats.DAGGER_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType BOW = new WeaponTemplate.WeaponType(5, "Bow", Stats.BOW_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType POLE = new WeaponTemplate.WeaponType(6, "Pole", Stats.POLE_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType ETC = new WeaponTemplate.WeaponType(7, "Etc", null);
    public static final /* enum */ WeaponTemplate.WeaponType FIST = new WeaponTemplate.WeaponType(8, "Fist", Stats.FIST_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType DUAL = new WeaponTemplate.WeaponType(9, "Dual Sword", Stats.DUAL_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType DUALFIST = new WeaponTemplate.WeaponType(10, "Dual Fist", Stats.FIST_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType BIGSWORD = new WeaponTemplate.WeaponType(11, "Big Sword", Stats.SWORD_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType PET = new WeaponTemplate.WeaponType(12, "Pet", Stats.FIST_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType ROD = new WeaponTemplate.WeaponType(13, "Rod", null);
    public static final /* enum */ WeaponTemplate.WeaponType BIGBLUNT = new WeaponTemplate.WeaponType(14, "Big Blunt", Stats.BLUNT_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType CROSSBOW = new WeaponTemplate.WeaponType(15, "Crossbow", Stats.CROSSBOW_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType RAPIER = new WeaponTemplate.WeaponType(16, "Rapier", Stats.DAGGER_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType ANCIENTSWORD = new WeaponTemplate.WeaponType(17, "Ancient Sword", Stats.SWORD_WPN_VULNERABILITY);
    public static final /* enum */ WeaponTemplate.WeaponType DUALDAGGER = new WeaponTemplate.WeaponType(18, "Dual Dagger", Stats.DAGGER_WPN_VULNERABILITY);
    public static final WeaponTemplate.WeaponType[] VALUES;
    private final long dW;
    private final String gu;
    private final Stats c;
    private static final /* synthetic */ WeaponTemplate.WeaponType[] a;

    public static WeaponTemplate.WeaponType[] values() {
        return (WeaponTemplate.WeaponType[])a.clone();
    }

    public static WeaponTemplate.WeaponType valueOf(String string) {
        return Enum.valueOf(WeaponTemplate.WeaponType.class, string);
    }

    private WeaponTemplate.WeaponType(int n2, String string2, Stats stats) {
        this.dW = 1L << n2;
        this.gu = string2;
        this.c = stats;
    }

    @Override
    public long mask() {
        return this.dW;
    }

    public Stats getDefence() {
        return this.c;
    }

    public String toString() {
        return this.gu;
    }

    private static /* synthetic */ WeaponTemplate.WeaponType[] a() {
        return new WeaponTemplate.WeaponType[]{NONE, SWORD, BLUNT, DAGGER, BOW, POLE, ETC, FIST, DUAL, DUALFIST, BIGSWORD, PET, ROD, BIGBLUNT, CROSSBOW, RAPIER, ANCIENTSWORD, DUALDAGGER};
    }

    static {
        a = WeaponTemplate.WeaponType.a();
        VALUES = WeaponTemplate.WeaponType.values();
    }
}
