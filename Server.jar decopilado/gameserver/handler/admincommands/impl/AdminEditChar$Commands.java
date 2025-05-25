/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminEditChar.Commands
extends Enum<AdminEditChar.Commands> {
    public static final /* enum */ AdminEditChar.Commands admin_edit_character = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_character_actions = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_current_player = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_nokarma = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setkarma = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_character_list = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_show_characters = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_show_characters_by_ip = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_show_characters_by_hwid = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_find_character = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_save_modifications = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_rec = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_settitle = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setclass = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setname = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setsex = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setcolor = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setcolortitle = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_exp_sp_to_character = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_exp_sp = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_sethero = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setnoble = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_trans = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setsubclass = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setfame = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_setbday = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_give_item = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_give_all = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_give_all_by_ip = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_give_all_by_hwid = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_give_all_radius = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_wp = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_remove_item = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_destroy_items = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_bang = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_vip_points = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_set_bang = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_set_raidpoints = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_add_raidpoints = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_set_pa = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_set_aug = new AdminEditChar.Commands();
    public static final /* enum */ AdminEditChar.Commands admin_unset_aug = new AdminEditChar.Commands();
    private static final /* synthetic */ AdminEditChar.Commands[] a;

    public static AdminEditChar.Commands[] values() {
        return (AdminEditChar.Commands[])a.clone();
    }

    public static AdminEditChar.Commands valueOf(String string) {
        return Enum.valueOf(AdminEditChar.Commands.class, string);
    }

    private static /* synthetic */ AdminEditChar.Commands[] a() {
        return new AdminEditChar.Commands[]{admin_edit_character, admin_character_actions, admin_current_player, admin_nokarma, admin_setkarma, admin_character_list, admin_show_characters, admin_show_characters_by_ip, admin_show_characters_by_hwid, admin_find_character, admin_save_modifications, admin_rec, admin_settitle, admin_setclass, admin_setname, admin_setsex, admin_setcolor, admin_setcolortitle, admin_add_exp_sp_to_character, admin_add_exp_sp, admin_sethero, admin_setnoble, admin_trans, admin_setsubclass, admin_setfame, admin_setbday, admin_give_item, admin_give_all, admin_give_all_by_ip, admin_give_all_by_hwid, admin_give_all_radius, admin_add_wp, admin_remove_item, admin_destroy_items, admin_add_bang, admin_add_vip_points, admin_set_bang, admin_set_raidpoints, admin_add_raidpoints, admin_set_pa, admin_set_aug, admin_unset_aug};
    }

    static {
        a = AdminEditChar.Commands.a();
    }
}
