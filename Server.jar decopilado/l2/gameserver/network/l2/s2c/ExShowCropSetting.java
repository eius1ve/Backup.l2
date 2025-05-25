/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Manor;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.CropProcure;

public class ExShowCropSetting
extends L2GameServerPacket {
    private int hp;
    private int gT;
    private long[] g;

    public ExShowCropSetting(int n) {
        this.hp = n;
        Castle castle = ResidenceHolder.getInstance().getResidence(Castle.class, this.hp);
        List<Manor.SeedData> list = Manor.getInstance().getCropsForCastle(this.hp);
        this.gT = list.size();
        this.g = new long[this.gT * 14];
        int n2 = 0;
        for (Manor.SeedData seedData : list) {
            this.g[n2 * 14 + 0] = seedData.getCrop();
            this.g[n2 * 14 + 1] = seedData.getLevel();
            this.g[n2 * 14 + 2] = seedData.getReward(1);
            this.g[n2 * 14 + 3] = seedData.getReward(2);
            this.g[n2 * 14 + 4] = seedData.getCropLimit();
            this.g[n2 * 14 + 5] = 0L;
            long l = Manor.getInstance().getCropBasicPrice(seedData.getCrop());
            this.g[n2 * 14 + 6] = l * 60L / 100L;
            this.g[n2 * 14 + 7] = l * 10L;
            CropProcure cropProcure = castle.getCrop(seedData.getCrop(), 0);
            if (cropProcure != null) {
                this.g[n2 * 14 + 8] = cropProcure.getStartAmount();
                this.g[n2 * 14 + 9] = cropProcure.getPrice();
                this.g[n2 * 14 + 10] = cropProcure.getReward();
            } else {
                this.g[n2 * 14 + 8] = 0L;
                this.g[n2 * 14 + 9] = 0L;
                this.g[n2 * 14 + 10] = 0L;
            }
            cropProcure = castle.getCrop(seedData.getCrop(), 1);
            if (cropProcure != null) {
                this.g[n2 * 14 + 11] = cropProcure.getStartAmount();
                this.g[n2 * 14 + 12] = cropProcure.getPrice();
                this.g[n2 * 14 + 13] = cropProcure.getReward();
            } else {
                this.g[n2 * 14 + 11] = 0L;
                this.g[n2 * 14 + 12] = 0L;
                this.g[n2 * 14 + 13] = 0L;
            }
            ++n2;
        }
    }

    @Override
    public void writeImpl() {
        this.writeEx(43);
        this.writeD(this.hp);
        this.writeD(this.gT);
        for (int i = 0; i < this.gT; ++i) {
            this.writeD((int)this.g[i * 14 + 0]);
            this.writeD((int)this.g[i * 14 + 1]);
            this.writeC(1);
            this.writeD((int)this.g[i * 14 + 2]);
            this.writeC(1);
            this.writeD((int)this.g[i * 14 + 3]);
            this.writeD((int)this.g[i * 14 + 4]);
            this.writeD((int)this.g[i * 14 + 5]);
            this.writeD((int)this.g[i * 14 + 6]);
            this.writeD((int)this.g[i * 14 + 7]);
            this.writeQ(this.g[i * 14 + 8]);
            this.writeQ(this.g[i * 14 + 9]);
            this.writeC((int)this.g[i * 14 + 10]);
            this.writeQ(this.g[i * 14 + 11]);
            this.writeQ(this.g[i * 14 + 12]);
            this.writeC((int)this.g[i * 14 + 13]);
        }
    }
}
