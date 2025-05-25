/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.DeleteObject;

class AdminMonsterRace.RunEnd
extends RunnableImpl {
    private Player b;

    public AdminMonsterRace.RunEnd(Player player) {
        this.b = player;
    }

    @Override
    public void runImpl() throws Exception {
        for (int i = 0; i < 8; ++i) {
            NpcInstance npcInstance = MonsterRace.getInstance().getMonsters()[i];
            this.b.broadcastPacket(new DeleteObject(npcInstance));
        }
        state = -1;
    }
}
