/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.RaidBossSpawnManager
 *  l2.gameserver.instancemanager.RaidBossSpawnManager$Status
 *  l2.gameserver.model.Spawner
 */
package handler.admincommands;

import handler.admincommands.AdminBossStatus;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.Spawner;

public static final class AdminBossStatus.RaidBossStatusInfo
extends AdminBossStatus.BossStatusInfo {
    public AdminBossStatus.RaidBossStatusInfo(int n) {
        super(n);
    }

    @Override
    public AdminBossStatus.BossStatusInfo.BossStatus getStatus() {
        RaidBossSpawnManager.Status status = RaidBossSpawnManager.getInstance().getRaidBossStatusId(this.getNpcId());
        if (status == RaidBossSpawnManager.Status.DEAD) {
            long l = this.getRespawnDate();
            if (l * 1000L > System.currentTimeMillis()) {
                return AdminBossStatus.BossStatusInfo.BossStatus.RESPAWN;
            }
            return AdminBossStatus.BossStatusInfo.BossStatus.DEAD;
        }
        if (status == RaidBossSpawnManager.Status.UNDEFINED) {
            return AdminBossStatus.BossStatusInfo.BossStatus.DEAD;
        }
        return AdminBossStatus.BossStatusInfo.BossStatus.ALIVE;
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
