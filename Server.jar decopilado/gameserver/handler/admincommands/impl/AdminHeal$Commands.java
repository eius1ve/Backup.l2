/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminHeal.Commands
extends Enum<AdminHeal.Commands> {
    public static final /* enum */ AdminHeal.Commands admin_heal = new AdminHeal.Commands();
    private static final /* synthetic */ AdminHeal.Commands[] a;

    public static AdminHeal.Commands[] values() {
        return (AdminHeal.Commands[])a.clone();
    }

    public static AdminHeal.Commands valueOf(String string) {
        return Enum.valueOf(AdminHeal.Commands.class, string);
    }

    private static /* synthetic */ AdminHeal.Commands[] a() {
        return new AdminHeal.Commands[]{admin_heal};
    }

    static {
        a = AdminHeal.Commands.a();
    }
}
