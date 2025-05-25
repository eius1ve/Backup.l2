/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBRNewIconCashBtnWnd
extends L2GameServerPacket {
    private final boolean ep;

    public ExBRNewIconCashBtnWnd(Player player) {
        this.ep = player.canReceiveGift(player);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(314);
        this.writeH(this.ep ? 1 : 0);
    }
}
