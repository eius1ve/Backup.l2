/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminHelpPage.Commands
extends Enum<AdminHelpPage.Commands> {
    public static final /* enum */ AdminHelpPage.Commands admin_showhtml = new AdminHelpPage.Commands();
    private static final /* synthetic */ AdminHelpPage.Commands[] a;

    public static AdminHelpPage.Commands[] values() {
        return (AdminHelpPage.Commands[])a.clone();
    }

    public static AdminHelpPage.Commands valueOf(String string) {
        return Enum.valueOf(AdminHelpPage.Commands.class, string);
    }

    private static /* synthetic */ AdminHelpPage.Commands[] a() {
        return new AdminHelpPage.Commands[]{admin_showhtml};
    }

    static {
        a = AdminHelpPage.Commands.a();
    }
}
