/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.Henna;

public class HennaUnequipList
extends L2GameServerPacket {
    private int zd;
    private long dg;
    private List<Henna> cJ = new ArrayList<Henna>(3);

    public HennaUnequipList(Player player) {
        this.dg = player.getAdena();
        this.zd = player.getHennaEmptySlots();
        for (int i = 1; i <= 3; ++i) {
            if (player.getHenna(i) == null) continue;
            this.cJ.add(player.getHenna(i));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(230);
        this.writeQ(this.dg);
        this.writeD(this.zd);
        this.writeD(this.cJ.size());
        for (Henna henna : this.cJ) {
            this.writeD(henna.getSymbolId());
            this.writeD(henna.getDyeId());
            this.writeQ(henna.getDrawCount());
            this.writeQ(henna.getPrice());
            this.writeD(1);
        }
    }
}
