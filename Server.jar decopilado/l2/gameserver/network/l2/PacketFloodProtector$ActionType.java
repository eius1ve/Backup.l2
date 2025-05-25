/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

public static final class PacketFloodProtector.ActionType
extends Enum<PacketFloodProtector.ActionType> {
    public static final /* enum */ PacketFloodProtector.ActionType log = new PacketFloodProtector.ActionType();
    public static final /* enum */ PacketFloodProtector.ActionType drop_log = new PacketFloodProtector.ActionType();
    public static final /* enum */ PacketFloodProtector.ActionType kick_log = new PacketFloodProtector.ActionType();
    public static final /* enum */ PacketFloodProtector.ActionType drop = new PacketFloodProtector.ActionType();
    public static final /* enum */ PacketFloodProtector.ActionType none = new PacketFloodProtector.ActionType();
    private static final /* synthetic */ PacketFloodProtector.ActionType[] a;

    public static PacketFloodProtector.ActionType[] values() {
        return (PacketFloodProtector.ActionType[])a.clone();
    }

    public static PacketFloodProtector.ActionType valueOf(String string) {
        return Enum.valueOf(PacketFloodProtector.ActionType.class, string);
    }

    private static /* synthetic */ PacketFloodProtector.ActionType[] a() {
        return new PacketFloodProtector.ActionType[]{log, drop_log, kick_log, drop, none};
    }

    static {
        a = PacketFloodProtector.ActionType.a();
    }
}
