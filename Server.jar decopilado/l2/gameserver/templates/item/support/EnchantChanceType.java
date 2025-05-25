/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class EnchantChanceType
extends Enum<EnchantChanceType> {
    public static final /* enum */ EnchantChanceType ARMOR = new EnchantChanceType();
    public static final /* enum */ EnchantChanceType FULL_ARMOR = new EnchantChanceType();
    public static final /* enum */ EnchantChanceType JEWELRY = new EnchantChanceType();
    public static final /* enum */ EnchantChanceType WEAPON = new EnchantChanceType();
    private static final /* synthetic */ EnchantChanceType[] a;

    public static EnchantChanceType[] values() {
        return (EnchantChanceType[])a.clone();
    }

    public static EnchantChanceType valueOf(String string) {
        return Enum.valueOf(EnchantChanceType.class, string);
    }

    private static /* synthetic */ EnchantChanceType[] a() {
        return new EnchantChanceType[]{ARMOR, FULL_ARMOR, JEWELRY, WEAPON};
    }

    static {
        a = EnchantChanceType.a();
    }
}
