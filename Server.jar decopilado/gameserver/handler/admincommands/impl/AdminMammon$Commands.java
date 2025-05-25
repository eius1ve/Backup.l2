/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminMammon.Commands
extends Enum<AdminMammon.Commands> {
    public static final /* enum */ AdminMammon.Commands admin_find_mammon = new AdminMammon.Commands();
    public static final /* enum */ AdminMammon.Commands admin_show_mammon = new AdminMammon.Commands();
    public static final /* enum */ AdminMammon.Commands admin_hide_mammon = new AdminMammon.Commands();
    public static final /* enum */ AdminMammon.Commands admin_list_spawns = new AdminMammon.Commands();
    private static final /* synthetic */ AdminMammon.Commands[] a;

    public static AdminMammon.Commands[] values() {
        return (AdminMammon.Commands[])a.clone();
    }

    public static AdminMammon.Commands valueOf(String string) {
        return Enum.valueOf(AdminMammon.Commands.class, string);
    }

    private static /* synthetic */ AdminMammon.Commands[] a() {
        return new AdminMammon.Commands[]{admin_find_mammon, admin_show_mammon, admin_hide_mammon, admin_list_spawns};
    }

    static {
        a = AdminMammon.Commands.a();
    }
}
