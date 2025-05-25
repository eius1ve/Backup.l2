/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeShopSellList
extends L2GameServerPacket {
    private int objId;
    private int curMp;
    private int maxMp;
    private long de;
    private List<ManufactureItem> cY;

    public RecipeShopSellList(Player player, Player player2) {
        this.objId = player2.getObjectId();
        this.curMp = (int)player2.getCurrentMp();
        this.maxMp = player2.getMaxMp();
        this.de = player.getAdena();
        this.cY = player2.getCreateList();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(223);
        this.writeD(this.objId);
        this.writeD(this.curMp);
        this.writeD(this.maxMp);
        this.writeQ(this.de);
        this.writeD(this.cY.size());
        for (ManufactureItem manufactureItem : this.cY) {
            this.writeD(manufactureItem.getRecipeId());
            this.writeD(0);
            this.writeQ(manufactureItem.getCost());
            this.writeQ(0L);
            this.writeQ(0L);
            this.writeC(0);
        }
    }
}
