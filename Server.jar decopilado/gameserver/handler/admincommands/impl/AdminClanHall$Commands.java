/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminClanHall.Commands
extends Enum<AdminClanHall.Commands> {
    public static final /* enum */ AdminClanHall.Commands admin_clanhall = new AdminClanHall.Commands();
    public static final /* enum */ AdminClanHall.Commands admin_clanhallset = new AdminClanHall.Commands();
    public static final /* enum */ AdminClanHall.Commands admin_clanhalldel = new AdminClanHall.Commands();
    public static final /* enum */ AdminClanHall.Commands admin_clanhallteleportself = new AdminClanHall.Commands();
    private static final /* synthetic */ AdminClanHall.Commands[] a;

    public static AdminClanHall.Commands[] values() {
        return (AdminClanHall.Commands[])a.clone();
    }

    public static AdminClanHall.Commands valueOf(String string) {
        return Enum.valueOf(AdminClanHall.Commands.class, string);
    }

    private static /* synthetic */ AdminClanHall.Commands[] a() {
        return new AdminClanHall.Commands[]{admin_clanhall, admin_clanhallset, admin_clanhalldel, admin_clanhallteleportself};
    }

    static {
        a = AdminClanHall.Commands.a();
    }
}
