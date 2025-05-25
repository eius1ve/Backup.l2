/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.stats.Env
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.Func
 *  l2.gameserver.templates.npc.MinionData
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.npc.MinionData;

public class GraveRobberSummoner
extends Mystic {
    private static final int[] d = new int[]{22683, 22684, 22685, 22686};
    private int R = 1;

    public GraveRobberSummoner(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.addStatFunc((Func)new FuncMulMinionCount(Stats.MAGIC_DEFENCE, 48, npcInstance));
        npcInstance.addStatFunc((Func)new FuncMulMinionCount(Stats.POWER_DEFENCE, 48, npcInstance));
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        NpcInstance npcInstance = this.getActor();
        npcInstance.getMinionList().addMinion(new MinionData(d[Rnd.get((int)d.length)], Rnd.get((int)2)));
        this.R = Math.max(npcInstance.getMinionList().getAliveMinions().size(), 1);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        MonsterInstance monsterInstance = (MonsterInstance)this.getActor();
        if (monsterInstance.isDead()) {
            return;
        }
        this.R = Math.max(monsterInstance.getMinionList().getAliveMinions().size(), 1);
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        npcInstance.getMinionList().deleteMinions();
        super.onEvtDead(creature);
    }

    private class FuncMulMinionCount
    extends Func {
        public FuncMulMinionCount(Stats stats, int n, Object object) {
            super(stats, n, object);
        }

        public void calc(Env env) {
            env.value *= (double)GraveRobberSummoner.this.R;
        }
    }
}
