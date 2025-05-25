/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Tears
extends DefaultAI {
    final Skill Invincible;
    final Skill Freezing;
    private static final int am = 2369;
    private static final int an = 25535;
    ScheduledFuture<?> spawnTask;
    ScheduledFuture<?> despawnTask;
    List<NpcInstance> spawns = new ArrayList<NpcInstance>();
    private boolean u;
    private int ao = 0;
    private long u = 0L;

    public Tears(NpcInstance npcInstance) {
        super(npcInstance);
        TIntObjectHashMap tIntObjectHashMap = this.getActor().getTemplate().getSkills();
        this.Invincible = (Skill)tIntObjectHashMap.get(5420);
        this.Freezing = (Skill)tIntObjectHashMap.get(5238);
    }

    protected void onEvtSeeSpell(Skill skill, Creature creature) {
        Player player;
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead() || skill == null || creature == null) {
            return;
        }
        if (System.currentTimeMillis() - this.u > 5000L) {
            this.ao = 0;
        }
        if (skill.getId() == 2369) {
            ++this.ao;
            this.u = System.currentTimeMillis();
        }
        if ((player = creature.getPlayer()) == null) {
            return;
        }
        int n = 1;
        Party party = player.getParty();
        if (party != null) {
            n = party.getMemberCount();
        }
        if (this.ao >= n) {
            this.ao = 0;
            npcInstance.getEffectList().stopEffect(this.Invincible);
        }
    }

    protected boolean createNewTask() {
        this.clearTasks();
        Creature creature = this.prepareTarget();
        if (creature == null) {
            return false;
        }
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return false;
        }
        double d = npcInstance.getDistance((GameObject)creature);
        double d2 = npcInstance.getCurrentHpPercents();
        int n = Rnd.get((int)100);
        if (d2 < 15.0 && !this.u) {
            this.u = true;
            this.addTaskBuff((Creature)npcInstance, this.Invincible);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1800033", (Object[])new Object[0]);
            return true;
        }
        if (n < 5 && this.spawnTask == null && this.despawnTask == null) {
            npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 5441, 1, 3000, 0L)});
            this.spawnTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnMobsTask()), 3000L);
            return true;
        }
        if (!npcInstance.isAMuted() && n < 75) {
            return this.chooseTaskAndTargets(null, creature, d);
        }
        return this.chooseTaskAndTargets(this.Freezing, creature, d);
    }

    private void d() {
        Creature creature;
        Location location;
        NpcInstance npcInstance = this.getActor();
        for (int i = 0; i < 9; ++i) {
            try {
                location = Location.findPointToStay((int)144298, (int)154420, (int)-11854, (int)300, (int)320, (int)npcInstance.getGeoIndex());
                SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(25535));
                simpleSpawner.setLoc(location);
                simpleSpawner.setReflection(npcInstance.getReflection());
                NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
                this.spawns.add(npcInstance2);
                creature = npcInstance.getAggroList().getRandomHated();
                if (creature == null) continue;
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)1, (int)100));
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        location = Location.findPointToStay((int)144298, (int)154420, (int)-11854, (int)300, (int)320, (int)npcInstance.getReflectionId());
        npcInstance.teleToLocation(location);
        creature = npcInstance.getAggroList().getRandomHated();
        if (creature != null) {
            npcInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)1, (int)100));
        }
        if (this.despawnTask != null) {
            this.despawnTask.cancel(false);
        }
        this.despawnTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new DeSpawnTask()), 30000L);
    }

    protected boolean randomWalk() {
        return false;
    }

    private class SpawnMobsTask
    extends RunnableImpl {
        private SpawnMobsTask() {
        }

        public void runImpl() {
            Tears.this.d();
            Tears.this.spawnTask = null;
        }
    }

    private class DeSpawnTask
    extends RunnableImpl {
        private DeSpawnTask() {
        }

        public void runImpl() {
            for (NpcInstance npcInstance : Tears.this.spawns) {
                if (npcInstance == null) continue;
                npcInstance.deleteMe();
            }
            Tears.this.spawns.clear();
            Tears.this.despawnTask = null;
        }
    }
}
