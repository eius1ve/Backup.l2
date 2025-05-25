/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.residence;

public final class ResidenceType
extends Enum<ResidenceType> {
    public static final /* enum */ ResidenceType Castle = new ResidenceType();
    public static final /* enum */ ResidenceType ClanHall = new ResidenceType();
    public static final /* enum */ ResidenceType Fortress = new ResidenceType();
    public static final /* enum */ ResidenceType Dominion = new ResidenceType();
    public static final ResidenceType[] VALUES;
    private static final /* synthetic */ ResidenceType[] a;

    public static ResidenceType[] values() {
        return (ResidenceType[])a.clone();
    }

    public static ResidenceType valueOf(String string) {
        return Enum.valueOf(ResidenceType.class, string);
    }

    private static /* synthetic */ ResidenceType[] a() {
        return new ResidenceType[]{Castle, ClanHall, Fortress, Dominion};
    }

    static {
        a = ResidenceType.a();
        VALUES = ResidenceType.values();
    }
}
