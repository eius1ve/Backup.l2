/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

public static final class AdminBossStatus.BossStatusInfo.BossStatus
extends Enum<AdminBossStatus.BossStatusInfo.BossStatus> {
    public static final /* enum */ AdminBossStatus.BossStatusInfo.BossStatus ALIVE = new AdminBossStatus.BossStatusInfo.BossStatus();
    public static final /* enum */ AdminBossStatus.BossStatusInfo.BossStatus DEAD = new AdminBossStatus.BossStatusInfo.BossStatus();
    public static final /* enum */ AdminBossStatus.BossStatusInfo.BossStatus READY = new AdminBossStatus.BossStatusInfo.BossStatus();
    public static final /* enum */ AdminBossStatus.BossStatusInfo.BossStatus RESPAWN = new AdminBossStatus.BossStatusInfo.BossStatus();
    private static final /* synthetic */ AdminBossStatus.BossStatusInfo.BossStatus[] a;

    public static AdminBossStatus.BossStatusInfo.BossStatus[] values() {
        return (AdminBossStatus.BossStatusInfo.BossStatus[])a.clone();
    }

    public static AdminBossStatus.BossStatusInfo.BossStatus valueOf(String string) {
        return Enum.valueOf(AdminBossStatus.BossStatusInfo.BossStatus.class, string);
    }

    private static /* synthetic */ AdminBossStatus.BossStatusInfo.BossStatus[] a() {
        return new AdminBossStatus.BossStatusInfo.BossStatus[]{ALIVE, DEAD, READY, RESPAWN};
    }

    static {
        a = AdminBossStatus.BossStatusInfo.BossStatus.a();
    }
}
