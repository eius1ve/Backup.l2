/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

import handler.admincommands.AdminPvPEvent;

static class AdminPvPEvent.1 {
    static final /* synthetic */ int[] $SwitchMap$handler$admincommands$AdminPvPEvent$Commands;

    static {
        $SwitchMap$handler$admincommands$AdminPvPEvent$Commands = new int[AdminPvPEvent.Commands.values().length];
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_active.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_setanntime.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_setannredu.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_countdown.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reg_type.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_settime.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_setinstsid.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_enable.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_capcha.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_hwid_restrict.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_ip_restrict.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_hide_identiti.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_dispell.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_dispell_after.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_kill_count_protection.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reqpart.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_maxpart.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_minlvl.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_maxlvl.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_evetime.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_prohclid.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_revdel.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_buffprotect.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_afkprotect.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_ipkill.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_herorevhours.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reward_team.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reward_lose_team.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reward_tie.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_reward_top.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_pvpevent_start.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_capt1.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_tvt1.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_dm1.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPvPEvent.1.$SwitchMap$handler$admincommands$AdminPvPEvent$Commands[AdminPvPEvent.Commands.admin_ctf1.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
