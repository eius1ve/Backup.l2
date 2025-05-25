/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent
 *  l2.gameserver.model.entity.events.objects.SpawnExObject
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.PositionUtils
 *  l2.gameserver.utils.ReflectionUtils
 */
package ai.residences.clanhall;

import ai.residences.SiegeGuardMystic;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.PositionUtils;
import l2.gameserver.utils.ReflectionUtils;

public class GiselleVonHellmann
extends SiegeGuardMystic {
    private static final Skill k = SkillTable.getInstance().getInfo(5003, 1);
    private static final Zone c = ReflectionUtils.getZone((String)"lidia_zone1");
    private static final Zone d = ReflectionUtils.getZone((String)"lidia_zone2");

    public GiselleVonHellmann(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtSpawn() {
        super.onEvtSpawn();
        c.setActive(true);
        d.setActive(true);
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"clanhall.GiselleVonHellmann.ARISE_MY_FAITHFUL_SERVANTS_YOU_MY_PEOPLE_WHO_HAVE_INHERITED_THE_BLOOD", (Object[])new Object[0]);
    }

    public void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        super.onEvtDead(creature);
        c.setActive(false);
        d.setActive(false);
        Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)"clanhall.GiselleVonHellmann.AARGH_IF_I_DIE_THEN_THE_MAGIC_FORCE_FIELD_OF_BLOOD_WILL", (Object[])new Object[0]);
        ClanHallSiegeEvent clanHallSiegeEvent = (ClanHallSiegeEvent)npcInstance.getEvent(ClanHallSiegeEvent.class);
        if (clanHallSiegeEvent == null) {
            return;
        }
        SpawnExObject spawnExObject = (SpawnExObject)clanHallSiegeEvent.getFirstObject("boss");
        NpcInstance npcInstance2 = spawnExObject.getFirstSpawned();
        if (npcInstance2.getCurrentHpRatio() == 1.0) {
            npcInstance2.setCurrentHp((double)(npcInstance2.getMaxHp() / 2), true);
        }
    }

    public void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        super.onEvtAttacked(creature, n);
        if (PositionUtils.calculateDistance((GameObject)creature, (GameObject)npcInstance, (boolean)false) > 300.0 && Rnd.chance((double)0.13)) {
            this.addTaskCast(creature, k);
        }
    }
}
