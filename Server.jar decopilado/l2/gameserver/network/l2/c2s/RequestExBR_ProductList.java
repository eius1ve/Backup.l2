/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBR_ProductList;

public class RequestExBR_ProductList
extends L2GameClientPacket {
    private int type;

    @Override
    protected void readImpl() {
        this.type = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (!Config.ENABLE_PRIME_SHOP) {
            return;
        }
        if (this.type == 0) {
            player.sendPacket((IStaticPacket)new ExBR_ProductList(player));
        }
    }
}
