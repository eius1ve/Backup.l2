/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminSpawn.Commands
extends Enum<AdminSpawn.Commands> {
    public static final /* enum */ AdminSpawn.Commands admin_show_spawns = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_show_npcs = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_despawn_all = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn_day = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn_all = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn_night = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn_monster = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn_index = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_spawn1 = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_setheading = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_setai = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_setaiparam = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_dumpparams = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_generate_loc = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_dumpspawntasks = new AdminSpawn.Commands();
    public static final /* enum */ AdminSpawn.Commands admin_dumpspawn = new AdminSpawn.Commands();
    private static final /* synthetic */ AdminSpawn.Commands[] a;

    public static AdminSpawn.Commands[] values() {
        return (AdminSpawn.Commands[])a.clone();
    }

    public static AdminSpawn.Commands valueOf(String string) {
        return Enum.valueOf(AdminSpawn.Commands.class, string);
    }

    private static /* synthetic */ AdminSpawn.Commands[] a() {
        return new AdminSpawn.Commands[]{admin_show_spawns, admin_show_npcs, admin_spawn, admin_despawn_all, admin_spawn_day, admin_spawn_all, admin_spawn_night, admin_spawn_monster, admin_spawn_index, admin_spawn1, admin_setheading, admin_setai, admin_setaiparam, admin_dumpparams, admin_generate_loc, admin_dumpspawntasks, admin_dumpspawn};
    }

    static {
        a = AdminSpawn.Commands.a();
    }
}
