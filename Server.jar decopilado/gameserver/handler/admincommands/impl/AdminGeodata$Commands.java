/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminGeodata.Commands
extends Enum<AdminGeodata.Commands> {
    public static final /* enum */ AdminGeodata.Commands admin_geo_z = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_type = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_nswe = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_los = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_move = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_trace = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geo_map = new AdminGeodata.Commands();
    public static final /* enum */ AdminGeodata.Commands admin_geogrid = new AdminGeodata.Commands();
    private static final /* synthetic */ AdminGeodata.Commands[] a;

    public static AdminGeodata.Commands[] values() {
        return (AdminGeodata.Commands[])a.clone();
    }

    public static AdminGeodata.Commands valueOf(String string) {
        return Enum.valueOf(AdminGeodata.Commands.class, string);
    }

    private static /* synthetic */ AdminGeodata.Commands[] a() {
        return new AdminGeodata.Commands[]{admin_geo_z, admin_geo_type, admin_geo_nswe, admin_geo_los, admin_geo_move, admin_geo_trace, admin_geo_map, admin_geogrid};
    }

    static {
        a = AdminGeodata.Commands.a();
    }
}
