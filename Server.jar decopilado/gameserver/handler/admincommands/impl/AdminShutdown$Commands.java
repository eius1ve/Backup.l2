/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminShutdown.Commands
extends Enum<AdminShutdown.Commands> {
    public static final /* enum */ AdminShutdown.Commands admin_server_shutdown = new AdminShutdown.Commands();
    public static final /* enum */ AdminShutdown.Commands admin_server_restart = new AdminShutdown.Commands();
    public static final /* enum */ AdminShutdown.Commands admin_server_abort = new AdminShutdown.Commands();
    private static final /* synthetic */ AdminShutdown.Commands[] a;

    public static AdminShutdown.Commands[] values() {
        return (AdminShutdown.Commands[])a.clone();
    }

    public static AdminShutdown.Commands valueOf(String string) {
        return Enum.valueOf(AdminShutdown.Commands.class, string);
    }

    private static /* synthetic */ AdminShutdown.Commands[] a() {
        return new AdminShutdown.Commands[]{admin_server_shutdown, admin_server_restart, admin_server_abort};
    }

    static {
        a = AdminShutdown.Commands.a();
    }
}
