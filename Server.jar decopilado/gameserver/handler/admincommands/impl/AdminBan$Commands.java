/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminBan.Commands
extends Enum<AdminBan.Commands> {
    public static final /* enum */ AdminBan.Commands admin_ban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_unban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_hwidban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_cban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_chatban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_nospam = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_unblockspam = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_chatunban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_accban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_accunban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_trade_ban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_trade_unban = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_jail = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_unjail = new AdminBan.Commands();
    public static final /* enum */ AdminBan.Commands admin_permaban = new AdminBan.Commands();
    private static final /* synthetic */ AdminBan.Commands[] a;

    public static AdminBan.Commands[] values() {
        return (AdminBan.Commands[])a.clone();
    }

    public static AdminBan.Commands valueOf(String string) {
        return Enum.valueOf(AdminBan.Commands.class, string);
    }

    private static /* synthetic */ AdminBan.Commands[] a() {
        return new AdminBan.Commands[]{admin_ban, admin_unban, admin_hwidban, admin_cban, admin_chatban, admin_nospam, admin_unblockspam, admin_chatunban, admin_accban, admin_accunban, admin_trade_ban, admin_trade_unban, admin_jail, admin_unjail, admin_permaban};
    }

    static {
        a = AdminBan.Commands.a();
    }
}
