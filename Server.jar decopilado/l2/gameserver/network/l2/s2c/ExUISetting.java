/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExUISetting
extends L2GameServerPacket {
    private final byte[] w;

    public ExUISetting(Player player) {
        this.w = player.getKeyBindings();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(113);
        this.writeD(this.w.length);
        this.writeB(this.w);
    }
}
