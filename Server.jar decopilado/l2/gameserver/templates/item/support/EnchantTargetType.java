/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import l2.gameserver.model.items.ItemInstance;

public final class EnchantTargetType
extends Enum<EnchantTargetType> {
    public static final /* enum */ EnchantTargetType ALL = new EnchantTargetType(true, true, true);
    public static final /* enum */ EnchantTargetType WEAPON = new EnchantTargetType(true, false, false);
    public static final /* enum */ EnchantTargetType ARMOR = new EnchantTargetType(false, true, true);
    private final boolean ho;
    private final boolean hp;
    private final boolean hq;
    private static final /* synthetic */ EnchantTargetType[] a;

    public static EnchantTargetType[] values() {
        return (EnchantTargetType[])a.clone();
    }

    public static EnchantTargetType valueOf(String string) {
        return Enum.valueOf(EnchantTargetType.class, string);
    }

    private EnchantTargetType(boolean bl, boolean bl2, boolean bl3) {
        this.ho = bl;
        this.hp = bl2;
        this.hq = bl3;
    }

    public boolean isUsableOn(ItemInstance itemInstance) {
        if (this.ho && itemInstance.isWeapon()) {
            return true;
        }
        if (this.hp && itemInstance.isArmor()) {
            return true;
        }
        return this.hq && itemInstance.isAccessory();
    }

    private static /* synthetic */ EnchantTargetType[] a() {
        return new EnchantTargetType[]{ALL, WEAPON, ARMOR};
    }

    static {
        a = EnchantTargetType.a();
    }
}
