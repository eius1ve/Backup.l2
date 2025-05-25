/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Manor;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.SeedProduction;

public class ExShowSeedSetting
extends L2GameServerPacket {
    private int hp;
    private int gT;
    private long[] h;

    public ExShowSeedSetting(int n) {
        this.hp = n;
        Castle castle = ResidenceHolder.getInstance().getResidence(Castle.class, this.hp);
        List<Manor.SeedData> list = Manor.getInstance().getSeedsForCastle(this.hp);
        this.gT = list.size();
        this.h = new long[this.gT * 12];
        int n2 = 0;
        for (Manor.SeedData seedData : list) {
            this.h[n2 * 12 + 0] = seedData.getId();
            this.h[n2 * 12 + 1] = seedData.getLevel();
            this.h[n2 * 12 + 2] = seedData.getReward(1);
            this.h[n2 * 12 + 3] = seedData.getReward(2);
            this.h[n2 * 12 + 4] = seedData.getSeedLimit();
            this.h[n2 * 12 + 5] = Manor.getInstance().getSeedBuyPrice(seedData.getId());
            long l = Manor.getInstance().getSeedBasicPrice(seedData.getId());
            this.h[n2 * 12 + 6] = l * 60L / 100L;
            this.h[n2 * 12 + 7] = l * 10L;
            SeedProduction seedProduction = castle.getSeed(seedData.getId(), 0);
            if (seedProduction != null) {
                this.h[n2 * 12 + 8] = seedProduction.getStartProduce();
                this.h[n2 * 12 + 9] = seedProduction.getPrice();
            } else {
                this.h[n2 * 12 + 8] = 0L;
                this.h[n2 * 12 + 9] = 0L;
            }
            seedProduction = castle.getSeed(seedData.getId(), 1);
            if (seedProduction != null) {
                this.h[n2 * 12 + 10] = seedProduction.getStartProduce();
                this.h[n2 * 12 + 11] = seedProduction.getPrice();
            } else {
                this.h[n2 * 12 + 10] = 0L;
                this.h[n2 * 12 + 11] = 0L;
            }
            ++n2;
        }
    }

    @Override
    public void writeImpl() {
        this.writeEx(38);
        this.writeD(this.hp);
        this.writeD(this.gT);
        for (int i = 0; i < this.gT; ++i) {
            this.writeD((int)this.h[i * 12 + 0]);
            this.writeD((int)this.h[i * 12 + 1]);
            this.writeC(1);
            this.writeD((int)this.h[i * 12 + 2]);
            this.writeC(1);
            this.writeD((int)this.h[i * 12 + 3]);
            this.writeD((int)this.h[i * 12 + 4]);
            this.writeD((int)this.h[i * 12 + 5]);
            this.writeD((int)this.h[i * 12 + 6]);
            this.writeD((int)this.h[i * 12 + 7]);
            this.writeQ(this.h[i * 12 + 8]);
            this.writeQ(this.h[i * 12 + 9]);
            this.writeQ(this.h[i * 12 + 10]);
            this.writeQ(this.h[i * 12 + 11]);
        }
    }
}
