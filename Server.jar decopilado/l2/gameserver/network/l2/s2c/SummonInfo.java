/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.AbstractNpcPacket;
import l2.gameserver.network.l2.s2c.NpcInfoType;

public class SummonInfo
extends AbstractNpcPacket {
    public SummonInfo(Summon summon, Creature creature) {
        if (summon.getPlayer() != null && summon.getPlayer().isInvisible()) {
            return;
        }
        this._npcId = summon.getTemplate().npcId;
        this._isAttackable = summon.isAutoAttackable(creature);
        this._rhand = 0;
        this._lhand = 0;
        this._enchantEffect = 0;
        this._showName = true;
        this._name = summon.getName();
        this._title = summon.getTitle();
        this._showSpawnAnimation = summon.getSpawnAnimation();
        this._isPet = true;
        this.setValues(summon, NpcInfoType.VALUES);
    }

    @Override
    protected void writeImpl() {
        this.writeC(139);
        this.writeData();
    }
}
