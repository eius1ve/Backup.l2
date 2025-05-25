/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.Manor;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.manor.CropProcure;

public class ExShowCropInfo
extends L2GameServerPacket {
    private List<CropProcure> cy;
    private int hp;

    public ExShowCropInfo(int n, List<CropProcure> list) {
        this.hp = n;
        this.cy = list;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(36);
        this.writeC(0);
        this.writeD(this.hp);
        this.writeD(0);
        this.writeD(this.cy.size());
        for (CropProcure cropProcure : this.cy) {
            this.writeD(cropProcure.getId());
            this.writeQ(cropProcure.getAmount());
            this.writeQ(cropProcure.getStartAmount());
            this.writeQ(cropProcure.getPrice());
            this.writeC(cropProcure.getReward());
            this.writeD(Manor.getInstance().getSeedLevelByCrop(cropProcure.getId()));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(cropProcure.getId(), 1));
            this.writeC(1);
            this.writeD(Manor.getInstance().getRewardItem(cropProcure.getId(), 2));
        }
    }
}
