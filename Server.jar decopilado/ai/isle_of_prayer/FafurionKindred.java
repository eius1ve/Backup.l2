/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.EFunction
 *  l2.gameserver.stats.funcs.FuncTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 */
package ai.isle_of_prayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.EFunction;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;

public class FafurionKindred
extends Fighter {
    private static final int aV = 22270;
    private static final int aW = 22271;
    private static final int aX = 2368;
    private static final int aY = 9691;
    private static final int aZ = 9700;
    ScheduledFuture<?> poisonTask;
    ScheduledFuture<?> despawnTask;
    List<SimpleSpawner> spawns = new ArrayList<SimpleSpawner>();
    private static final FuncTemplate a = new FuncTemplate(null, EFunction.Mul, Stats.HEAL_EFFECTIVNESS, 144, 0.0);

    public FafurionKindred(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.addStatFunc(a.getFunc((Object)this));
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        this.spawns.clear();
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnTask(22270)), 500L);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnTask(22271)), 500L);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnTask(22270)), 500L);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnTask(22271)), 500L);
        this.poisonTask = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new PoisonTask()), 3000L, 3000L);
        this.despawnTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new DeSpawnTask()), 300000L);
    }

    protected void onEvtDead(Creature creature) {
        this.cleanUp();
        super.onEvtDead(creature);
    }

    protected void onEvtSeeSpell(Skill skill, Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead() || skill == null) {
            return;
        }
        if (skill.getId() == 2368) {
            npcInstance.setCurrentHp(npcInstance.getCurrentHp() + 3000.0, false);
        }
        npcInstance.getAggroList().remove(creature, true);
    }

    protected boolean randomWalk() {
        return false;
    }

    private void cleanUp() {
        if (this.poisonTask != null) {
            this.poisonTask.cancel(false);
            this.poisonTask = null;
        }
        if (this.despawnTask != null) {
            this.despawnTask.cancel(false);
            this.despawnTask = null;
        }
        for (SimpleSpawner simpleSpawner : this.spawns) {
            simpleSpawner.deleteAll();
        }
        this.spawns.clear();
    }

    private void a(NpcInstance npcInstance, int n, int n2) {
        ItemInstance itemInstance = ItemFunctions.createItem((int)n);
        itemInstance.setCount((long)n2);
        itemInstance.dropToTheGround((Creature)npcInstance, Location.findPointToStay((GameObject)npcInstance, (int)100));
    }

    private class SpawnTask
    extends RunnableImpl {
        private final int ba;

        public SpawnTask(int n) {
            this.ba = n;
        }

        public void runImpl() {
            NpcInstance npcInstance = FafurionKindred.this.getActor();
            SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(this.ba));
            simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
            simpleSpawner.setRespawnDelay(30L, 40L);
            simpleSpawner.doSpawn(true);
            FafurionKindred.this.spawns.add(simpleSpawner);
        }
    }

    private class PoisonTask
    extends RunnableImpl {
        private PoisonTask() {
        }

        public void runImpl() {
            NpcInstance npcInstance = FafurionKindred.this.getActor();
            npcInstance.reduceCurrentHp(500.0, (Creature)npcInstance, null, true, false, true, false, false, false, false);
        }
    }

    private class DeSpawnTask
    extends RunnableImpl {
        private DeSpawnTask() {
        }

        public void runImpl() {
            NpcInstance npcInstance = FafurionKindred.this.getActor();
            FafurionKindred.this.a(npcInstance, 9691, Rnd.get((int)1, (int)2));
            if (Rnd.chance((int)36)) {
                FafurionKindred.this.a(npcInstance, 9700, Rnd.get((int)1, (int)3));
            }
            FafurionKindred.this.cleanUp();
            npcInstance.deleteMe();
        }
    }
}
