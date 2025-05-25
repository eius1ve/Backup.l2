/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.components;

public final class SysString
extends Enum<SysString> {
    public static final /* enum */ SysString PASSENGER_BOAT_INFO = new SysString(801);
    public static final /* enum */ SysString PREVIOUS = new SysString(1037);
    public static final /* enum */ SysString NEXT = new SysString(1038);
    private static final SysString[] a;
    private final int st;
    private static final /* synthetic */ SysString[] b;

    public static SysString[] values() {
        return (SysString[])b.clone();
    }

    public static SysString valueOf(String string) {
        return Enum.valueOf(SysString.class, string);
    }

    private SysString(int n2) {
        this.st = n2;
    }

    public int getId() {
        return this.st;
    }

    public static SysString valueOf2(String string) {
        for (SysString sysString : a) {
            if (!sysString.name().equals(string)) continue;
            return sysString;
        }
        return null;
    }

    public static SysString valueOf(int n) {
        for (SysString sysString : a) {
            if (sysString.getId() != n) continue;
            return sysString;
        }
        return null;
    }

    private static /* synthetic */ SysString[] a() {
        return new SysString[]{PASSENGER_BOAT_INFO, PREVIOUS, NEXT};
    }

    static {
        b = SysString.a();
        a = SysString.values();
    }
}
