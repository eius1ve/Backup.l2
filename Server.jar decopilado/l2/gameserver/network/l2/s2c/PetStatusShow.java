/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PetStatusShow
extends L2GameServerPacket {
    private int wo;
    private int wn;

    public PetStatusShow(Summon summon) {
        this.wo = summon.getSummonType();
        this.wn = summon.getObjectId();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(177);
        this.writeD(this.wo);
        this.writeD(this.wn);
    }
}
