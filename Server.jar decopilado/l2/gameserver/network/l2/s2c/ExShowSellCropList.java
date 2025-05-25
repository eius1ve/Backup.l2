/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l2.gameserver.model.Manor;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.CropProcure;

public class ExShowSellCropList
extends L2GameServerPacket {
    private int hp = 1;
    private Map<Integer, ItemInstance> bw;
    private Map<Integer, CropProcure> bv;

    public ExShowSellCropList(Player player, int n, List<CropProcure> list) {
        this.hp = n;
        this.bv = new TreeMap<Integer, CropProcure>();
        this.bw = new TreeMap<Integer, ItemInstance>();
        List<Integer> list2 = Manor.getInstance().getAllCrops();
        Iterator<Object> iterator = list2.iterator();
        while (iterator.hasNext()) {
            int n2 = iterator.next();
            ItemInstance itemInstance = player.getInventory().getItemByItemId(n2);
            if (itemInstance == null) continue;
            this.bw.put(n2, itemInstance);
        }
        for (CropProcure cropProcure : list) {
            if (!this.bw.containsKey(cropProcure.getId()) || cropProcure.getAmount() <= 0L) continue;
            this.bv.put(cropProcure.getId(), cropProcure);
        }
    }

    @Override
    public void writeImpl() {
        this.writeEx(44);
        this.writeD(this.hp);
        this.writeD(this.bw.size());
        for (ItemInstance itemInstance : this.bw.values()) {
            this.writeD(itemInstance.getObjectId());
            this.writeD(itemInstance.getItemId());
            this.writeD(Manor.getInstance().getSeedLevelByCrop(itemInstance.getItemId()));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(itemInstance.getItemId(), 1));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(itemInstance.getItemId(), 2));
            if (this.bv.containsKey(itemInstance.getItemId())) {
                CropProcure cropProcure = this.bv.get(itemInstance.getItemId());
                this.writeD(this.hp);
                this.writeQ(cropProcure.getAmount());
                this.writeQ(cropProcure.getPrice());
                this.writeC(cropProcure.getReward());
            } else {
                this.writeD(-1);
                this.writeQ(0L);
                this.writeQ(0L);
                this.writeC(0);
            }
            this.writeQ(itemInstance.getCount());
        }
    }
}
