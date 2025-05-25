/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminZone.Commands
extends Enum<AdminZone.Commands> {
    public static final /* enum */ AdminZone.Commands admin_zones = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_zone_check = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_region = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_pos = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_vis_count = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_domain = new AdminZone.Commands();
    public static final /* enum */ AdminZone.Commands admin_zone_visual = new AdminZone.Commands();
    private static final /* synthetic */ AdminZone.Commands[] a;

    public static AdminZone.Commands[] values() {
        return (AdminZone.Commands[])a.clone();
    }

    public static AdminZone.Commands valueOf(String string) {
        return Enum.valueOf(AdminZone.Commands.class, string);
    }

    private static /* synthetic */ AdminZone.Commands[] a() {
        return new AdminZone.Commands[]{admin_zones, admin_zone_check, admin_region, admin_pos, admin_vis_count, admin_domain, admin_zone_visual};
    }

    static {
        a = AdminZone.Commands.a();
    }
}
