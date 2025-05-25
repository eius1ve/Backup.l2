/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExRaidBossSpawnInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestRaidBossSpawnInfo
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
        if (!Config.ALT_SEND_BOSS_SPAWN_INFO) {
            return;
        }
        this.sendPacket((L2GameServerPacket)new ExRaidBossSpawnInfo());
    }
}
