/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public final class EffectCharge
extends Effect {
    public static final int MAX_CHARGE = 7;
    private final int CX;
    private final boolean fB;

    public EffectCharge(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.CX = effectTemplate.getParam().getInteger("charges", 7);
        this.fB = effectTemplate.getParam().getBool("fullCharge", false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getEffected().isPlayer()) {
            Player player = (Player)this.getEffected();
            if (player.getIncreasedForce() >= this.CX) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_FORCE_HAS_REACHED_MAXIMUM_CAPACITY);
            } else if (this.fB) {
                player.setIncreasedForce(this.CX);
            } else {
                player.setIncreasedForce(player.getIncreasedForce() + 1);
            }
        }
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
