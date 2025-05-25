/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminKill.Commands
extends Enum<AdminKill.Commands> {
    public static final /* enum */ AdminKill.Commands admin_kill = new AdminKill.Commands();
    public static final /* enum */ AdminKill.Commands admin_damage = new AdminKill.Commands();
    private static final /* synthetic */ AdminKill.Commands[] a;

    public static AdminKill.Commands[] values() {
        return (AdminKill.Commands[])a.clone();
    }

    public static AdminKill.Commands valueOf(String string) {
        return Enum.valueOf(AdminKill.Commands.class, string);
    }

    private static /* synthetic */ AdminKill.Commands[] a() {
        return new AdminKill.Commands[]{admin_kill, admin_damage};
    }

    static {
        a = AdminKill.Commands.a();
    }
}
