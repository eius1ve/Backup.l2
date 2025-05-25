/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.ReflectionBossInstance
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.Func
 *  l2.gameserver.stats.funcs.FuncSet
 */
package ai.custom;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncSet;

public class LabyrinthLostWarden
extends Fighter {
    public LabyrinthLostWarden(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        Reflection reflection = npcInstance.getReflection();
        if (!reflection.isDefault() && this.a(npcInstance.getNpcId()) && this.a() != null) {
            this.a().addStatFunc((Func)new FuncSet(Stats.POWER_ATTACK, 48, (Object)this, (double)this.a().getTemplate().basePAtk * 0.66));
        }
        super.onEvtDead(creature);
    }

    private boolean a(int n) {
        for (NpcInstance npcInstance : this.getActor().getReflection().getNpcs()) {
            if (npcInstance.getNpcId() != n || npcInstance.isDead()) continue;
            return false;
        }
        return true;
    }

    private NpcInstance a() {
        for (NpcInstance npcInstance : this.getActor().getReflection().getNpcs()) {
            if (!(npcInstance instanceof ReflectionBossInstance)) continue;
            return npcInstance;
        }
        return null;
    }
}
