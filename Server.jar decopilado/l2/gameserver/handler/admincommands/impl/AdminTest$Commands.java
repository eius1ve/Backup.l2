/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminTest.Commands
extends Enum<AdminTest.Commands> {
    public static final /* enum */ AdminTest.Commands admin_collapse_this = new AdminTest.Commands();
    public static final /* enum */ AdminTest.Commands admin_collapse_this2 = new AdminTest.Commands();
    public static final /* enum */ AdminTest.Commands admin_alt_move_000 = new AdminTest.Commands();
    public static final /* enum */ AdminTest.Commands admin_alt_move_rnd = new AdminTest.Commands();
    private static final /* synthetic */ AdminTest.Commands[] a;

    public static AdminTest.Commands[] values() {
        return (AdminTest.Commands[])a.clone();
    }

    public static AdminTest.Commands valueOf(String string) {
        return Enum.valueOf(AdminTest.Commands.class, string);
    }

    private static /* synthetic */ AdminTest.Commands[] a() {
        return new AdminTest.Commands[]{admin_collapse_this, admin_collapse_this2, admin_alt_move_000, admin_alt_move_rnd};
    }

    static {
        a = AdminTest.Commands.a();
    }
}
