/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPartyPetWindowUpdate
extends L2GameServerPacket {
    private int wp;
    private int wq;
    private int _type;
    private int curHp;
    private int maxHp;
    private int curMp;
    private int maxMp;
    private int tG = 0;
    private String _name;

    public ExPartyPetWindowUpdate(Summon summon) {
        this.tG = summon.getObjectId();
        this.wp = summon.getPlayer().getObjectId();
        this.wq = summon.getTemplate().npcId + 1000000;
        this._type = summon.getSummonType();
        this._name = summon.getName();
        this.curHp = (int)summon.getCurrentHp();
        this.maxHp = summon.getMaxHp();
        this.curMp = (int)summon.getCurrentMp();
        this.maxMp = summon.getMaxMp();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(25);
        this.writeD(this.tG);
        this.writeD(this.wq);
        this.writeC(this._type);
        this.writeD(this.wp);
        this.writeS(this._name);
        this.writeD(this.curHp);
        this.writeD(this.maxHp);
        this.writeD(this.curMp);
        this.writeD(this.maxMp);
    }
}
