/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminAdmin.Commands
extends Enum<AdminAdmin.Commands> {
    public static final /* enum */ AdminAdmin.Commands admin_admin = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_play_sounds = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_play_sound = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_silence = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_tradeoff = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_cfg = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_config = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_show_html = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_setnpcstate = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_setareanpcstate = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_showmovie = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_setzoneinfo = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_eventtrigger = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_debug = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_uievent = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_forcenpcinfo = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_loc = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_spawn_loc = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_spawn_pos = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_locdump = new AdminAdmin.Commands();
    public static final /* enum */ AdminAdmin.Commands admin_undying = new AdminAdmin.Commands();
    private static final /* synthetic */ AdminAdmin.Commands[] a;

    public static AdminAdmin.Commands[] values() {
        return (AdminAdmin.Commands[])a.clone();
    }

    public static AdminAdmin.Commands valueOf(String string) {
        return Enum.valueOf(AdminAdmin.Commands.class, string);
    }

    private static /* synthetic */ AdminAdmin.Commands[] a() {
        return new AdminAdmin.Commands[]{admin_admin, admin_play_sounds, admin_play_sound, admin_silence, admin_tradeoff, admin_cfg, admin_config, admin_show_html, admin_setnpcstate, admin_setareanpcstate, admin_showmovie, admin_setzoneinfo, admin_eventtrigger, admin_debug, admin_uievent, admin_forcenpcinfo, admin_loc, admin_spawn_loc, admin_spawn_pos, admin_locdump, admin_undying};
    }

    static {
        a = AdminAdmin.Commands.a();
    }
}
