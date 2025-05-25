/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_PremiumState
extends L2GameServerPacket {
    private int fW;
    private int _state;

    public ExBR_PremiumState(Player player, boolean bl) {
        this.fW = player.getObjectId();
        this._state = bl ? 1 : 0;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(218);
        this.writeD(this.fW);
        this.writeC(this._state);
    }
}
