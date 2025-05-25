/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.RaidBossSpawnManager
 *  l2.gameserver.instancemanager.RaidBossSpawnManager$Status
 *  l2.gameserver.model.Spawner
 */
package services;

import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.Spawner;
import services.BossStatusService;

public static final class BossStatusService.RaidBossStatusInfo
extends BossStatusService.BossStatusInfo {
    public BossStatusService.RaidBossStatusInfo(int n) {
        super(n);
    }

    @Override
    public BossStatusService.BossStatusInfo.BossStatus getStatus() {
        RaidBossSpawnManager.Status status = RaidBossSpawnManager.getInstance().getRaidBossStatusId(this.getNpcId());
        if (status == RaidBossSpawnManager.Status.DEAD) {
            long l = this.getRespawnDate();
            if (l * 1000L > System.currentTimeMillis()) {
                return BossStatusService.BossStatusInfo.BossStatus.RESPAWN;
            }
            return BossStatusService.BossStatusInfo.BossStatus.DEAD;
        }
        if (status == RaidBossSpawnManager.Status.UNDEFINED) {
            return BossStatusService.BossStatusInfo.BossStatus.DEAD;
        }
        return BossStatusService.BossStatusInfo.BossStatus.ALIVE;
    }

    @Override
    public long getRespawnDate() {
        Spawner spawner = (Spawner)RaidBossSpawnManager.getInstance().getSpawnTable().get(this.getNpcId());
        if (spawner == null) {
            return -1L;
        }
        return spawner.getRespawnTime();
    }
}
