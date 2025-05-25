/*
 * Decompiled with CFR 0.152.
 */
package services;

import bosses.EpicBossState;
import services.BossStatusService;

public static final class BossStatusService.EpicBossStatusInfo
extends BossStatusService.BossStatusInfo {
    private final EpicBossState b;

    public BossStatusService.EpicBossStatusInfo(int n, EpicBossState epicBossState) {
        super(n);
        this.b = epicBossState;
    }

    @Override
    public BossStatusService.BossStatusInfo.BossStatus getStatus() {
        switch (this.b.getState()) {
            case ALIVE: {
                return BossStatusService.BossStatusInfo.BossStatus.ALIVE;
            }
            case NOTSPAWN: {
                return BossStatusService.BossStatusInfo.BossStatus.READY;
            }
            case DEAD: {
                return BossStatusService.BossStatusInfo.BossStatus.DEAD;
            }
        }
        return BossStatusService.BossStatusInfo.BossStatus.RESPAWN;
    }

    @Override
    public long getRespawnDate() {
        return this.b.getRespawnDate() / 1000L;
    }
}
