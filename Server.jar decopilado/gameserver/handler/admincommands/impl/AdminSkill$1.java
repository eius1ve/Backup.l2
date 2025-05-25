/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminSkill;

static class AdminSkill.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands = new int[AdminSkill.Commands.values().length];
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_show_skills.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_show_effects.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_stop_effect.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_remove_skills.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_delete_skills.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_skill_list.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_skill_index.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_add_skill.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_remove_skill.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_get_skills.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_reset_skills.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_give_all_skills.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_debug_stats.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_remove_cooldown.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_buff.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_skill_ench.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_skill_enchant.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_add_clan_skill.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminSkill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminSkill$Commands[AdminSkill.Commands.admin_give_all_clan_skills.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
