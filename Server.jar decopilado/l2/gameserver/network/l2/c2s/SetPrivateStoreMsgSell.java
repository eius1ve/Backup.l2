/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgSell;
import l2.gameserver.utils.Log;

public class SetPrivateStoreMsgSell
extends L2GameClientPacket {
    private String eG;
    private static final int sm = 29;

    @Override
    protected void readImpl() {
        this.eG = this.readS(29);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getSellList() == null) {
            return;
        }
        if (this.eG != null && this.eG.length() > 29) {
            Log.add("Player " + player.getName() + " tried to overflow private store sell message", "illegal-actions");
            return;
        }
        player.setSellStoreName(this.eG);
        player.broadcastPacket(new PrivateStoreMsgSell(player));
    }
}
