/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExVitalityEffectInfo
extends L2GameServerPacket {
    private final int xY;
    private final double ab;

    public ExVitalityEffectInfo(Player player) {
        this.ab = player.getVitality();
        this.xY = (int)((double)(player.getVitalityLevel(false) * 100 / 2) * Config.ALT_VITALITY_RATE) - 100;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(280);
        this.writeD((int)this.ab);
        this.writeD(this.xY);
        this.writeH(0);
        this.writeH(999);
        this.writeH(999);
    }
}
