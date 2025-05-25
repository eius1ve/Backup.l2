/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExFishingHpRegen
extends L2GameServerPacket {
    private int _time;
    private int vj;
    private int vk;
    private int vl;
    private int vm;
    private int vn;
    private int vo;
    private int char_obj_id;

    public ExFishingHpRegen(Creature creature, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.char_obj_id = creature.getObjectId();
        this._time = n;
        this.vj = n2;
        this.vk = n3;
        this.vm = n4;
        this.vl = n5;
        this.vn = n6;
        this.vo = n7;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(40);
        this.writeD(this.char_obj_id);
        this.writeD(this._time);
        this.writeD(this.vj);
        this.writeC(this.vk);
        this.writeC(this.vm);
        this.writeC(this.vl);
        this.writeD(this.vn);
        this.writeC(this.vo);
    }
}
