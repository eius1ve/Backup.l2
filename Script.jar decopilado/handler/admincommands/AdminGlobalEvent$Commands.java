/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

static final class AdminGlobalEvent.Commands
extends Enum<AdminGlobalEvent.Commands> {
    public static final /* enum */ AdminGlobalEvent.Commands admin_list_events = new AdminGlobalEvent.Commands();
    private static final /* synthetic */ AdminGlobalEvent.Commands[] a;

    public static AdminGlobalEvent.Commands[] values() {
        return (AdminGlobalEvent.Commands[])a.clone();
    }

    public static AdminGlobalEvent.Commands valueOf(String string) {
        return Enum.valueOf(AdminGlobalEvent.Commands.class, string);
    }

    private static /* synthetic */ AdminGlobalEvent.Commands[] a() {
        return new AdminGlobalEvent.Commands[]{admin_list_events};
    }

    static {
        a = AdminGlobalEvent.Commands.a();
    }
}
