/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeShopMsg
extends L2GameServerPacket {
    private int fW;
    private String fI;

    public RecipeShopMsg(Player player) {
        this.fW = player.getObjectId();
        this.fI = player.getManufactureName();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(225);
        this.writeD(this.fW);
        this.writeS(this.fI);
    }
}
