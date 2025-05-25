/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Map;
import l2.gameserver.model.Player;
import l2.gameserver.model.PremiumItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExGetPremiumItemList
extends L2GameServerPacket {
    private int fW;
    private Map<Integer, PremiumItem> bt;

    public ExGetPremiumItemList(Player player) {
        this.fW = player.getObjectId();
        this.bt = player.getPremiumItemList();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(134);
        if (!this.bt.isEmpty()) {
            this.writeD(this.bt.size());
            for (Map.Entry<Integer, PremiumItem> entry : this.bt.entrySet()) {
                this.writeD(entry.getKey());
                this.writeD(this.fW);
                this.writeD(entry.getValue().getItemId());
                this.writeQ(entry.getValue().getCount());
                this.writeD(0);
                this.writeS(entry.getValue().getSender());
            }
        }
    }
}
