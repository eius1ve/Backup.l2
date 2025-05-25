/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminCamera.Commands
extends Enum<AdminCamera.Commands> {
    public static final /* enum */ AdminCamera.Commands admin_freelook = new AdminCamera.Commands();
    public static final /* enum */ AdminCamera.Commands admin_cinematic = new AdminCamera.Commands();
    private static final /* synthetic */ AdminCamera.Commands[] a;

    public static AdminCamera.Commands[] values() {
        return (AdminCamera.Commands[])a.clone();
    }

    public static AdminCamera.Commands valueOf(String string) {
        return Enum.valueOf(AdminCamera.Commands.class, string);
    }

    private static /* synthetic */ AdminCamera.Commands[] a() {
        return new AdminCamera.Commands[]{admin_freelook, admin_cinematic};
    }

    static {
        a = AdminCamera.Commands.a();
    }
}
