/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.admincommands.impl.AdminMonsterRace;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.network.l2.s2c.MonRaceInfo;

class AdminMonsterRace.RunRace
extends RunnableImpl {
    private int[][] d;
    private Player b;

    public AdminMonsterRace.RunRace(int[][] nArray, Player player) {
        this.d = nArray;
        this.b = player;
    }

    @Override
    public void runImpl() throws Exception {
        this.b.broadcastPacket(new MonRaceInfo(this.d[2][0], this.d[2][1], MonsterRace.getInstance().getMonsters(), MonsterRace.getInstance().getSpeeds()));
        ThreadPoolManager.getInstance().schedule(new AdminMonsterRace.RunEnd(AdminMonsterRace.this, this.b), 30000L);
    }
}
