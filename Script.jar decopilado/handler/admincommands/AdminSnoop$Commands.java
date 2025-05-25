/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

static final class AdminSnoop.Commands
extends Enum<AdminSnoop.Commands> {
    public static final /* enum */ AdminSnoop.Commands admin_snoop = new AdminSnoop.Commands();
    private static final /* synthetic */ AdminSnoop.Commands[] a;

    public static AdminSnoop.Commands[] values() {
        return (AdminSnoop.Commands[])a.clone();
    }

    public static AdminSnoop.Commands valueOf(String string) {
        return Enum.valueOf(AdminSnoop.Commands.class, string);
    }

    private static /* synthetic */ AdminSnoop.Commands[] a() {
        return new AdminSnoop.Commands[]{admin_snoop};
    }

    static {
        a = AdminSnoop.Commands.a();
    }
}
