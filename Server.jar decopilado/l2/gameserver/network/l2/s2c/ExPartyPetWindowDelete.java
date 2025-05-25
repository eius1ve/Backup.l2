/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPartyPetWindowDelete
extends L2GameServerPacket {
    private int wn;
    private int sP;
    private int wo;

    public ExPartyPetWindowDelete(Summon summon) {
        this.wn = summon.getObjectId();
        this.wo = summon.getSummonType();
        this.sP = summon.getPlayer().getObjectId();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(107);
        this.writeD(this.wn);
        this.writeC(this.wo);
        this.writeD(this.sP);
    }
}
