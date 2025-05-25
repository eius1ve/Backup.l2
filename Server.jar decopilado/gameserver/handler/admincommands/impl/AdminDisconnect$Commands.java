/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminDisconnect.Commands
extends Enum<AdminDisconnect.Commands> {
    public static final /* enum */ AdminDisconnect.Commands admin_disconnect = new AdminDisconnect.Commands();
    public static final /* enum */ AdminDisconnect.Commands admin_kick = new AdminDisconnect.Commands();
    private static final /* synthetic */ AdminDisconnect.Commands[] a;

    public static AdminDisconnect.Commands[] values() {
        return (AdminDisconnect.Commands[])a.clone();
    }

    public static AdminDisconnect.Commands valueOf(String string) {
        return Enum.valueOf(AdminDisconnect.Commands.class, string);
    }

    private static /* synthetic */ AdminDisconnect.Commands[] a() {
        return new AdminDisconnect.Commands[]{admin_disconnect, admin_kick};
    }

    static {
        a = AdminDisconnect.Commands.a();
    }
}
