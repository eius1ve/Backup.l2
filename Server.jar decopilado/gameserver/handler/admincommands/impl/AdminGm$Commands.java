/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminGm.Commands
extends Enum<AdminGm.Commands> {
    public static final /* enum */ AdminGm.Commands admin_gm = new AdminGm.Commands();
    private static final /* synthetic */ AdminGm.Commands[] a;

    public static AdminGm.Commands[] values() {
        return (AdminGm.Commands[])a.clone();
    }

    public static AdminGm.Commands valueOf(String string) {
        return Enum.valueOf(AdminGm.Commands.class, string);
    }

    private static /* synthetic */ AdminGm.Commands[] a() {
        return new AdminGm.Commands[]{admin_gm};
    }

    static {
        a = AdminGm.Commands.a();
    }
}
