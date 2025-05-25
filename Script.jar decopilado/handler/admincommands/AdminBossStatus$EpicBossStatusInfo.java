/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

import bosses.EpicBossState;
import handler.admincommands.AdminBossStatus;

public static final class AdminBossStatus.EpicBossStatusInfo
extends AdminBossStatus.BossStatusInfo {
    private final EpicBossState a;

    public AdminBossStatus.EpicBossStatusInfo(int n, EpicBossState epicBossState) {
        super(n);
        this.a = epicBossState;
    }

    @Override
    public AdminBossStatus.BossStatusInfo.BossStatus getStatus() {
        switch (this.a.getState()) {
            case ALIVE: {
                return AdminBossStatus.BossStatusInfo.BossStatus.ALIVE;
            }
            case NOTSPAWN: {
                return AdminBossStatus.BossStatusInfo.BossStatus.READY;
            }
            case DEAD: {
                return AdminBossStatus.BossStatusInfo.BossStatus.DEAD;
            }
        }
        return AdminBossStatus.BossStatusInfo.BossStatus.RESPAWN;
    }

    @Override
    public long getRespawnDate() {
        return this.a.getRespawnDate() / 1000L;
    }
}
