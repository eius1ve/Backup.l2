/*
 * Decompiled with CFR 0.152.
 */
package services;

public static final class BossStatusService.BossStatusInfo.BossStatus
extends Enum<BossStatusService.BossStatusInfo.BossStatus> {
    public static final /* enum */ BossStatusService.BossStatusInfo.BossStatus ALIVE = new BossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ BossStatusService.BossStatusInfo.BossStatus DEAD = new BossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ BossStatusService.BossStatusInfo.BossStatus READY = new BossStatusService.BossStatusInfo.BossStatus();
    public static final /* enum */ BossStatusService.BossStatusInfo.BossStatus RESPAWN = new BossStatusService.BossStatusInfo.BossStatus();
    private static final /* synthetic */ BossStatusService.BossStatusInfo.BossStatus[] a;

    public static BossStatusService.BossStatusInfo.BossStatus[] values() {
        return (BossStatusService.BossStatusInfo.BossStatus[])a.clone();
    }

    public static BossStatusService.BossStatusInfo.BossStatus valueOf(String string) {
        return Enum.valueOf(BossStatusService.BossStatusInfo.BossStatus.class, string);
    }

    private static /* synthetic */ BossStatusService.BossStatusInfo.BossStatus[] a() {
        return new BossStatusService.BossStatusInfo.BossStatus[]{ALIVE, DEAD, READY, RESPAWN};
    }

    static {
        a = BossStatusService.BossStatusInfo.BossStatus.a();
    }
}
