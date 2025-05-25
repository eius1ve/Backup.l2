/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

private static final class AdminResidence.Commands
extends Enum<AdminResidence.Commands> {
    public static final /* enum */ AdminResidence.Commands admin_residence_list = new AdminResidence.Commands();
    public static final /* enum */ AdminResidence.Commands admin_residence = new AdminResidence.Commands();
    public static final /* enum */ AdminResidence.Commands admin_set_owner = new AdminResidence.Commands();
    public static final /* enum */ AdminResidence.Commands admin_set_siege_time = new AdminResidence.Commands();
    public static final /* enum */ AdminResidence.Commands admin_quick_siege_start = new AdminResidence.Commands();
    public static final /* enum */ AdminResidence.Commands admin_quick_siege_stop = new AdminResidence.Commands();
    private static final /* synthetic */ AdminResidence.Commands[] a;

    public static AdminResidence.Commands[] values() {
        return (AdminResidence.Commands[])a.clone();
    }

    public static AdminResidence.Commands valueOf(String string) {
        return Enum.valueOf(AdminResidence.Commands.class, string);
    }

    private static /* synthetic */ AdminResidence.Commands[] a() {
        return new AdminResidence.Commands[]{admin_residence_list, admin_residence, admin_set_owner, admin_set_siege_time, admin_quick_siege_start, admin_quick_siege_stop};
    }

    static {
        a = AdminResidence.Commands.a();
    }
}
