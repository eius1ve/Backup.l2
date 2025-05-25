/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExStorageMaxCount
extends L2GameServerPacket {
    private int ks;
    private int kt;
    private int ku;
    private int xB;
    private int xD;
    private int kw;
    private int kx;
    private int xE;
    private int xF;

    public ExStorageMaxCount(Player player) {
        this.ks = player.getInventoryLimit();
        this.kt = player.getWarehouseLimit();
        this.ku = Config.WAREHOUSE_SLOTS_CLAN;
        this.xD = this.xB = player.getTradeLimit();
        this.kw = player.getDwarvenRecipeLimit();
        this.kx = player.getCommonRecipeLimit();
        this.xE = player.getBeltInventoryIncrease();
        this.xF = Config.QUEST_INVENTORY_MAXIMUM;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(47);
        this.writeD(this.ks);
        this.writeD(this.kt);
        this.writeD(this.ku);
        this.writeD(this.xB);
        this.writeD(this.xD);
        this.writeD(this.kw);
        this.writeD(this.kx);
        this.writeD(this.xE);
        this.writeD(this.xF);
        this.writeD(40);
        this.writeD(40);
        this.writeD(100);
    }
}
