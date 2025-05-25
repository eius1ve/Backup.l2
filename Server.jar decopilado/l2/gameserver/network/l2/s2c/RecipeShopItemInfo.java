/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeShopItemInfo
extends L2GameServerPacket {
    private int oD;
    private int AM;
    private int AN;
    private int _maxMp;
    private int AO = -1;
    private long _price;

    public RecipeShopItemInfo(Player player, Player player2, int n, long l, int n2) {
        this.oD = n;
        this.AM = player2.getObjectId();
        this._price = l;
        this.AO = n2;
        this.AN = (int)player2.getCurrentMp();
        this._maxMp = player2.getMaxMp();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(224);
        this.writeD(this.AM);
        this.writeD(this.oD);
        this.writeD(this.AN);
        this.writeD(this._maxMp);
        this.writeD(this.AO);
        this.writeQ(this._price);
    }
}
