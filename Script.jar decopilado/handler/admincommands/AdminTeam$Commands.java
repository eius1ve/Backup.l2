/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

static final class AdminTeam.Commands
extends Enum<AdminTeam.Commands> {
    public static final /* enum */ AdminTeam.Commands admin_setteam = new AdminTeam.Commands();
    private static final /* synthetic */ AdminTeam.Commands[] a;

    public static AdminTeam.Commands[] values() {
        return (AdminTeam.Commands[])a.clone();
    }

    public static AdminTeam.Commands valueOf(String string) {
        return Enum.valueOf(AdminTeam.Commands.class, string);
    }

    private static /* synthetic */ AdminTeam.Commands[] a() {
        return new AdminTeam.Commands[]{admin_setteam};
    }

    static {
        a = AdminTeam.Commands.a();
    }
}
