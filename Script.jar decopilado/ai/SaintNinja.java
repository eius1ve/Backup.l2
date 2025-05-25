/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.ai.DefaultAI$Teleport
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Territory
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.templates.spawn.SpawnRange
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Territory;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.templates.spawn.SpawnRange;
import l2.gameserver.utils.Location;

public class SaintNinja
extends Fighter {
    private boolean m = false;
    private long g;
    private final NpcTemplate a;
    private final int ai;

    public SaintNinja(NpcInstance npcInstance) {
        super(npcInstance);
        this.a = NpcHolder.getInstance().getTemplate(npcInstance.getParameter("CreateOnePrivateOnAttack", 0));
        this.ai = npcInstance.getParameter("CreateOnePrivateOnAttackChance", 0);
    }

    protected void onEvtSpawn() {
        this.m = false;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (Rnd.get((int)100) < this.ai && !this.m) {
            this.m = true;
            SimpleSpawner simpleSpawner = new SimpleSpawner(this.a);
            simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)100));
            NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
            if (creature.isPet() || creature.isSummon()) {
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)2, (int)100));
            }
            npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature.getPlayer(), (Object)Rnd.get((int)1, (int)100));
            simpleSpawner.stopRespawn();
        }
        super.onEvtAttacked(creature, n);
    }

    protected boolean maybeMoveToHome() {
        int n;
        NpcInstance npcInstance = this.getActor();
        if (System.currentTimeMillis() - this.g < 10000L) {
            return false;
        }
        boolean bl = npcInstance.hasRandomWalk();
        Location location = npcInstance.getSpawnedLoc();
        if (location == null) {
            return false;
        }
        if (bl && (!Config.RND_WALK || Rnd.chance((int)Config.RND_WALK_RATE))) {
            return false;
        }
        if (!bl && npcInstance.isInRangeZ(location, (long)Config.MAX_DRIFT_RANGE)) {
            return false;
        }
        int n2 = location.x + Rnd.get((int)(-Config.MAX_DRIFT_RANGE), (int)Config.MAX_DRIFT_RANGE);
        int n3 = GeoEngine.getHeight((int)n2, (int)(n = location.y + Rnd.get((int)(-Config.MAX_DRIFT_RANGE), (int)Config.MAX_DRIFT_RANGE)), (int)location.z, (int)npcInstance.getGeoIndex());
        if (location.z - n3 > 64) {
            return false;
        }
        SpawnRange spawnRange = npcInstance.getSpawnRange();
        boolean bl2 = true;
        if (spawnRange != null && spawnRange instanceof Territory) {
            bl2 = ((Territory)spawnRange).isInside(n2, n);
        }
        if (bl2) {
            npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 4671, 1, 500, 0L)});
            ThreadPoolManager.getInstance().schedule((Runnable)new DefaultAI.Teleport((DefaultAI)this, new Location(n2, n, n3)), 500L);
            this.g = System.currentTimeMillis();
        }
        return bl2;
    }
}
