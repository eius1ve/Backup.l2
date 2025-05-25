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

import ai.residences.SiegeGuardFighter;
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

public class AlfredVonHellmann
extends SiegeGuardFighter {
    public static final Skill DAMAGE_SKILL = SkillTable.getInstance().getInfo(5000, 1);
    public static final Skill DRAIN_SKILL = SkillTable.getInstance().getInfo(5001, 1);
    private static Zone b = ReflectionUtils.getZone((String)"lidia_zone3");

    public AlfredVonHellmann(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtSpawn() {
        super.onEvtSpawn();
        b.setActive(true);
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"clanhall.AlfredVonHellmann.HEH_HEH_I_SEE_THAT_THE_FEAST_HAS_BEGAN_BE_WARY_THE_CURSE_OF_THE_HELLMANN_FAMILY_HAS_POISONED_THIS_LAND", (Object[])new Object[0]);
    }

    public void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        super.onEvtDead(creature);
        b.setActive(false);
        Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)"clanhall.AlfredVonHellmann.AARGH_IF_I_DIE_THEN_THE_MAGIC_FORCE_FIELD_OF_BLOOD_WILL", (Object[])new Object[0]);
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
        Creature creature2;
        NpcInstance npcInstance = this.getActor();
        super.onEvtAttacked(creature, n);
        if (PositionUtils.calculateDistance((GameObject)creature, (GameObject)npcInstance, (boolean)false) > 300.0 && Rnd.chance((double)0.13)) {
            this.addTaskCast(creature, DRAIN_SKILL);
        }
        if ((creature2 = npcInstance.getAggroList().getMostHated()) == creature && Rnd.chance((double)0.3)) {
            this.addTaskCast(creature, DAMAGE_SKILL);
        }
    }
}
