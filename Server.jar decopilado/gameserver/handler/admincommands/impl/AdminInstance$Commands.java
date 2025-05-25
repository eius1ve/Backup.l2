/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminInstance.Commands
extends Enum<AdminInstance.Commands> {
    public static final /* enum */ AdminInstance.Commands admin_instance = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_instance_id = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_collapse = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_reset_reuse = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_reset_reuse_all = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_set_reuse = new AdminInstance.Commands();
    public static final /* enum */ AdminInstance.Commands admin_addtiatkill = new AdminInstance.Commands();
    private static final /* synthetic */ AdminInstance.Commands[] a;

    public static AdminInstance.Commands[] values() {
        return (AdminInstance.Commands[])a.clone();
    }

    public static AdminInstance.Commands valueOf(String string) {
        return Enum.valueOf(AdminInstance.Commands.class, string);
    }

    private static /* synthetic */ AdminInstance.Commands[] a() {
        return new AdminInstance.Commands[]{admin_instance, admin_instance_id, admin_collapse, admin_reset_reuse, admin_reset_reuse_all, admin_set_reuse, admin_addtiatkill};
    }

    static {
        a = AdminInstance.Commands.a();
    }
}
