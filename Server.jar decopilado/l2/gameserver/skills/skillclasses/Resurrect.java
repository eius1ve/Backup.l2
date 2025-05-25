/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.listener.actor.player.impl.ReviveAnswerListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.StatsSet;
import org.apache.commons.lang3.tuple.Pair;

public class Resurrect
extends Skill {
    private final boolean gi;
    private final int Do;

    public Resurrect(StatsSet statsSet) {
        super(statsSet);
        this.gi = statsSet.getBool("canPet", false);
        this.Do = statsSet.getInteger("expireResurrectTime", 0);
    }

    private boolean a(Player player, Creature creature, boolean bl) {
        boolean bl2 = true;
        for (GlobalEvent globalEvent : player.getEvents()) {
            if (globalEvent.canResurrect(player, creature, bl)) continue;
            bl2 = false;
        }
        if (bl2) {
            GlobalEvent globalEvent;
            SiegeEvent siegeEvent = player.getEvent(SiegeEvent.class);
            globalEvent = creature.getEvent(SiegeEvent.class);
            boolean bl3 = player.isInZone(Zone.ZoneType.SIEGE);
            boolean bl4 = creature.isInZone(Zone.ZoneType.SIEGE);
            if (siegeEvent == null && bl3 || globalEvent == null && bl4) {
                bl2 = false;
            }
        }
        if (!bl2) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        return true;
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!creature.isPlayer()) {
            return false;
        }
        if (creature2 == null || creature2 != creature && !creature2.isDead()) {
            creature.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        Player player = (Player)creature;
        Player player2 = creature2.getPlayer();
        if (player2 == null) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        if (player.isOlyParticipant() || player2.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        if (!this.a(player, creature2, bl)) {
            return false;
        }
        if (this.oneTarget()) {
            if (creature2.isPet()) {
                ReviveAnswerListener reviveAnswerListener;
                Pair<Integer, OnAnswerListener> pair = player2.getAskListener(false);
                ReviveAnswerListener reviveAnswerListener2 = reviveAnswerListener = pair != null && pair.getValue() instanceof ReviveAnswerListener ? (ReviveAnswerListener)pair.getValue() : null;
                if (reviveAnswerListener != null) {
                    if (reviveAnswerListener.isForPet()) {
                        creature.sendPacket((IStaticPacket)SystemMsg.RESURRECTION_HAS_ALREADY_BEEN_PROPOSED);
                    } else {
                        creature.sendPacket((IStaticPacket)SystemMsg.A_PET_CANNOT_BE_RESURRECTED_WHILE_ITS_OWNER_IS_IN_THE_PROCESS_OF_RESURRECTING);
                    }
                    return false;
                }
                if (!this.gi && this._targetType != Skill.SkillTargetType.TARGET_PET) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                    return false;
                }
            } else if (creature2.isPlayer()) {
                ReviveAnswerListener reviveAnswerListener;
                Pair<Integer, OnAnswerListener> pair = player2.getAskListener(false);
                ReviveAnswerListener reviveAnswerListener3 = reviveAnswerListener = pair != null && pair.getValue() instanceof ReviveAnswerListener ? (ReviveAnswerListener)pair.getValue() : null;
                if (reviveAnswerListener != null) {
                    if (reviveAnswerListener.isForPet()) {
                        creature.sendPacket((IStaticPacket)SystemMsg.WHILE_A_PET_IS_BEING_RESURRECTED_IT_CANNOT_HELP_IN_RESURRECTING_ITS_MASTER);
                    } else {
                        creature.sendPacket((IStaticPacket)SystemMsg.RESURRECTION_HAS_ALREADY_BEEN_PROPOSED);
                    }
                    return false;
                }
                if (this._targetType == Skill.SkillTargetType.TARGET_PET) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                    return false;
                }
                if (player2.isFestivalParticipant()) {
                    player.sendMessage(new CustomMessage("l2p.gameserver.skills.skillclasses.Resurrect", player, new Object[0]));
                    return false;
                }
            }
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        double d;
        double d2 = this._power;
        if (d2 < 100.0 && !this.isHandler() && (d2 += (d = this._power * (BaseStats.WIT.calcBonus(creature) - 1.0)) > 20.0 ? 20.0 : d) > 90.0) {
            d2 = 90.0;
        }
        block0: for (Creature creature2 : list) {
            Pair<Integer, OnAnswerListener> pair;
            if (creature2 == null || creature2.getPlayer() == null) continue;
            Object object = creature2.getEvents().iterator();
            while (object.hasNext()) {
                pair = object.next();
                if (pair.canResurrect((Player)creature, creature2, true)) continue;
                continue block0;
            }
            if (creature2.isPet() && this.gi) {
                if (creature2.getPlayer() == creature) {
                    ((PetInstance)creature2).doRevive(d2);
                } else {
                    creature2.getPlayer().reviveRequest((Player)creature, d2, true, this.Do);
                }
            } else {
                ReviveAnswerListener reviveAnswerListener;
                if (!creature2.isPlayer() || this._targetType == Skill.SkillTargetType.TARGET_PET || (reviveAnswerListener = (pair = ((Player)(object = (Player)creature2)).getAskListener(false)) != null && pair.getValue() instanceof ReviveAnswerListener ? (ReviveAnswerListener)pair.getValue() : null) != null || ((Player)object).isFestivalParticipant()) continue;
                ((Player)object).reviveRequest((Player)creature, d2, false, this.Do);
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
