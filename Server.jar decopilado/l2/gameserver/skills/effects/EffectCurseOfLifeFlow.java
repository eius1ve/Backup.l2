/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TObjectIntHashMap
 *  gnu.trove.TObjectIntIterator
 */
package l2.gameserver.skills.effects;

import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntIterator;
import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public final class EffectCurseOfLifeFlow
extends Effect {
    private CurseOfLifeFlowListener a;
    private TObjectIntHashMap<HardReference<? extends Creature>> b = new TObjectIntHashMap();

    public EffectCurseOfLifeFlow(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.a = new CurseOfLifeFlowListener();
        this._effected.addListener(this.a);
    }

    @Override
    public void onExit() {
        super.onExit();
        this._effected.removeListener(this.a);
        this.a = null;
    }

    @Override
    public boolean onActionTime() {
        if (this._effected.isDead()) {
            return false;
        }
        TObjectIntIterator tObjectIntIterator = this.b.iterator();
        while (tObjectIntIterator.hasNext()) {
            int n;
            tObjectIntIterator.advance();
            Creature creature = (Creature)((HardReference)tObjectIntIterator.key()).get();
            if (creature == null || creature.isDead() || creature.isCurrentHpFull() || (n = tObjectIntIterator.value()) <= 0) continue;
            double d = this.calc();
            double d2 = Math.min((double)n, d);
            double d3 = Math.min(creature.getCurrentHp() + d2, (double)creature.getMaxHp());
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HP_HAS_BEEN_RESTORED).addNumber((long)(d3 - creature.getCurrentHp())));
            creature.setCurrentHp(d3, false);
        }
        this.b.clear();
        return true;
    }

    private class CurseOfLifeFlowListener
    implements OnCurrentHpDamageListener {
        private CurseOfLifeFlowListener() {
        }

        @Override
        public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
            if (creature2 == creature || creature2 == EffectCurseOfLifeFlow.this._effected) {
                return;
            }
            int n = EffectCurseOfLifeFlow.this.b.get(creature2.getRef());
            EffectCurseOfLifeFlow.this.b.put(creature2.getRef(), n == 0 ? (int)d : n + (int)d);
        }
    }
}
