/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

public static final class AdminBossStatus.Commands
extends Enum<AdminBossStatus.Commands> {
    public static final /* enum */ AdminBossStatus.Commands admin_boss_status = new AdminBossStatus.Commands();
    private static final /* synthetic */ AdminBossStatus.Commands[] a;

    public static AdminBossStatus.Commands[] values() {
        return (AdminBossStatus.Commands[])a.clone();
    }

    public static AdminBossStatus.Commands valueOf(String string) {
        return Enum.valueOf(AdminBossStatus.Commands.class, string);
    }

    private static /* synthetic */ AdminBossStatus.Commands[] a() {
        return new AdminBossStatus.Commands[]{admin_boss_status};
    }

    static {
        a = AdminBossStatus.Commands.a();
    }
}
