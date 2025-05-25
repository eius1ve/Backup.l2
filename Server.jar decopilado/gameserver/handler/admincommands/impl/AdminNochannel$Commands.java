/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminNochannel.Commands
extends Enum<AdminNochannel.Commands> {
    public static final /* enum */ AdminNochannel.Commands admin_nochannel = new AdminNochannel.Commands();
    public static final /* enum */ AdminNochannel.Commands admin_nc = new AdminNochannel.Commands();
    private static final /* synthetic */ AdminNochannel.Commands[] a;

    public static AdminNochannel.Commands[] values() {
        return (AdminNochannel.Commands[])a.clone();
    }

    public static AdminNochannel.Commands valueOf(String string) {
        return Enum.valueOf(AdminNochannel.Commands.class, string);
    }

    private static /* synthetic */ AdminNochannel.Commands[] a() {
        return new AdminNochannel.Commands[]{admin_nochannel, admin_nc};
    }

    static {
        a = AdminNochannel.Commands.a();
    }
}
