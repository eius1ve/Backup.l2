/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class MoSMonk
extends Fighter {
    public MoSMonk(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onIntentionAttack(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (this.getIntention() == CtrlIntention.AI_INTENTION_ACTIVE && Rnd.chance((int)20)) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)"scripts.ai.MoSMonk.onIntentionAttack", (Object[])new Object[0]);
        }
        super.onIntentionAttack(creature);
    }

    public boolean checkAggression(Creature creature) {
        if (creature.getActiveWeaponInstance() == null) {
            return false;
        }
        return super.checkAggression(creature);
    }
}
