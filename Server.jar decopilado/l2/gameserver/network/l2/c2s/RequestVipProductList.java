/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.ReceiveVipProductList;

public class RequestVipProductList
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (!Config.ENABLE_PRIME_SHOP) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        this.sendPacket((L2GameServerPacket)new ReceiveVipProductList(player));
    }
}
