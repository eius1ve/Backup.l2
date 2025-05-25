/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Effect;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShortBuffStatusUpdate
extends L2GameServerPacket {
    int _skillId;
    int _skillLevel;
    int _skillDuration;

    public ShortBuffStatusUpdate(Effect effect) {
        this._skillId = effect.getSkill().getDisplayId();
        this._skillLevel = effect.getSkill().getDisplayLevel();
        this._skillDuration = effect.getTimeLeft();
    }

    public ShortBuffStatusUpdate() {
        this._skillId = 0;
        this._skillLevel = 0;
        this._skillDuration = 0;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(250);
        this.writeD(this._skillId);
        this.writeH(this._skillLevel);
        this.writeH(0);
        this.writeD(this._skillDuration);
    }
}
