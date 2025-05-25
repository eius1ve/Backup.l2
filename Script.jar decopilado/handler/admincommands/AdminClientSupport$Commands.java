/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

public static final class AdminClientSupport.Commands
extends Enum<AdminClientSupport.Commands> {
    public static final /* enum */ AdminClientSupport.Commands admin_setskill = new AdminClientSupport.Commands();
    public static final /* enum */ AdminClientSupport.Commands admin_summon = new AdminClientSupport.Commands();
    private static final /* synthetic */ AdminClientSupport.Commands[] a;

    public static AdminClientSupport.Commands[] values() {
        return (AdminClientSupport.Commands[])a.clone();
    }

    public static AdminClientSupport.Commands valueOf(String string) {
        return Enum.valueOf(AdminClientSupport.Commands.class, string);
    }

    private static /* synthetic */ AdminClientSupport.Commands[] a() {
        return new AdminClientSupport.Commands[]{admin_setskill, admin_summon};
    }

    static {
        a = AdminClientSupport.Commands.a();
    }
}
