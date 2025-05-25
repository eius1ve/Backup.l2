/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Zone.ZoneType
extends Enum<Zone.ZoneType> {
    public static final /* enum */ Zone.ZoneType SIEGE = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType RESIDENCE = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType HEADQUARTER = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType FISHING = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType water = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType battle_zone = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType damage = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType instant_skill = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType mother_tree = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType peace_zone = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType poison = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType ssq_zone = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType swamp = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType no_escape = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType no_landing = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType no_restart = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType no_summon = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType dummy = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType offshore = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType epic = new Zone.ZoneType();
    public static final /* enum */ Zone.ZoneType fun = new Zone.ZoneType();
    private static final /* synthetic */ Zone.ZoneType[] a;

    public static Zone.ZoneType[] values() {
        return (Zone.ZoneType[])a.clone();
    }

    public static Zone.ZoneType valueOf(String string) {
        return Enum.valueOf(Zone.ZoneType.class, string);
    }

    private static /* synthetic */ Zone.ZoneType[] a() {
        return new Zone.ZoneType[]{SIEGE, RESIDENCE, HEADQUARTER, FISHING, water, battle_zone, damage, instant_skill, mother_tree, peace_zone, poison, ssq_zone, swamp, no_escape, no_landing, no_restart, no_summon, dummy, offshore, epic, fun};
    }

    static {
        a = Zone.ZoneType.a();
    }
}
