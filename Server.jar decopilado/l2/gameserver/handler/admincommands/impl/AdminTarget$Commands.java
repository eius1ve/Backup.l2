/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminTarget.Commands
extends Enum<AdminTarget.Commands> {
    public static final /* enum */ AdminTarget.Commands admin_target = new AdminTarget.Commands();
    private static final /* synthetic */ AdminTarget.Commands[] a;

    public static AdminTarget.Commands[] values() {
        return (AdminTarget.Commands[])a.clone();
    }

    public static AdminTarget.Commands valueOf(String string) {
        return Enum.valueOf(AdminTarget.Commands.class, string);
    }

    private static /* synthetic */ AdminTarget.Commands[] a() {
        return new AdminTarget.Commands[]{admin_target};
    }

    static {
        a = AdminTarget.Commands.a();
    }
}
