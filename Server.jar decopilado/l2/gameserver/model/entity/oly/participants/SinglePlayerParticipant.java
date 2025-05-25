/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly.participants;

import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.lang.reference.HardReference;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.entity.oly.Participant;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExOlympiadMatchEnd;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class SinglePlayerParticipant
extends Participant {
    private final HardReference<Player> W;
    private final int lP;
    private double E;
    private AtomicBoolean j = new AtomicBoolean(true);
    private String _name;

    public SinglePlayerParticipant(int n, Competition competition, Player player) {
        super(n, competition);
        this.W = player.getRef();
        this.lP = player.getActiveClassId();
        this._name = player.getName();
        this.j.set(true);
    }

    private Player getPlayer() {
        return this.W.get();
    }

    @Override
    public void OnStart() {
        Player player = this.getPlayer();
        if (player != null) {
            player.setOlyParticipant(this);
        }
    }

    @Override
    public void OnFinish() {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)ExOlympiadMatchEnd.STATIC);
            player.setOlyParticipant(null);
        }
    }

    @Override
    public boolean OnDamaged(Player player, Creature creature, double d, double d2, double d3) {
        if (!player.isOlyCompetitionStarted()) {
            return true;
        }
        if (d2 - d <= 1.0) {
            player.setCurrentHp(1.0, false);
            if (this.j.compareAndSet(true, false)) {
                creature.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                creature.abortAttack(true, true);
                if (creature.isCastingNow()) {
                    creature.abortCast(true, false);
                }
                creature.sendActionFailed();
                this.getCompetition().ValidateWinner();
            }
            return false;
        }
        if (creature.isPlayable()) {
            this.E += Math.min(d3, d2);
        }
        return true;
    }

    @Override
    public void OnDisconnect(Player player) {
        if (player.isOlyCompetitionFinished()) {
            return;
        }
        this.j.set(false);
        this.getCompetition().ValidateWinner();
    }

    @Override
    public void sendPacket(L2GameServerPacket l2GameServerPacket) {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public boolean isAlive() {
        return this.j.get();
    }

    @Override
    public boolean isPlayerLoose(Player player) {
        if (player != null && player == this.W.get()) {
            return !this.j.get();
        }
        return false;
    }

    @Override
    public double getDamageOf(Player player) {
        if (player != null && player == this.W.get()) {
            return this.E;
        }
        return 0.0;
    }

    @Override
    public Player[] getPlayers() {
        if (this.getPlayer() != null) {
            return new Player[]{this.getPlayer()};
        }
        return new Player[0];
    }

    @Override
    public double getTotalDamage() {
        return this.E;
    }

    @Override
    public boolean validateThis() {
        Participant participant = null;
        for (Participant participant2 : this.getCompetition().getParticipants()) {
            if (participant2 == this) continue;
            participant = participant2;
        }
        if (participant == null) {
            return false;
        }
        Player player = this.W.get();
        if (player == null || !player.isOnline() || player.isLogoutStarted()) {
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        if (player.isDead()) {
            this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD_WHILE_DEAD).addName(player));
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        if (player.getActiveClassId() != this.lP || !player.getActiveClass().isBase()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SINCE_YOU_HAVE_CHANGED_YOUR_CLASS_INTO_A_SUB_JOB_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player));
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        if (player.isCursedWeaponEquipped()) {
            this.sendPacket((L2GameServerPacket)((SystemMessage)new SystemMessage(SystemMsg.SINCE_YOU_NOW_OWN_S1_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player)).addItemName(player.getCursedWeaponEquippedId()));
            participant.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.SINCE_YOUR_OPPONENT_IS_NOW_THE_OWNER_OF_S1_THE_OLYMPIAD_HAS_BEEN_CANCELLED).addItemName(player.getCursedWeaponEquippedId()));
            return false;
        }
        if (!player.isInPeaceZone()) {
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        SystemMessage systemMessage = Competition.checkPlayer(player);
        if (systemMessage != null) {
            this.sendPacket(systemMessage);
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        return true;
    }
}
