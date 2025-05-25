/*
 * Decompiled with CFR 0.152.
 */
package bosses;

private static final class FrintezzaManager.Commands
extends Enum<FrintezzaManager.Commands> {
    public static final /* enum */ FrintezzaManager.Commands admin_fri_startup = new FrintezzaManager.Commands();
    public static final /* enum */ FrintezzaManager.Commands admin_fri_cleanup = new FrintezzaManager.Commands();
    public static final /* enum */ FrintezzaManager.Commands admin_fri_go = new FrintezzaManager.Commands();
    public static final /* enum */ FrintezzaManager.Commands admin_fri_devdump = new FrintezzaManager.Commands();
    public static final /* enum */ FrintezzaManager.Commands admin_fri_musdump = new FrintezzaManager.Commands();
    private static final /* synthetic */ FrintezzaManager.Commands[] a;

    public static FrintezzaManager.Commands[] values() {
        return (FrintezzaManager.Commands[])a.clone();
    }

    public static FrintezzaManager.Commands valueOf(String string) {
        return Enum.valueOf(FrintezzaManager.Commands.class, string);
    }

    private static /* synthetic */ FrintezzaManager.Commands[] a() {
        return new FrintezzaManager.Commands[]{admin_fri_startup, admin_fri_cleanup, admin_fri_go, admin_fri_devdump, admin_fri_musdump};
    }

    static {
        a = FrintezzaManager.Commands.a();
    }
}
