/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.events.impl.CastleSiegeEvent;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.GmListTable;
import l2.gameserver.templates.StatsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TakeCastle
extends Skill {
    private static final Logger dp = LoggerFactory.getLogger(TakeCastle.class);

    public TakeCastle(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!super.checkCondition(creature, creature2, bl, bl2, bl3)) {
            return false;
        }
        if (creature == null || !creature.isPlayer()) {
            return false;
        }
        Player player = (Player)creature;
        if (player.getClan() == null || !player.isClanLeader()) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        CastleSiegeEvent castleSiegeEvent = player.getEvent(CastleSiegeEvent.class);
        if (castleSiegeEvent == null) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        CastleSiegeEvent castleSiegeEvent2 = creature2.getEvent(CastleSiegeEvent.class);
        if (castleSiegeEvent2 != castleSiegeEvent) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        if (castleSiegeEvent.getSiegeClan("attackers", player.getClan()) == null) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        if (player.isMounted()) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        if (!player.isInRangeZ(creature2, 185L) || player.getZDeltaSq(creature2.getZ()) > 2500L) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_TARGET_IS_OUT_OF_RANGE);
            return false;
        }
        if (bl3) {
            castleSiegeEvent.broadcastTo(SystemMsg.THE_OPPOSING_CLAN_HAS_STARTED_TO_ENGRAVE_THE_HOLY_ARTIFACT, "defenders");
            String string = "TakeCastle: caster: " + creature.getName() + ", loc:" + creature.getLoc() + ", castle: " + castleSiegeEvent.getName() + ", target: " + creature2;
            dp.debug(string);
            GmListTable.broadcastMessageToGMs(string);
        }
        return true;
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            Player player;
            CastleSiegeEvent castleSiegeEvent;
            if (creature2 == null || !creature2.isArtefact() || !creature.isInRangeZ(creature2, 185L) || creature.getZDeltaSq(creature2.getZ()) > 2500L || (castleSiegeEvent = (player = (Player)creature).getEvent(CastleSiegeEvent.class)) == null) continue;
            castleSiegeEvent.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.CLAN_S1_HAS_SUCCESSFULLY_ENGRAVED_THE_HOLY_ARTIFACT).addString(player.getClan().getName()), "attackers", "defenders");
            castleSiegeEvent.processStep(player.getClan());
        }
    }
}
