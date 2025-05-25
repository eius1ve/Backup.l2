/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

public static final class BypassManager.BypassType
extends Enum<BypassManager.BypassType> {
    public static final /* enum */ BypassManager.BypassType ENCODED = new BypassManager.BypassType();
    public static final /* enum */ BypassManager.BypassType ENCODED_BBS = new BypassManager.BypassType();
    public static final /* enum */ BypassManager.BypassType SIMPLE = new BypassManager.BypassType();
    public static final /* enum */ BypassManager.BypassType SIMPLE_BBS = new BypassManager.BypassType();
    public static final /* enum */ BypassManager.BypassType SIMPLE_DIRECT = new BypassManager.BypassType();
    private static final /* synthetic */ BypassManager.BypassType[] a;

    public static BypassManager.BypassType[] values() {
        return (BypassManager.BypassType[])a.clone();
    }

    public static BypassManager.BypassType valueOf(String string) {
        return Enum.valueOf(BypassManager.BypassType.class, string);
    }

    private static /* synthetic */ BypassManager.BypassType[] a() {
        return new BypassManager.BypassType[]{ENCODED, ENCODED_BBS, SIMPLE, SIMPLE_BBS, SIMPLE_DIRECT};
    }

    static {
        a = BypassManager.BypassType.a();
    }
}
