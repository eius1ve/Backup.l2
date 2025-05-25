/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.events.impl.DuelEvent;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExDuelReady
extends L2GameServerPacket {
    private int qE;

    public ExDuelReady(DuelEvent duelEvent) {
        this.qE = duelEvent.getDuelType();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(78);
        this.writeD(this.qE);
    }
}
