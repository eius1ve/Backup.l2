/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminRepairChar.Commands
extends Enum<AdminRepairChar.Commands> {
    public static final /* enum */ AdminRepairChar.Commands admin_restore = new AdminRepairChar.Commands();
    public static final /* enum */ AdminRepairChar.Commands admin_repair = new AdminRepairChar.Commands();
    private static final /* synthetic */ AdminRepairChar.Commands[] a;

    public static AdminRepairChar.Commands[] values() {
        return (AdminRepairChar.Commands[])a.clone();
    }

    public static AdminRepairChar.Commands valueOf(String string) {
        return Enum.valueOf(AdminRepairChar.Commands.class, string);
    }

    private static /* synthetic */ AdminRepairChar.Commands[] a() {
        return new AdminRepairChar.Commands[]{admin_restore, admin_repair};
    }

    static {
        a = AdminRepairChar.Commands.a();
    }
}
