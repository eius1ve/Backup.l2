/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminDoorControl.Commands
extends Enum<AdminDoorControl.Commands> {
    public static final /* enum */ AdminDoorControl.Commands admin_open = new AdminDoorControl.Commands();
    public static final /* enum */ AdminDoorControl.Commands admin_close = new AdminDoorControl.Commands();
    private static final /* synthetic */ AdminDoorControl.Commands[] a;

    public static AdminDoorControl.Commands[] values() {
        return (AdminDoorControl.Commands[])a.clone();
    }

    public static AdminDoorControl.Commands valueOf(String string) {
        return Enum.valueOf(AdminDoorControl.Commands.class, string);
    }

    private static /* synthetic */ AdminDoorControl.Commands[] a() {
        return new AdminDoorControl.Commands[]{admin_open, admin_close};
    }

    static {
        a = AdminDoorControl.Commands.a();
    }
}
