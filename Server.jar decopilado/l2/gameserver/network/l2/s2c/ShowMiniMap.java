/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShowMiniMap
extends L2GameServerPacket {
    private int Bz;
    private int or;

    public ShowMiniMap(Player player, int n) {
        this.Bz = n;
        this.or = SevenSigns.getInstance().getCurrentPeriod();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(163);
        this.writeD(this.Bz);
        this.writeC(this.or);
    }
}
