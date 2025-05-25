/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminServer.Commands
extends Enum<AdminServer.Commands> {
    public static final /* enum */ AdminServer.Commands admin_server = new AdminServer.Commands();
    public static final /* enum */ AdminServer.Commands admin_server_info = new AdminServer.Commands();
    public static final /* enum */ AdminServer.Commands admin_check_actor = new AdminServer.Commands();
    public static final /* enum */ AdminServer.Commands admin_setvar = new AdminServer.Commands();
    public static final /* enum */ AdminServer.Commands admin_set_ai_interval = new AdminServer.Commands();
    public static final /* enum */ AdminServer.Commands admin_spawn2 = new AdminServer.Commands();
    private static final /* synthetic */ AdminServer.Commands[] a;

    public static AdminServer.Commands[] values() {
        return (AdminServer.Commands[])a.clone();
    }

    public static AdminServer.Commands valueOf(String string) {
        return Enum.valueOf(AdminServer.Commands.class, string);
    }

    private static /* synthetic */ AdminServer.Commands[] a() {
        return new AdminServer.Commands[]{admin_server, admin_server_info, admin_check_actor, admin_setvar, admin_set_ai_interval, admin_spawn2};
    }

    static {
        a = AdminServer.Commands.a();
    }
}
