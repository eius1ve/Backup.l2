/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminPledge.Commands
extends Enum<AdminPledge.Commands> {
    public static final /* enum */ AdminPledge.Commands admin_pledge = new AdminPledge.Commands();
    private static final /* synthetic */ AdminPledge.Commands[] a;

    public static AdminPledge.Commands[] values() {
        return (AdminPledge.Commands[])a.clone();
    }

    public static AdminPledge.Commands valueOf(String string) {
        return Enum.valueOf(AdminPledge.Commands.class, string);
    }

    private static /* synthetic */ AdminPledge.Commands[] a() {
        return new AdminPledge.Commands[]{admin_pledge};
    }

    static {
        a = AdminPledge.Commands.a();
    }
}
