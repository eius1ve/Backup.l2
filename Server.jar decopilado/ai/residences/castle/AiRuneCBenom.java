/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Balanced
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 *  l2.gameserver.utils.ReflectionUtils
 */
package ai.residences.castle;

import java.util.Collections;
import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.util.Rnd;
import l2.gameserver.ai.Balanced;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;
import l2.gameserver.utils.ReflectionUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AiRuneCBenom
extends Balanced {
    private static final Location a = new Location(11563, -49152, -537);
    private static final Location b = new Location(11882, -49216, -3008);
    private static final long P = 30000L;
    private static final long Q = 90000L;
    private static final Location[] d = new Location[]{new Location(12860, -49158, 976), new Location(14878, -51339, 1024), new Location(15674, -49970, 864), new Location(15696, -48326, 864), new Location(14873, -46956, 1024), new Location(12157, -49135, -1088), new Location(12875, -46392, -288), new Location(14087, -46706, -288), new Location(14086, -51593, -288), new Location(12864, -51898, -288), new Location(15538, -49153, -1056), new Location(17001, -49149, -1064)};
    private final Zone a = ReflectionUtils.getZone((String)"[rune03_npc2016_20]");
    private boolean O = false;
    private long R = 0L;
    private HardReference<Player> h = HardReferences.emptyRef();

    public AiRuneCBenom(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtFinishCasting(Skill skill, Creature creature) {
        Player player = (Player)this.h.get();
        if (skill != null && player != null && skill.getId() == 4222) {
            player.teleToLocation(this.a());
            this.getActor().getAggroList().remove((Creature)player, true);
            this.h = HardReferences.emptyRef();
        }
        super.onEvtFinishCasting(skill, creature);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        this.c(npcInstance);
        return super.thinkActive();
    }

    protected void thinkAttack() {
        if (this.R == 0L) {
            this.R = System.currentTimeMillis() + 90000L;
        }
        if (this.R + 90000L < System.currentTimeMillis()) {
            Player player = (Player)this.h.get();
            this.h();
            if (player != null) {
                this.getActor().doCast(SkillTable.getInstance().getInfo(4222, 1), (Creature)player, false);
            }
            this.R = System.currentTimeMillis();
        }
        super.thinkAttack();
    }

    private void h() {
        this.h = HardReferences.emptyRef();
        List list = this.getActor().getAggroList().getHateList(900);
        Collections.shuffle(list);
        for (Creature creature : list) {
            if (!creature.isPlayer() || creature.isDead() || !GeoEngine.canSeeTarget((GameObject)this.getActor(), (GameObject)creature, (boolean)false)) continue;
            this.h = creature.getPlayer().getRef();
        }
    }

    private void c(NpcInstance npcInstance) {
        if (this.a(npcInstance)) {
            this.O = true;
            npcInstance.setSpawnedLoc(a);
            npcInstance.teleToLocation(a);
            Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"1010623", (Object[])new Object[0]);
        }
        if (this.b(npcInstance)) {
            npcInstance.setSpawnedLoc(b);
            npcInstance.teleToLocation(b);
            Location location = npcInstance.getSpawnedLoc();
            this.clearTasks();
            npcInstance.stopMove();
            npcInstance.getAggroList().clear(true);
            this.setAttackTimeout(Long.MAX_VALUE);
            this.setAttackTarget(null);
            this.changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
            npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2036, 1, 500, 0L)});
            npcInstance.teleToLocation(location.x, location.y, GeoEngine.getHeight((Location)location, (int)npcInstance.getGeoIndex()));
            this.O = false;
        }
    }

    private boolean a(NpcInstance npcInstance) {
        Castle castle = (Castle)ResidenceHolder.getInstance().getResidence(Castle.class, 8);
        return !this.O && castle != null && castle.getSiegeEvent() != null && castle.getOwner() != null && castle.getSiegeEvent().isInProgress() && !npcInstance.isDead();
    }

    private boolean b(NpcInstance npcInstance) {
        Castle castle = (Castle)ResidenceHolder.getInstance().getResidence(Castle.class, 8);
        return npcInstance != null && castle != null && castle.getSiegeEvent() != null && !castle.getSiegeEvent().isInProgress() && !npcInstance.isDead() && !this.a.checkIfInZone((Creature)npcInstance);
    }

    public void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        this.O = false;
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"1010626", (Object[])new Object[0]);
        NpcUtils.spawnSingle((int)29055, (int)12589, (int)-49044, (int)-3008, (long)120000L);
    }

    protected boolean maybeMoveToHome() {
        return false;
    }

    private Location a() {
        int n = Rnd.get((int)d.length);
        return d[n];
    }
}
