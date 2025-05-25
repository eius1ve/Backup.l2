/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.Manor;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowManorDefaultInfo
extends L2GameServerPacket {
    private List<Integer> cy = Manor.getInstance().getAllCrops();

    @Override
    protected void writeImpl() {
        this.writeEx(37);
        this.writeC(0);
        this.writeD(this.cy.size());
        for (int n : this.cy) {
            this.writeD(n);
            this.writeD(Manor.getInstance().getSeedLevelByCrop(n));
            this.writeD((int)Manor.getInstance().getSeedBasicPriceByCrop(n));
            this.writeD((int)Manor.getInstance().getCropBasicPrice(n));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(n, 1));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(n, 2));
        }
    }
}
