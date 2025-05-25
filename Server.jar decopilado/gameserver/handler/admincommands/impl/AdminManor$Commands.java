/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminManor.Commands
extends Enum<AdminManor.Commands> {
    public static final /* enum */ AdminManor.Commands admin_manor = new AdminManor.Commands();
    public static final /* enum */ AdminManor.Commands admin_manor_reset = new AdminManor.Commands();
    public static final /* enum */ AdminManor.Commands admin_manor_save = new AdminManor.Commands();
    public static final /* enum */ AdminManor.Commands admin_manor_disable = new AdminManor.Commands();
    private static final /* synthetic */ AdminManor.Commands[] a;

    public static AdminManor.Commands[] values() {
        return (AdminManor.Commands[])a.clone();
    }

    public static AdminManor.Commands valueOf(String string) {
        return Enum.valueOf(AdminManor.Commands.class, string);
    }

    private static /* synthetic */ AdminManor.Commands[] a() {
        return new AdminManor.Commands[]{admin_manor, admin_manor_reset, admin_manor_save, admin_manor_disable};
    }

    static {
        a = AdminManor.Commands.a();
    }
}
