/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;

public static final class ArmorTemplate.ArmorType
extends Enum<ArmorTemplate.ArmorType>
implements ItemType {
    public static final /* enum */ ArmorTemplate.ArmorType NONE = new ArmorTemplate.ArmorType(1, "None");
    public static final /* enum */ ArmorTemplate.ArmorType LIGHT = new ArmorTemplate.ArmorType(2, "Light");
    public static final /* enum */ ArmorTemplate.ArmorType HEAVY = new ArmorTemplate.ArmorType(3, "Heavy");
    public static final /* enum */ ArmorTemplate.ArmorType MAGIC = new ArmorTemplate.ArmorType(4, "Magic");
    public static final /* enum */ ArmorTemplate.ArmorType PET = new ArmorTemplate.ArmorType(5, "Pet");
    public static final /* enum */ ArmorTemplate.ArmorType SIGIL = new ArmorTemplate.ArmorType(6, "Sigil");
    public static final ArmorTemplate.ArmorType[] VALUES;
    private final long dT;
    private final String gs;
    private static final /* synthetic */ ArmorTemplate.ArmorType[] a;

    public static ArmorTemplate.ArmorType[] values() {
        return (ArmorTemplate.ArmorType[])a.clone();
    }

    public static ArmorTemplate.ArmorType valueOf(String string) {
        return Enum.valueOf(ArmorTemplate.ArmorType.class, string);
    }

    private ArmorTemplate.ArmorType(int n2, String string2) {
        this.dT = 1L << n2 + WeaponTemplate.WeaponType.VALUES.length;
        this.gs = string2;
    }

    @Override
    public long mask() {
        return this.dT;
    }

    public String toString() {
        return this.gs;
    }

    private static /* synthetic */ ArmorTemplate.ArmorType[] a() {
        return new ArmorTemplate.ArmorType[]{NONE, LIGHT, HEAVY, MAGIC, PET, SIGIL};
    }

    static {
        a = ArmorTemplate.ArmorType.a();
        VALUES = ArmorTemplate.ArmorType.values();
    }
}
