/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminCursedWeapons.Commands
extends Enum<AdminCursedWeapons.Commands> {
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_info = new AdminCursedWeapons.Commands();
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_remove = new AdminCursedWeapons.Commands();
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_goto = new AdminCursedWeapons.Commands();
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_reload = new AdminCursedWeapons.Commands();
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_add = new AdminCursedWeapons.Commands();
    public static final /* enum */ AdminCursedWeapons.Commands admin_cw_drop = new AdminCursedWeapons.Commands();
    private static final /* synthetic */ AdminCursedWeapons.Commands[] a;

    public static AdminCursedWeapons.Commands[] values() {
        return (AdminCursedWeapons.Commands[])a.clone();
    }

    public static AdminCursedWeapons.Commands valueOf(String string) {
        return Enum.valueOf(AdminCursedWeapons.Commands.class, string);
    }

    private static /* synthetic */ AdminCursedWeapons.Commands[] a() {
        return new AdminCursedWeapons.Commands[]{admin_cw_info, admin_cw_remove, admin_cw_goto, admin_cw_reload, admin_cw_add, admin_cw_drop};
    }

    static {
        a = AdminCursedWeapons.Commands.a();
    }
}
