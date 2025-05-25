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
import services.RaidBossStatusService;

public static final class RaidBossStatusService.RaidBossStatusInfo
extends RaidBossStatusService.BossStatusInfo {
    public RaidBossStatusService.RaidBossStatusInfo(int n) {
        super(n);
    }

    @Override
    public RaidBossStatusService.BossStatusInfo.BossStatus getStatus() {
        RaidBossSpawnManager.Status status = RaidBossSpawnManager.getInstance().getRaidBossStatusId(this.getNpcId());
        if (status == RaidBossSpawnManager.Status.DEAD) {
            long l = this.getRespawnDate();
            if (l * 1000L > System.currentTimeMillis()) {
                return RaidBossStatusService.BossStatusInfo.BossStatus.RESPAWN;
            }
            return RaidBossStatusService.BossStatusInfo.BossStatus.DEAD;
        }
        if (status == RaidBossSpawnManager.Status.UNDEFINED) {
            return RaidBossStatusService.BossStatusInfo.BossStatus.DEAD;
        }
        return RaidBossStatusService.BossStatusInfo.BossStatus.ALIVE;
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
