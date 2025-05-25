/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;

public class BookMarkTeleport
extends Skill {
    public BookMarkTeleport(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!creature.isPlayer() || !super.checkCondition(creature, creature2, bl, bl2, bl3)) {
            return false;
        }
        Player player = creature.getPlayer();
        TpBookMark tpBookMark = (TpBookMark)player.getVars().get("teleport_bookmark");
        if (tpBookMark == null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this.getId(), this.getLevel()));
            return false;
        }
        if (player.isActionBlocked("use_bookmark") || player.isInZone(Zone.ZoneType.epic) || player.isInZone(Zone.ZoneType.no_summon) || player.isInZone(Zone.ZoneType.no_escape) || player.isInZone(Zone.ZoneType.no_restart)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_IN_THIS_AREA);
            return false;
        }
        if (player.getActiveWeaponFlagAttachment() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
            return false;
        }
        if (player.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_IN_AN_OLYMPIAD_MATCH);
            return false;
        }
        if (player.getReflection() != ReflectionManager.DEFAULT) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_IN_AN_INSTANT_ZONE);
            return false;
        }
        if (player.isInDuel()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_DUEL);
            return false;
        }
        if (player.isInCombat() || player.getPvpFlag() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_BATTLE);
            return false;
        }
        if (player.isOnSiegeField() || player.isInZoneBattle()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_A_LARGESCALE_BATTLE_SUCH_AS_A_CASTLE_SIEGE_FORTRESS_SIEGE_OR_HIDEOUT_SIEGE);
            return false;
        }
        if (player.isFlying()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_FLYING);
            return false;
        }
        if (player.isInWater() || player.isInBoat()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_UNDERWATER);
            return false;
        }
        if (bl3 && !player.consumeItem(Config.EX_USE_TELEPORT_BOOK_SCROLL, 1L)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_BECAUSE_YOU_DO_NOT_HAVE_A_TELEPORT_ITEM);
            return false;
        }
        return true;
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        TpBookMark tpBookMark = (TpBookMark)player.getVars().remove("teleport_bookmark");
        if (tpBookMark == null) {
            return;
        }
        player.teleToLocation((Location)tpBookMark, ReflectionManager.DEFAULT);
    }
}
