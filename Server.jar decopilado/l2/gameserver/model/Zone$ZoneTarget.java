/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Zone.ZoneTarget
extends Enum<Zone.ZoneTarget> {
    public static final /* enum */ Zone.ZoneTarget pc = new Zone.ZoneTarget();
    public static final /* enum */ Zone.ZoneTarget npc = new Zone.ZoneTarget();
    public static final /* enum */ Zone.ZoneTarget only_pc = new Zone.ZoneTarget();
    private static final /* synthetic */ Zone.ZoneTarget[] a;

    public static Zone.ZoneTarget[] values() {
        return (Zone.ZoneTarget[])a.clone();
    }

    public static Zone.ZoneTarget valueOf(String string) {
        return Enum.valueOf(Zone.ZoneTarget.class, string);
    }

    private static /* synthetic */ Zone.ZoneTarget[] a() {
        return new Zone.ZoneTarget[]{pc, npc, only_pc};
    }

    static {
        a = Zone.ZoneTarget.a();
    }
}
