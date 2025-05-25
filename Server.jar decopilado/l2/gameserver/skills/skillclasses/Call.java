/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.StatsSet;

public class Call
extends Skill {
    private final boolean fQ;
    private final int Dj;

    public Call(StatsSet statsSet) {
        super(statsSet);
        this.fQ = statsSet.getBool("party", false);
        this.Dj = statsSet.getInteger("requestWithCrystal", -1);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (creature.isPlayer()) {
            if (this.fQ && ((Player)creature).getParty() == null) {
                return false;
            }
            SystemMessage systemMessage = Call.canSummonHere((Player)creature);
            if (systemMessage != null) {
                creature.sendPacket((IStaticPacket)systemMessage);
                return false;
            }
            if (!this.fQ) {
                if (creature == creature2) {
                    return false;
                }
                systemMessage = Call.canBeSummoned(creature2);
                if (systemMessage != null) {
                    creature.sendPacket((IStaticPacket)systemMessage);
                    return false;
                }
            }
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        if (!creature.isPlayer()) {
            return;
        }
        SystemMessage systemMessage = Call.canSummonHere((Player)creature);
        if (systemMessage != null) {
            creature.sendPacket((IStaticPacket)systemMessage);
            return;
        }
        if (this.fQ) {
            if (((Player)creature).getParty() != null) {
                for (Player player : ((Player)creature).getParty().getPartyMembers()) {
                    if (player.equals(creature) || Call.canBeSummoned(player) != null) continue;
                    if (this.Dj >= 0) {
                        player.summonCharacterRequest(creature, creature.getLoc(), this.Dj);
                    } else {
                        player.stopMove();
                        player.teleToLocation(creature.getLoc(), creature.getGeoIndex());
                    }
                    this.getEffects(creature, player, this.getActivateRate() > 0, false);
                }
            }
            if (this.isSSPossible()) {
                creature.unChargeShots(this.isMagic());
            }
            return;
        }
        for (Creature creature2 : list) {
            if (creature2 == null || Call.canBeSummoned(creature2) != null) continue;
            if (this.Dj >= 0) {
                ((Player)creature2).summonCharacterRequest(creature, creature.getLoc(), this.Dj);
            } else {
                creature2.stopMove();
                creature2.teleToLocation(creature.getLoc(), creature.getGeoIndex());
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    public static SystemMessage canSummonHere(Player player) {
        if (player.isAlikeDead() || player.isOlyParticipant() || player.isInObserverMode() || player.isFlying() || player.isFestivalParticipant()) {
            return new SystemMessage(SystemMsg.NOTHING_HAPPENED);
        }
        if (player.isInZoneBattle() || player.isInZone(Zone.ZoneType.SIEGE) || player.isInZone(Zone.ZoneType.no_restart) || player.isInZone(Zone.ZoneType.no_summon) || player.isInBoat() || player.getReflection() != ReflectionManager.DEFAULT || player.isInZone(Zone.ZoneType.fun)) {
            return new SystemMessage(SystemMsg.YOU_MAY_NOT_SUMMON_FROM_YOUR_CURRENT_LOCATION);
        }
        if (player.isInStoreMode() || player.isProcessingRequest()) {
            return new SystemMessage(SystemMsg.YOU_CANNOT_SUMMON_DURING_A_TRADE_OR_WHILE_USING_A_PRIVATE_STORE);
        }
        return null;
    }

    public static SystemMessage canBeSummoned(Creature creature) {
        if (creature == null || !creature.isPlayer() || creature.isFlying() || creature.isInObserverMode() || creature.getPlayer().isFestivalParticipant() || !creature.getPlayer().getPlayerAccess().UseTeleport) {
            return new SystemMessage(SystemMsg.INVALID_TARGET);
        }
        if (creature.isOlyParticipant()) {
            return new SystemMessage(SystemMsg.YOU_CANNOT_SUMMON_PLAYERS_WHO_ARE_CURRENTLY_PARTICIPATING_IN_THE_GRAND_OLYMPIAD);
        }
        if (creature.isInZoneBattle() || creature.isInZone(Zone.ZoneType.SIEGE) || creature.isInZone(Zone.ZoneType.no_restart) || creature.isInZone(Zone.ZoneType.no_summon) || creature.getReflection() != ReflectionManager.DEFAULT || creature.isInBoat() || creature.isInZone(Zone.ZoneType.fun)) {
            return new SystemMessage(SystemMsg.YOUR_TARGET_IS_IN_AN_AREA_WHICH_BLOCKS_SUMMONING);
        }
        if (creature.isAlikeDead()) {
            return (SystemMessage)new SystemMessage(SystemMsg.C1_IS_DEAD_AT_THE_MOMENT_AND_CANNOT_BE_SUMMONED).addString(creature.getName());
        }
        if (creature.getPvpFlag() != 0 || creature.isInCombat()) {
            return (SystemMessage)new SystemMessage(SystemMsg.C1_IS_ENGAGED_IN_COMBAT_AND_CANNOT_BE_SUMMONED).addString(creature.getName());
        }
        Player player = (Player)creature;
        if (player.getPrivateStoreType() != 0 || player.isProcessingRequest()) {
            return (SystemMessage)new SystemMessage(SystemMsg.C1_IS_CURRENTLY_TRADING_OR_OPERATING_A_PRIVATE_STORE_AND_CANNOT_BE_SUMMONED).addString(creature.getName());
        }
        return null;
    }
}
