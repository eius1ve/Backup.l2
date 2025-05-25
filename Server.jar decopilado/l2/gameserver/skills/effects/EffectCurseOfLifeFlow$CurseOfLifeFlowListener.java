/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;

private class EffectCurseOfLifeFlow.CurseOfLifeFlowListener
implements OnCurrentHpDamageListener {
    private EffectCurseOfLifeFlow.CurseOfLifeFlowListener() {
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
