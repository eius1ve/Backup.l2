/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.CropProcure;

public class SellListProcure
extends L2GameServerPacket {
    private long da;
    private Map<ItemInstance, Long> bz = new HashMap<ItemInstance, Long>();
    private List<CropProcure> bP = new ArrayList<CropProcure>();
    private int Ba;

    public SellListProcure(Player player, int n) {
        this.da = player.getAdena();
        this.Ba = n;
        this.bP = ResidenceHolder.getInstance().getResidence(Castle.class, this.Ba).getCropProcure(0);
        for (CropProcure cropProcure : this.bP) {
            ItemInstance itemInstance = player.getInventory().getItemByItemId(cropProcure.getId());
            if (itemInstance == null || cropProcure.getAmount() <= 0L) continue;
            this.bz.put(itemInstance, cropProcure.getAmount());
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(239);
        this.writeQ(this.da);
        this.writeD(0);
        this.writeH(this.bz.size());
        for (ItemInstance itemInstance : this.bz.keySet()) {
            this.writeH(0);
            this.writeD(itemInstance.getObjectId());
            this.writeD(itemInstance.getItemId());
            this.writeQ(this.bz.get(itemInstance));
            this.writeH(itemInstance.getTemplate().getType2ForPackets());
            this.writeH(0);
            this.writeQ(0L);
        }
    }
}
