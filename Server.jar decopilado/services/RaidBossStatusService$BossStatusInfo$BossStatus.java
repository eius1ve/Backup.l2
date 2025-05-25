/*
 * Decompiled with CFR 0.152.
 */
package services;

public static final class RaidBossStatusService.BossStatusInfo.BossStatus
extends Enum<RaidBossStatusService.BossStatusInfo.BossStatus> {
    public static final /* enum */ RaidBossStatusService.BossStatusInfo.BossStatus ALIVE = new RaidBossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ RaidBossStatusService.BossStatusInfo.BossStatus DEAD = new RaidBossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ RaidBossStatusService.BossStatusInfo.BossStatus READY = new RaidBossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ RaidBossStatusService.BossStatusInfo.BossStatus RESPAWN = new RaidBossStatusService.BossStatusInfo.BossStatus();
    private static final /* synthetic */ RaidBossStatusService.BossStatusInfo.BossStatus[] a;

    public static RaidBossStatusService.BossStatusInfo.BossStatus[] values() {
        return (RaidBossStatusService.BossStatusInfo.BossStatus[])a.clone();
    }

    public static RaidBossStatusService.BossStatusInfo.BossStatus valueOf(String string) {
        return Enum.valueOf(RaidBossStatusService.BossStatusInfo.BossStatus.class, string);
    }

    private static /* synthetic */ RaidBossStatusService.BossStatusInfo.BossStatus[] a() {
        return new RaidBossStatusService.BossStatusInfo.BossStatus[]{ALIVE, DEAD, READY, RESPAWN};
    }

    static {
        a = RaidBossStatusService.BossStatusInfo.BossStatus.a();
    }
}
