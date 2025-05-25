/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExGetCrystalizingEstimation
extends L2GameServerPacket {
    private final List<ItemInfo> ci;

    public ExGetCrystalizingEstimation(ItemInstance itemInstance) {
        int n = itemInstance.getTemplate().getCrystalCount();
        int n2 = itemInstance.getTemplate().getCrystalItemId();
        this.ci = new ArrayList<ItemInfo>();
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.itemId = n2;
        itemInfo.count = n;
        itemInfo.chance = 100.0f;
        this.ci.add(itemInfo);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(225);
        this.writeD(this.ci.size());
        for (ItemInfo itemInfo : this.ci) {
            this.writeD(itemInfo.itemId);
            this.writeQ(itemInfo.count);
            this.writeF(itemInfo.chance);
        }
    }

    private static class ItemInfo {
        int itemId;
        long count;
        float chance;

        private ItemInfo() {
        }
    }
}
