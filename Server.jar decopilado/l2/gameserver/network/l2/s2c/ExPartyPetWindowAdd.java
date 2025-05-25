/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPartyPetWindowAdd
extends L2GameServerPacket {
    private final int wf;
    private final int wg;
    private final int wh;
    private final int wi;
    private final int wj;
    private final int wk;
    private final int wl;
    private final int wm;
    private final String eY;

    public ExPartyPetWindowAdd(Summon summon) {
        this.wm = summon.getObjectId();
        this.wf = summon.getPlayer().getObjectId();
        this.wg = summon.getTemplate().npcId + 1000000;
        this.wh = summon.getSummonType();
        this.eY = summon.getName();
        this.wi = (int)summon.getCurrentHp();
        this.wj = summon.getMaxHp();
        this.wk = (int)summon.getCurrentMp();
        this.wl = summon.getMaxMp();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(24);
        this.writeD(this.wm);
        this.writeD(this.wg);
        this.writeC(this.wh);
        this.writeD(this.wf);
        this.writeS(this.eY);
        this.writeD(this.wi);
        this.writeD(this.wj);
        this.writeD(this.wk);
        this.writeD(this.wl);
    }
}
