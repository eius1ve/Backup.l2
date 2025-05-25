/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SpawnEmitter
extends L2GameServerPacket {
    private int BC;
    private int kg;

    public SpawnEmitter(NpcInstance npcInstance, Player player) {
        this.kg = player.getObjectId();
        this.BC = npcInstance.getObjectId();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(94);
        this.writeD(this.BC);
        this.writeD(this.kg);
        this.writeD(0);
    }
}
