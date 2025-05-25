/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminSkill.Commands
extends Enum<AdminSkill.Commands> {
    public static final /* enum */ AdminSkill.Commands admin_show_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_remove_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_skill_list = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_skill_index = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_add_skill = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_remove_skill = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_get_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_delete_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_reset_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_give_all_skills = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_show_effects = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_stop_effect = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_debug_stats = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_remove_cooldown = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_buff = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_skill_ench = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_skill_enchant = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_add_clan_skill = new AdminSkill.Commands();
    public static final /* enum */ AdminSkill.Commands admin_give_all_clan_skills = new AdminSkill.Commands();
    private static final /* synthetic */ AdminSkill.Commands[] a;

    public static AdminSkill.Commands[] values() {
        return (AdminSkill.Commands[])a.clone();
    }

    public static AdminSkill.Commands valueOf(String string) {
        return Enum.valueOf(AdminSkill.Commands.class, string);
    }

    private static /* synthetic */ AdminSkill.Commands[] a() {
        return new AdminSkill.Commands[]{admin_show_skills, admin_remove_skills, admin_skill_list, admin_skill_index, admin_add_skill, admin_remove_skill, admin_get_skills, admin_delete_skills, admin_reset_skills, admin_give_all_skills, admin_show_effects, admin_stop_effect, admin_debug_stats, admin_remove_cooldown, admin_buff, admin_skill_ench, admin_skill_enchant, admin_add_clan_skill, admin_give_all_clan_skills};
    }

    static {
        a = AdminSkill.Commands.a();
    }
}
