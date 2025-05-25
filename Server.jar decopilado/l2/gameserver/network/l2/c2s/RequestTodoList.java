/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExOneDayReceiveRewardList;

public class RequestTodoList
extends L2GameClientPacket {
    private int se;

    @Override
    protected void readImpl() throws Exception {
        this.se = this.readC();
    }

    @Override
    protected void runImpl() throws Exception {
        if (!Config.EX_ONE_DAY_REWARD) {
            return;
        }
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        switch (this.se) {
            case 9: {
                player.sendPacket((IStaticPacket)new ExOneDayReceiveRewardList(player));
            }
        }
    }
}
