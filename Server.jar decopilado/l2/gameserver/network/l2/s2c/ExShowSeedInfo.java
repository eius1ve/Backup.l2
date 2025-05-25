/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.Manor;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.SeedProduction;

public class ExShowSeedInfo
extends L2GameServerPacket {
    private List<SeedProduction> cC;
    private int hp;

    public ExShowSeedInfo(int n, List<SeedProduction> list) {
        this.hp = n;
        this.cC = list;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(35);
        this.writeC(0);
        this.writeD(this.hp);
        this.writeD(0);
        this.writeD(this.cC.size());
        for (SeedProduction seedProduction : this.cC) {
            this.writeD(seedProduction.getId());
            this.writeQ(seedProduction.getCanProduce());
            this.writeQ(seedProduction.getStartProduce());
            this.writeQ(seedProduction.getPrice());
            this.writeD(Manor.getInstance().getSeedLevel(seedProduction.getId()));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItemBySeed(seedProduction.getId(), 1));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItemBySeed(seedProduction.getId(), 2));
        }
    }
}
