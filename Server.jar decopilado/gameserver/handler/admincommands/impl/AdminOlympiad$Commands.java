/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminOlympiad.Commands
extends Enum<AdminOlympiad.Commands> {
    public static final /* enum */ AdminOlympiad.Commands admin_oly_save = new AdminOlympiad.Commands();
    public static final /* enum */ AdminOlympiad.Commands admin_add_oly_points = new AdminOlympiad.Commands();
    public static final /* enum */ AdminOlympiad.Commands admin_oly_start = new AdminOlympiad.Commands();
    public static final /* enum */ AdminOlympiad.Commands admin_add_hero = new AdminOlympiad.Commands();
    public static final /* enum */ AdminOlympiad.Commands admin_add_custom_hero = new AdminOlympiad.Commands();
    public static final /* enum */ AdminOlympiad.Commands admin_oly_stop = new AdminOlympiad.Commands();
    private static final /* synthetic */ AdminOlympiad.Commands[] a;

    public static AdminOlympiad.Commands[] values() {
        return (AdminOlympiad.Commands[])a.clone();
    }

    public static AdminOlympiad.Commands valueOf(String string) {
        return Enum.valueOf(AdminOlympiad.Commands.class, string);
    }

    private static /* synthetic */ AdminOlympiad.Commands[] a() {
        return new AdminOlympiad.Commands[]{admin_oly_save, admin_add_oly_points, admin_oly_start, admin_add_hero, admin_add_custom_hero, admin_oly_stop};
    }

    static {
        a = AdminOlympiad.Commands.a();
    }
}
