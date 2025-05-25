/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.AbnormalStatusUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAbnormalStatusUpdateFromTarget
extends L2GameServerPacket {
    private final List<AbnormalStatusUpdate.Effect> bY = new LinkedList<AbnormalStatusUpdate.Effect>();
    private final int up;

    public ExAbnormalStatusUpdateFromTarget(Creature creature) {
        this(creature, false);
    }

    public ExAbnormalStatusUpdateFromTarget(Creature creature, boolean bl) {
        this.up = creature.getObjectId();
        if (bl) {
            for (Effect effect : creature.getEffectList().getAllFirstEffects()) {
                if (effect == null || !effect.isInUse() || !effect.isActive() || effect.isHidden() || effect.getSkill().isToggle()) continue;
                this.addEffect(effect);
            }
        }
    }

    public void addEffect(Effect effect) {
        int n;
        if (!Config.SEND_BUFF_LIST_ON_TARGET && !effect.isOffensive() || !Config.SEND_DEBUFF_LIST_ON_TARGET && effect.isOffensive()) {
            return;
        }
        int n2 = effect.getDisplayLevel();
        int n3 = n = effect.getSkill().isToggle() ? -1 : effect.getTimeLeft();
        if (n2 < 100) {
            this.addEffect(effect.getDisplayId(), n2, n, 0, effect.getEffector() != null ? effect.getEffector().getObjectId() : 0);
        } else {
            this.addEffect(effect.getDisplayId(), effect.getSkill() != null ? effect.getSkill().getBaseLevel() : 1, n, 0, effect.getEffector() != null ? effect.getEffector().getObjectId() : 0);
        }
    }

    public void addEffect(int n, int n2, int n3, int n4, int n5) {
        this.bY.add(new AbnormalStatusUpdate.Effect(n, n2, n3, n4, n5));
    }

    @Override
    protected void writeImpl() {
        this.writeEx(230);
        this.writeD(this.up);
        this.writeH(this.bY.size());
        for (AbnormalStatusUpdate.Effect effect : this.bY) {
            this.writeD(effect.skillId);
            this.writeH(effect.skillLevel);
            this.writeH(effect.clientAbnormalId);
            this.writeOptionalD(effect.duration);
            this.writeD(effect.effectorObjectId);
        }
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        if (!Config.SEND_EFFECT_LIST_ON_TARGET) {
            return null;
        }
        return super.packet(player);
    }
}
