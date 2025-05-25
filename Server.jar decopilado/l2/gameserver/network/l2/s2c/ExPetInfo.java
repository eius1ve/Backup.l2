/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.AbstractNpcPacket;
import l2.gameserver.network.l2.s2c.NpcInfoType;

public class ExPetInfo
extends AbstractNpcPacket {
    public ExPetInfo(Summon summon, Player player) {
        this._npcId = summon.getTemplate().npcId;
        this._isAttackable = summon.isAutoAttackable(player);
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
        this.writeEx(350);
        this.writeData();
    }
}
