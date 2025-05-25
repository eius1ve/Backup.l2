/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminRide.Commands
extends Enum<AdminRide.Commands> {
    public static final /* enum */ AdminRide.Commands admin_ride = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_ride_wyvern = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_ride_strider = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_unride = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_wr = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_sr = new AdminRide.Commands();
    public static final /* enum */ AdminRide.Commands admin_ur = new AdminRide.Commands();
    private static final /* synthetic */ AdminRide.Commands[] a;

    public static AdminRide.Commands[] values() {
        return (AdminRide.Commands[])a.clone();
    }

    public static AdminRide.Commands valueOf(String string) {
        return Enum.valueOf(AdminRide.Commands.class, string);
    }

    private static /* synthetic */ AdminRide.Commands[] a() {
        return new AdminRide.Commands[]{admin_ride, admin_ride_wyvern, admin_ride_strider, admin_unride, admin_wr, admin_sr, admin_ur};
    }

    static {
        a = AdminRide.Commands.a();
    }
}
