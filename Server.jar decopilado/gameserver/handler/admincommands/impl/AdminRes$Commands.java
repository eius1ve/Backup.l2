/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminRes.Commands
extends Enum<AdminRes.Commands> {
    public static final /* enum */ AdminRes.Commands admin_res = new AdminRes.Commands();
    private static final /* synthetic */ AdminRes.Commands[] a;

    public static AdminRes.Commands[] values() {
        return (AdminRes.Commands[])a.clone();
    }

    public static AdminRes.Commands valueOf(String string) {
        return Enum.valueOf(AdminRes.Commands.class, string);
    }

    private static /* synthetic */ AdminRes.Commands[] a() {
        return new AdminRes.Commands[]{admin_res};
    }

    static {
        a = AdminRes.Commands.a();
    }
}
