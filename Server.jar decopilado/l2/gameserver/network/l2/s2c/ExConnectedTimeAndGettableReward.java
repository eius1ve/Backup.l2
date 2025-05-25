/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExConnectedTimeAndGettableReward
extends L2GameServerPacket {
    private final int uQ;

    public ExConnectedTimeAndGettableReward(Player player) {
        this.uQ = OneDayRewardHolder.getInstance().getAvaliableOneDayReward(player);
    }

    public ExConnectedTimeAndGettableReward(int n) {
        this.uQ = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(425);
        this.writeD(0);
        this.writeD(this.uQ);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }
}
