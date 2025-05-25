/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminIP.Commands
extends Enum<AdminIP.Commands> {
    public static final /* enum */ AdminIP.Commands admin_charip = new AdminIP.Commands();
    public static final /* enum */ AdminIP.Commands admin_charhwid = new AdminIP.Commands();
    private static final /* synthetic */ AdminIP.Commands[] a;

    public static AdminIP.Commands[] values() {
        return (AdminIP.Commands[])a.clone();
    }

    public static AdminIP.Commands valueOf(String string) {
        return Enum.valueOf(AdminIP.Commands.class, string);
    }

    private static /* synthetic */ AdminIP.Commands[] a() {
        return new AdminIP.Commands[]{admin_charip, admin_charhwid};
    }

    static {
        a = AdminIP.Commands.a();
    }
}
