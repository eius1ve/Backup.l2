/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminEvents.Commands
extends Enum<AdminEvents.Commands> {
    public static final /* enum */ AdminEvents.Commands admin_events = new AdminEvents.Commands();
    private static final /* synthetic */ AdminEvents.Commands[] a;

    public static AdminEvents.Commands[] values() {
        return (AdminEvents.Commands[])a.clone();
    }

    public static AdminEvents.Commands valueOf(String string) {
        return Enum.valueOf(AdminEvents.Commands.class, string);
    }

    private static /* synthetic */ AdminEvents.Commands[] a() {
        return new AdminEvents.Commands[]{admin_events};
    }

    static {
        a = AdminEvents.Commands.a();
    }
}
