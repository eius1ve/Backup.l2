/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm;

public final class ServerType
extends Enum<ServerType> {
    public static final /* enum */ ServerType NORMAL = new ServerType();
    public static final /* enum */ ServerType RELAX = new ServerType();
    public static final /* enum */ ServerType TEST = new ServerType();
    public static final /* enum */ ServerType NO_LABEL = new ServerType();
    public static final /* enum */ ServerType RESTRICTED = new ServerType();
    public static final /* enum */ ServerType EVENT = new ServerType();
    public static final /* enum */ ServerType FREE = new ServerType();
    public static final /* enum */ ServerType UNK1 = new ServerType();
    public static final /* enum */ ServerType WORLD = new ServerType();
    public static final /* enum */ ServerType NEW = new ServerType();
    public static final /* enum */ ServerType CLASSIC = new ServerType();
    private int pa = 1 << this.ordinal();
    private static final /* synthetic */ ServerType[] a;

    public static ServerType[] values() {
        return (ServerType[])a.clone();
    }

    public static ServerType valueOf(String string) {
        return Enum.valueOf(ServerType.class, string);
    }

    public int getMask() {
        return this.pa;
    }

    private static /* synthetic */ ServerType[] a() {
        return new ServerType[]{NORMAL, RELAX, TEST, NO_LABEL, RESTRICTED, EVENT, FREE, UNK1, WORLD, NEW, CLASSIC};
    }

    static {
        a = ServerType.a();
    }
}
