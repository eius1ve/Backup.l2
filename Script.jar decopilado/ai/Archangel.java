/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.ReflectionUtils
 */
package ai;

import java.util.ArrayList;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.Fighter;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;

public class Archangel
extends Fighter {
    private static final int t = 29020;
    private final Zone _zone = ReflectionUtils.getZone((String)"[baium_epic]");
    private long e;

    public Archangel(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = null;
        for (Creature creature : this._zone.getObjects()) {
            if (creature == null || !creature.isNpc() || creature.getNpcId() != 29020) continue;
            npcInstance = (NpcInstance)creature;
            break;
        }
        if (npcInstance == null) {
            return false;
        }
        return super.thinkActive();
    }

    protected void thinkAttack() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return;
        }
        if (this.e < System.currentTimeMillis()) {
            ArrayList<Creature> arrayList = new ArrayList<Creature>();
            Creature creature = this._zone.getObjects();
            int n = ((Creature[])creature).length;
            for (int i = 0; i < n; ++i) {
                Creature creature2 = creature[i];
                if (creature2 == null || creature2.isDead()) continue;
                if (creature2.getNpcId() == 29020) {
                    if (!Rnd.chance((int)10)) continue;
                    arrayList.add(creature2);
                    continue;
                }
                arrayList.add(creature2);
            }
            if (!arrayList.isEmpty() && (creature = (Creature)arrayList.get(Rnd.get((int)arrayList.size()))) != null && (creature.getNpcId() == 29020 || creature.isPlayer())) {
                this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
                npcInstance.getAggroList().addDamageHate(creature, 100, 10);
            }
            this.e = System.currentTimeMillis() + 20000L;
        }
        super.thinkAttack();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && !npcInstance.isDead() && creature != null && creature.getNpcId() == 29020) {
            npcInstance.getAggroList().addDamageHate(creature, n, 10);
            this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
        }
        super.onEvtAttacked(creature, n);
    }

    protected boolean maybeMoveToHome() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && !npcInstance.isDead() && !this._zone.checkIfInZone((Creature)npcInstance)) {
            this.returnHome();
            return true;
        }
        return false;
    }

    protected void returnHome() {
        NpcInstance npcInstance = this.getActor();
        Location location = npcInstance.getSpawnedLoc();
        this.clearTasks();
        npcInstance.stopMove();
        npcInstance.getAggroList().clear(true);
        this.setAttackTimeout(Long.MAX_VALUE);
        this.setAttackTarget(null);
        this.changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2036, 1, 500, 0L)});
        npcInstance.teleToLocation(location.x, location.y, GeoEngine.getHeight((Location)location, (int)npcInstance.getGeoIndex()));
    }
}
