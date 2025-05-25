/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminDelete.Commands
extends Enum<AdminDelete.Commands> {
    public static final /* enum */ AdminDelete.Commands admin_delete = new AdminDelete.Commands();
    private static final /* synthetic */ AdminDelete.Commands[] a;

    public static AdminDelete.Commands[] values() {
        return (AdminDelete.Commands[])a.clone();
    }

    public static AdminDelete.Commands valueOf(String string) {
        return Enum.valueOf(AdminDelete.Commands.class, string);
    }

    private static /* synthetic */ AdminDelete.Commands[] a() {
        return new AdminDelete.Commands[]{admin_delete};
    }

    static {
        a = AdminDelete.Commands.a();
    }
}
