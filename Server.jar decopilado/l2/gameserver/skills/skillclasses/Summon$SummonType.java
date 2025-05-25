/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

private static final class Summon.SummonType
extends Enum<Summon.SummonType> {
    public static final /* enum */ Summon.SummonType PET = new Summon.SummonType();
    public static final /* enum */ Summon.SummonType SIEGE_SUMMON = new Summon.SummonType();
    public static final /* enum */ Summon.SummonType AGATHION = new Summon.SummonType();
    public static final /* enum */ Summon.SummonType TRAP = new Summon.SummonType();
    public static final /* enum */ Summon.SummonType MERCHANT = new Summon.SummonType();
    public static final /* enum */ Summon.SummonType NPC = new Summon.SummonType();
    private static final /* synthetic */ Summon.SummonType[] a;

    public static Summon.SummonType[] values() {
        return (Summon.SummonType[])a.clone();
    }

    public static Summon.SummonType valueOf(String string) {
        return Enum.valueOf(Summon.SummonType.class, string);
    }

    private static /* synthetic */ Summon.SummonType[] a() {
        return new Summon.SummonType[]{PET, SIEGE_SUMMON, AGATHION, TRAP, MERCHANT, NPC};
    }

    static {
        a = Summon.SummonType.a();
    }
}
