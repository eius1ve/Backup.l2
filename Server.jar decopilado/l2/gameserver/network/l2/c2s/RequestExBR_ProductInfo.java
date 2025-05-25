/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBR_ProductInfo;

public class RequestExBR_ProductInfo
extends L2GameClientPacket {
    private int iS;

    @Override
    protected void readImpl() {
        this.iS = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.sendPacket((IStaticPacket)new ExBR_ProductInfo(this.iS));
    }
}
