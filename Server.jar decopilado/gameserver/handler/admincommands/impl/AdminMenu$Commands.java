/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminMenu.Commands
extends Enum<AdminMenu.Commands> {
    public static final /* enum */ AdminMenu.Commands admin_char_manage = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_teleport_character_to_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_recall_char_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_goto_char_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_kick_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_kill_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_ban_menu = new AdminMenu.Commands();
    public static final /* enum */ AdminMenu.Commands admin_unban_menu = new AdminMenu.Commands();
    private static final /* synthetic */ AdminMenu.Commands[] a;

    public static AdminMenu.Commands[] values() {
        return (AdminMenu.Commands[])a.clone();
    }

    public static AdminMenu.Commands valueOf(String string) {
        return Enum.valueOf(AdminMenu.Commands.class, string);
    }

    private static /* synthetic */ AdminMenu.Commands[] a() {
        return new AdminMenu.Commands[]{admin_char_manage, admin_teleport_character_to_menu, admin_recall_char_menu, admin_goto_char_menu, admin_kick_menu, admin_kill_menu, admin_ban_menu, admin_unban_menu};
    }

    static {
        a = AdminMenu.Commands.a();
    }
}
