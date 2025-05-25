/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MagicAndSkillList
extends L2GameServerPacket {
    private int zq;
    private int _unk1;
    private int zr;

    public MagicAndSkillList(Creature creature, int n, int n2) {
        this.zq = creature.getObjectId();
        this._unk1 = n;
        this.zr = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(64);
        this.writeD(this.zq);
        this.writeD(this._unk1);
        this.writeD(this.zr);
    }
}
