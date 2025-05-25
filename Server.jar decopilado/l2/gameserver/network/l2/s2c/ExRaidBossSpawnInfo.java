/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.network.l2.s2c;

import gnu.trove.TIntHashSet;
import java.util.Map;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.Spawner;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExRaidBossSpawnInfo
extends L2GameServerPacket {
    private int[] bc;

    public ExRaidBossSpawnInfo() {
        RaidBossSpawnManager raidBossSpawnManager = RaidBossSpawnManager.getInstance();
        Map<Integer, Spawner> map = raidBossSpawnManager.getSpawnTable();
        TIntHashSet tIntHashSet = new TIntHashSet();
        for (Integer n : map.keySet()) {
            RaidBossSpawnManager.Status status = raidBossSpawnManager.getRaidBossStatusId(n);
            if (status != RaidBossSpawnManager.Status.ALIVE) continue;
            tIntHashSet.add(n.intValue());
        }
        this.bc = tIntHashSet.toArray();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(441);
        this.writeDD(this.bc, true);
    }
}
