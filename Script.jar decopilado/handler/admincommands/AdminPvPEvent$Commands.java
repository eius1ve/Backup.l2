/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

private static final class AdminPvPEvent.Commands
extends Enum<AdminPvPEvent.Commands> {
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_active = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_setanntime = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_setannredu = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_countdown = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_settime = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_setinstsid = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_enable = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_capcha = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reg_type = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_hwid_restrict = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_ip_restrict = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_hide_identiti = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_dispell = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_dispell_after = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_kill_count_protection = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reqpart = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_maxpart = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_minlvl = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_maxlvl = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_evetime = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_ipkill = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_herorevhours = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_prohclid = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_revdel = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_buffprotect = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_afkprotect = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reward_team = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reward_lose_team = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reward_tie = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_reward_top = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_pvpevent_start = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_capt1 = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_tvt1 = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_dm1 = new AdminPvPEvent.Commands();
    public static final /* enum */ AdminPvPEvent.Commands admin_ctf1 = new AdminPvPEvent.Commands();
    private static final /* synthetic */ AdminPvPEvent.Commands[] a;

    public static AdminPvPEvent.Commands[] values() {
        return (AdminPvPEvent.Commands[])a.clone();
    }

    public static AdminPvPEvent.Commands valueOf(String string) {
        return Enum.valueOf(AdminPvPEvent.Commands.class, string);
    }

    private static /* synthetic */ AdminPvPEvent.Commands[] a() {
        return new AdminPvPEvent.Commands[]{admin_pvpevent, admin_pvpevent_active, admin_pvpevent_setanntime, admin_pvpevent_setannredu, admin_pvpevent_countdown, admin_pvpevent_settime, admin_pvpevent_setinstsid, admin_pvpevent_enable, admin_pvpevent_capcha, admin_pvpevent_reg_type, admin_pvpevent_hwid_restrict, admin_pvpevent_ip_restrict, admin_pvpevent_hide_identiti, admin_pvpevent_dispell, admin_pvpevent_dispell_after, admin_kill_count_protection, admin_pvpevent_reqpart, admin_pvpevent_maxpart, admin_pvpevent_minlvl, admin_pvpevent_maxlvl, admin_pvpevent_evetime, admin_pvpevent_ipkill, admin_pvpevent_herorevhours, admin_pvpevent_prohclid, admin_pvpevent_revdel, admin_pvpevent_buffprotect, admin_pvpevent_afkprotect, admin_pvpevent_reward_team, admin_pvpevent_reward_lose_team, admin_pvpevent_reward_tie, admin_pvpevent_reward_top, admin_pvpevent_start, admin_capt1, admin_tvt1, admin_dm1, admin_ctf1};
    }

    static {
        a = AdminPvPEvent.Commands.a();
    }
}
