/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminChangeAccessLevel.Commands
extends Enum<AdminChangeAccessLevel.Commands> {
    public static final /* enum */ AdminChangeAccessLevel.Commands admin_changelvl = new AdminChangeAccessLevel.Commands();
    public static final /* enum */ AdminChangeAccessLevel.Commands admin_moders = new AdminChangeAccessLevel.Commands();
    public static final /* enum */ AdminChangeAccessLevel.Commands admin_moders_add = new AdminChangeAccessLevel.Commands();
    public static final /* enum */ AdminChangeAccessLevel.Commands admin_moders_del = new AdminChangeAccessLevel.Commands();
    public static final /* enum */ AdminChangeAccessLevel.Commands admin_penalty = new AdminChangeAccessLevel.Commands();
    private static final /* synthetic */ AdminChangeAccessLevel.Commands[] a;

    public static AdminChangeAccessLevel.Commands[] values() {
        return (AdminChangeAccessLevel.Commands[])a.clone();
    }

    public static AdminChangeAccessLevel.Commands valueOf(String string) {
        return Enum.valueOf(AdminChangeAccessLevel.Commands.class, string);
    }

    private static /* synthetic */ AdminChangeAccessLevel.Commands[] a() {
        return new AdminChangeAccessLevel.Commands[]{admin_changelvl, admin_moders, admin_moders_add, admin_moders_del, admin_penalty};
    }

    static {
        a = AdminChangeAccessLevel.Commands.a();
    }
}
