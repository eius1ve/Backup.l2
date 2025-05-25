/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 */
package instances;

import instances.Frintezza;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;

public class Frintezza.CurrentHpListener
implements OnCurrentHpDamageListener {
    public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
        if (creature.isDead() || creature != Frintezza.this.i) {
            return;
        }
        double d2 = creature.getCurrentHp() - d;
        double d3 = creature.getMaxHp();
        switch (Frintezza.this.bu) {
            case 1: {
                if (!(d2 < 0.75 * d3)) break;
                Frintezza.this.bu = 2;
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.SecondMorph(Frintezza.this, 1)), 1100L);
                break;
            }
            case 2: {
                if (!(d2 < 0.1 * d3)) break;
                Frintezza.this.bu = 3;
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(Frintezza.this, 1)), 2000L);
            }
        }
    }
}
