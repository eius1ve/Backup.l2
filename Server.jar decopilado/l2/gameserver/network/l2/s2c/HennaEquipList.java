/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.xml.holder.HennaHolder;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.Henna;

public class HennaEquipList
extends L2GameServerPacket {
    private int zd;
    private long dg;
    private List<Henna> cI = new ArrayList<Henna>();

    public HennaEquipList(Player player) {
        this.dg = player.getAdena();
        this.zd = player.getHennaEmptySlots();
        List<Henna> list = HennaHolder.getInstance().generateList(player);
        for (Henna henna : list) {
            if (player.getInventory().getItemByItemId(henna.getDyeId()) == null) continue;
            this.cI.add(henna);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(238);
        this.writeQ(this.dg);
        this.writeD(this.zd);
        if (this.cI.size() != 0) {
            this.writeD(this.cI.size());
            for (Henna henna : this.cI) {
                this.writeD(henna.getSymbolId());
                this.writeD(henna.getDyeId());
                this.writeQ(henna.getDrawCount());
                this.writeQ(henna.getPrice());
                this.writeD(1);
            }
        } else {
            this.writeD(1);
            this.writeD(0);
            this.writeD(0);
            this.writeQ(0L);
            this.writeQ(0L);
            this.writeD(0);
        }
    }
}
