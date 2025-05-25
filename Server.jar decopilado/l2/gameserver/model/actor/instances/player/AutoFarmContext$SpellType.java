/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

public static final class AutoFarmContext.SpellType
extends Enum<AutoFarmContext.SpellType> {
    public static final /* enum */ AutoFarmContext.SpellType ATTACK = new AutoFarmContext.SpellType();
    public static final /* enum */ AutoFarmContext.SpellType CHANCE = new AutoFarmContext.SpellType();
    public static final /* enum */ AutoFarmContext.SpellType SELF = new AutoFarmContext.SpellType();
    public static final /* enum */ AutoFarmContext.SpellType LOWLIFE = new AutoFarmContext.SpellType();
    private static final /* synthetic */ AutoFarmContext.SpellType[] a;

    public static AutoFarmContext.SpellType[] values() {
        return (AutoFarmContext.SpellType[])a.clone();
    }

    public static AutoFarmContext.SpellType valueOf(String string) {
        return Enum.valueOf(AutoFarmContext.SpellType.class, string);
    }

    private static /* synthetic */ AutoFarmContext.SpellType[] a() {
        return new AutoFarmContext.SpellType[]{ATTACK, CHANCE, SELF, LOWLIFE};
    }

    static {
        a = AutoFarmContext.SpellType.a();
    }
}
