/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminReload.Commands
extends Enum<AdminReload.Commands> {
    public static final /* enum */ AdminReload.Commands admin_reload = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_config = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_multisell = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_gmaccess = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_htm = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_qr = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_qs = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_qs_help = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_skills = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_items = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_npc = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_spawn = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_fish = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_chatfilters = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_auto_announce = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_translit = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_shops = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_static = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_pets = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_locale = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_nobles = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_promo = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_prime_shop = new AdminReload.Commands();
    public static final /* enum */ AdminReload.Commands admin_reload_instances = new AdminReload.Commands();
    private static final /* synthetic */ AdminReload.Commands[] a;

    public static AdminReload.Commands[] values() {
        return (AdminReload.Commands[])a.clone();
    }

    public static AdminReload.Commands valueOf(String string) {
        return Enum.valueOf(AdminReload.Commands.class, string);
    }

    private static /* synthetic */ AdminReload.Commands[] a() {
        return new AdminReload.Commands[]{admin_reload, admin_reload_config, admin_reload_multisell, admin_reload_gmaccess, admin_reload_htm, admin_reload_qr, admin_reload_qs, admin_reload_qs_help, admin_reload_skills, admin_reload_items, admin_reload_npc, admin_reload_spawn, admin_reload_fish, admin_reload_chatfilters, admin_reload_auto_announce, admin_reload_translit, admin_reload_shops, admin_reload_static, admin_reload_pets, admin_reload_locale, admin_reload_nobles, admin_reload_promo, admin_reload_prime_shop, admin_reload_instances};
    }

    static {
        a = AdminReload.Commands.a();
    }
}
