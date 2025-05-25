/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminLevel.Commands
extends Enum<AdminLevel.Commands> {
    public static final /* enum */ AdminLevel.Commands admin_add_level = new AdminLevel.Commands();
    public static final /* enum */ AdminLevel.Commands admin_addLevel = new AdminLevel.Commands();
    public static final /* enum */ AdminLevel.Commands admin_set_level = new AdminLevel.Commands();
    public static final /* enum */ AdminLevel.Commands admin_setLevel = new AdminLevel.Commands();
    private static final /* synthetic */ AdminLevel.Commands[] a;

    public static AdminLevel.Commands[] values() {
        return (AdminLevel.Commands[])a.clone();
    }

    public static AdminLevel.Commands valueOf(String string) {
        return Enum.valueOf(AdminLevel.Commands.class, string);
    }

    private static /* synthetic */ AdminLevel.Commands[] a() {
        return new AdminLevel.Commands[]{admin_add_level, admin_addLevel, admin_set_level, admin_setLevel};
    }

    static {
        a = AdminLevel.Commands.a();
    }
}
