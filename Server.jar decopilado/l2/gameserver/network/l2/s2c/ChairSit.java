/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ChairSit
extends L2GameServerPacket {
    private int fW;
    private int sQ;

    public ChairSit(Player player, StaticObjectInstance staticObjectInstance) {
        this.fW = player.getObjectId();
        this.sQ = staticObjectInstance.getUId();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(237);
        this.writeD(this.fW);
        this.writeD(this.sQ);
    }
}
