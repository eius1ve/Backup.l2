/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

static final class AdminZoneBuilder.Commands
extends Enum<AdminZoneBuilder.Commands> {
    public static final /* enum */ AdminZoneBuilder.Commands admin_zone_panel = new AdminZoneBuilder.Commands();
    public static final /* enum */ AdminZoneBuilder.Commands admin_zone_add = new AdminZoneBuilder.Commands();
    public static final /* enum */ AdminZoneBuilder.Commands admin_zone_del = new AdminZoneBuilder.Commands();
    public static final /* enum */ AdminZoneBuilder.Commands admin_zone_visualize = new AdminZoneBuilder.Commands();
    public static final /* enum */ AdminZoneBuilder.Commands admin_zone_dump = new AdminZoneBuilder.Commands();
    private static final /* synthetic */ AdminZoneBuilder.Commands[] a;

    public static AdminZoneBuilder.Commands[] values() {
        return (AdminZoneBuilder.Commands[])a.clone();
    }

    public static AdminZoneBuilder.Commands valueOf(String string) {
        return Enum.valueOf(AdminZoneBuilder.Commands.class, string);
    }

    private static /* synthetic */ AdminZoneBuilder.Commands[] a() {
        return new AdminZoneBuilder.Commands[]{admin_zone_panel, admin_zone_add, admin_zone_del, admin_zone_visualize, admin_zone_dump};
    }

    static {
        a = AdminZoneBuilder.Commands.a();
    }
}
