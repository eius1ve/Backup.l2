/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collection;
import java.util.Collections;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MagicSkillLaunched
extends L2GameServerPacket {
    private final int zv;
    private final int zw;
    private final int _skillLevel;
    private final int zx;
    private final int zy;
    private final Collection<Creature> f;

    public MagicSkillLaunched(Creature creature, int n, int n2, Creature creature2) {
        this.zv = creature.getObjectId();
        this.zx = creature.getX();
        this.zy = creature.getY();
        this.zw = n;
        this._skillLevel = n2;
        this.f = Collections.singletonList(creature2);
    }

    public MagicSkillLaunched(Creature creature, Skill skill, Creature creature2) {
        this.zv = creature.getObjectId();
        this.zx = creature.getX();
        this.zy = creature.getY();
        this.zw = skill.getDisplayId();
        this._skillLevel = skill.getDisplayLevel();
        this.f = Collections.singletonList(creature2);
    }

    public MagicSkillLaunched(Creature creature, int n, int n2, Collection<Creature> collection) {
        this.zv = creature.getObjectId();
        this.zx = creature.getX();
        this.zy = creature.getY();
        this.zw = n;
        this._skillLevel = n2;
        this.f = collection;
    }

    public MagicSkillLaunched(Creature creature, Skill skill, Collection<Creature> collection) {
        this.zv = creature.getObjectId();
        this.zx = creature.getX();
        this.zy = creature.getY();
        this.zw = skill.getDisplayId();
        this._skillLevel = skill.getDisplayLevel();
        this.f = collection;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(84);
        this.writeD(0);
        this.writeD(this.zv);
        this.writeD(this.zw);
        this.writeD(this._skillLevel);
        this.writeD(this.f.size());
        for (Creature creature : this.f) {
            if (creature == null) continue;
            this.writeD(creature.getObjectId());
        }
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        if (player != null && !player.isInObserverMode()) {
            if (player.buffAnimRange() < 0) {
                return null;
            }
            if (player.buffAnimRange() == 0) {
                return this.zv == player.getObjectId() ? super.packet(player) : null;
            }
            return player.getDistance(this.zx, this.zy) < (double)player.buffAnimRange() ? super.packet(player) : null;
        }
        return super.packet(player);
    }
}
