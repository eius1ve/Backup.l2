/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MagicSkillUse
extends L2GameServerPacket {
    private int _targetId;
    private int _skillId;
    private int _skillLevel;
    private int _hitTime;
    private int zz;
    private int zq;
    private int _x;
    private int _y;
    private int gl;
    private int sL;
    private int sM;
    private int sN;

    public MagicSkillUse(Creature creature, Creature creature2, int n, int n2, int n3, long l) {
        this.zq = creature.getObjectId();
        this._targetId = creature2.getObjectId();
        this._skillId = n;
        this._skillLevel = n2;
        this._hitTime = n3;
        this.zz = (int)l;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = creature2.getX();
        this.sM = creature2.getY();
        this.sN = creature2.getZ();
    }

    public MagicSkillUse(Creature creature, Creature creature2, Skill skill, long l) {
        this.zq = creature.getObjectId();
        this._targetId = creature2.getObjectId();
        this._skillId = skill.getDisplayId();
        this._skillLevel = skill.getDisplayLevel() >= 100 ? skill.getBaseLevel() : skill.getDisplayLevel();
        this._hitTime = skill.getHitTime();
        this.zz = (int)l;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = creature2.getX();
        this.sM = creature2.getY();
        this.sN = creature2.getZ();
    }

    public MagicSkillUse(Creature creature, Creature creature2, Skill skill, int n, long l) {
        this.zq = creature.getObjectId();
        this._targetId = creature2.getObjectId();
        this._skillId = skill.getDisplayId();
        this._skillLevel = skill.getDisplayLevel() >= 100 ? skill.getBaseLevel() : skill.getDisplayLevel();
        this._hitTime = n;
        this.zz = (int)l;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = creature2.getX();
        this.sM = creature2.getY();
        this.sN = creature2.getZ();
    }

    public MagicSkillUse(Creature creature, int n, int n2, int n3, long l) {
        this.zq = creature.getObjectId();
        this._targetId = creature.getTargetId();
        this._skillId = n;
        this._skillLevel = n2;
        this._hitTime = n3;
        this.zz = (int)l;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = creature.getX();
        this.sM = creature.getY();
        this.sN = creature.getZ();
    }

    public MagicSkillUse(Creature creature, Skill skill, int n, long l) {
        this.zq = creature.getObjectId();
        this._targetId = creature.getTargetId();
        this._skillId = skill.getDisplayId();
        this._skillLevel = skill.getDisplayLevel();
        this._hitTime = n;
        this.zz = (int)l;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = creature.getX();
        this.sM = creature.getY();
        this.sN = creature.getZ();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(72);
        this.writeD(0);
        this.writeD(this.zq);
        this.writeD(this._targetId);
        this.writeD(this._skillId);
        this.writeD(this._skillLevel);
        this.writeD(this._hitTime);
        this.writeD(0);
        this.writeD(this.zz);
        this.writeD(this._x);
        this.writeD(this._y);
        this.writeD(this.gl);
        this.writeH(0);
        this.writeH(0);
        this.writeD(this.sL);
        this.writeD(this.sM);
        this.writeD(this.sN);
        this.writeD(0);
        this.writeD(0);
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        if (player != null && !player.isInObserverMode()) {
            if (player.buffAnimRange() < 0) {
                return null;
            }
            if (player.buffAnimRange() == 0) {
                return this.zq == player.getObjectId() ? super.packet(player) : null;
            }
            return player.getDistance(this._x, this._y) < (double)player.buffAnimRange() ? super.packet(player) : null;
        }
        return super.packet(player);
    }
}
