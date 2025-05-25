/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminCancel.Commands
extends Enum<AdminCancel.Commands> {
    public static final /* enum */ AdminCancel.Commands admin_cancel = new AdminCancel.Commands();
    public static final /* enum */ AdminCancel.Commands admin_check_cancel = new AdminCancel.Commands();
    private static final /* synthetic */ AdminCancel.Commands[] a;

    public static AdminCancel.Commands[] values() {
        return (AdminCancel.Commands[])a.clone();
    }

    public static AdminCancel.Commands valueOf(String string) {
        return Enum.valueOf(AdminCancel.Commands.class, string);
    }

    private static /* synthetic */ AdminCancel.Commands[] a() {
        return new AdminCancel.Commands[]{admin_cancel, admin_check_cancel};
    }

    static {
        a = AdminCancel.Commands.a();
    }
}
