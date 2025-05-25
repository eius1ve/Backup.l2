/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminMove.Commands
extends Enum<AdminMove.Commands> {
    public static final /* enum */ AdminMove.Commands admin_move_debug = new AdminMove.Commands();
    private static final /* synthetic */ AdminMove.Commands[] a;

    public static AdminMove.Commands[] values() {
        return (AdminMove.Commands[])a.clone();
    }

    public static AdminMove.Commands valueOf(String string) {
        return Enum.valueOf(AdminMove.Commands.class, string);
    }

    private static /* synthetic */ AdminMove.Commands[] a() {
        return new AdminMove.Commands[]{admin_move_debug};
    }

    static {
        a = AdminMove.Commands.a();
    }
}
