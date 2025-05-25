/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class TradeStart
extends AbstractItemListPacket {
    private final boolean fp;
    private final List<ItemInfo> de = new ArrayList<ItemInfo>();
    private final int Cp;
    private final int Cq;
    private int ue;

    public TradeStart(boolean bl, Player player, Player player2) {
        ItemInstance[] itemInstanceArray;
        this.fp = bl;
        this.Cp = player2.getObjectId();
        this.Cq = player2.getLevel();
        for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
            if (!itemInstance.canBeTraded(player)) continue;
            this.de.add(new ItemInfo(itemInstance));
        }
        if (player.getFriendList().getList().containsKey(player2.getObjectId())) {
            this.ue |= 1;
        }
        if (player.getClanId() > 0 && player.getClanId() == player2.getClanId()) {
            this.ue |= 2;
        }
        if (player.getAllyId() > 0 && player.getAllyId() == player2.getAllyId()) {
            this.ue = 8;
        }
        if (player2.isGM()) {
            this.ue |= 0x10;
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(20);
        this.writeC(this.fp ? 1 : 2);
        if (this.fp) {
            this.writeD(this.Cp);
            this.writeC(this.ue);
            this.writeC(this.Cq);
            this.writeC(0);
            this.writeH(0);
            this.writeC(0);
        } else {
            this.writeD(this.de.size());
            this.writeH(this.de.size());
            this.writeC(0);
            this.writeC(0);
            for (ItemInfo itemInfo : this.de) {
                this.writeItemInfo(itemInfo);
            }
        }
    }
}
