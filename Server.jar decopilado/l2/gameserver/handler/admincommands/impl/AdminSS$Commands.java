/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminSS.Commands
extends Enum<AdminSS.Commands> {
    public static final /* enum */ AdminSS.Commands admin_ssq_change = new AdminSS.Commands();
    public static final /* enum */ AdminSS.Commands admin_ssq_time = new AdminSS.Commands();
    public static final /* enum */ AdminSS.Commands admin_ssq_cabal = new AdminSS.Commands();
    private static final /* synthetic */ AdminSS.Commands[] a;

    public static AdminSS.Commands[] values() {
        return (AdminSS.Commands[])a.clone();
    }

    public static AdminSS.Commands valueOf(String string) {
        return Enum.valueOf(AdminSS.Commands.class, string);
    }

    private static /* synthetic */ AdminSS.Commands[] a() {
        return new AdminSS.Commands[]{admin_ssq_change, admin_ssq_time, admin_ssq_cabal};
    }

    static {
        a = AdminSS.Commands.a();
    }
}
