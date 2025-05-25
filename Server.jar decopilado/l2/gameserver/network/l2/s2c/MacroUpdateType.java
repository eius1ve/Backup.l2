/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public final class MacroUpdateType
extends Enum<MacroUpdateType> {
    public static final /* enum */ MacroUpdateType ADD = new MacroUpdateType(1);
    public static final /* enum */ MacroUpdateType LIST = new MacroUpdateType(1);
    public static final /* enum */ MacroUpdateType MODIFY = new MacroUpdateType(2);
    public static final /* enum */ MacroUpdateType DELETE = new MacroUpdateType(0);
    private final int zp;
    private static final /* synthetic */ MacroUpdateType[] a;

    public static MacroUpdateType[] values() {
        return (MacroUpdateType[])a.clone();
    }

    public static MacroUpdateType valueOf(String string) {
        return Enum.valueOf(MacroUpdateType.class, string);
    }

    private MacroUpdateType(int n2) {
        this.zp = n2;
    }

    public int getId() {
        return this.zp;
    }

    private static /* synthetic */ MacroUpdateType[] a() {
        return new MacroUpdateType[]{ADD, LIST, MODIFY, DELETE};
    }

    static {
        a = MacroUpdateType.a();
    }
}
