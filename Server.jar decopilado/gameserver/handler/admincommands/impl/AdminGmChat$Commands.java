/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminGmChat.Commands
extends Enum<AdminGmChat.Commands> {
    public static final /* enum */ AdminGmChat.Commands admin_gmchat = new AdminGmChat.Commands();
    public static final /* enum */ AdminGmChat.Commands admin_snoop = new AdminGmChat.Commands();
    private static final /* synthetic */ AdminGmChat.Commands[] a;

    public static AdminGmChat.Commands[] values() {
        return (AdminGmChat.Commands[])a.clone();
    }

    public static AdminGmChat.Commands valueOf(String string) {
        return Enum.valueOf(AdminGmChat.Commands.class, string);
    }

    private static /* synthetic */ AdminGmChat.Commands[] a() {
        return new AdminGmChat.Commands[]{admin_gmchat, admin_snoop};
    }

    static {
        a = AdminGmChat.Commands.a();
    }
}
