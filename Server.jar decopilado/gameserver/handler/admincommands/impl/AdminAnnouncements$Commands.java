/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminAnnouncements.Commands
extends Enum<AdminAnnouncements.Commands> {
    public static final /* enum */ AdminAnnouncements.Commands admin_list_announcements = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_announce_announcements = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_add_announcement = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_del_announcement = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_announce = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_a = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_announce_menu = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_crit_announce = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_c = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_toscreen = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_s = new AdminAnnouncements.Commands();
    public static final /* enum */ AdminAnnouncements.Commands admin_reload_announcements = new AdminAnnouncements.Commands();
    private static final /* synthetic */ AdminAnnouncements.Commands[] a;

    public static AdminAnnouncements.Commands[] values() {
        return (AdminAnnouncements.Commands[])a.clone();
    }

    public static AdminAnnouncements.Commands valueOf(String string) {
        return Enum.valueOf(AdminAnnouncements.Commands.class, string);
    }

    private static /* synthetic */ AdminAnnouncements.Commands[] a() {
        return new AdminAnnouncements.Commands[]{admin_list_announcements, admin_announce_announcements, admin_add_announcement, admin_del_announcement, admin_announce, admin_a, admin_announce_menu, admin_crit_announce, admin_c, admin_toscreen, admin_s, admin_reload_announcements};
    }

    static {
        a = AdminAnnouncements.Commands.a();
    }
}
