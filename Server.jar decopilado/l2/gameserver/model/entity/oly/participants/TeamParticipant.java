/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly.participants;

import java.util.ArrayList;
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TeamParticipant
extends Participant {
    private final HardReference<Player>[] a;
    private final int[] aI;
    private double[] h;
    private boolean[] a;
    private String _name = "";

    public TeamParticipant(int n, Competition competition, Player[] playerArray) {
        super(n, competition);
        this.h = new double[playerArray.length];
        this.a = new boolean[playerArray.length];
        this.aI = new int[playerArray.length];
        this.a = new HardReference[playerArray.length];
        this._name = playerArray[0].getName();
        for (int i = 0; i < playerArray.length; ++i) {
            this.a[i] = playerArray[i].getRef();
            this.aI[i] = playerArray[i].getActiveClassId();
            this.h[i] = 0.0;
            this.a[i] = true;
        }
    }

    @Override
    public void OnStart() {
        for (Player player : this.getPlayers()) {
            player.setOlyParticipant(this);
        }
    }

    @Override
    public void OnFinish() {
        for (Player player : this.getPlayers()) {
            player.sendPacket((IStaticPacket)ExOlympiadMatchEnd.STATIC);
            if (player.isDead()) {
                player.doRevive(100.0);
            }
            player.setOlyParticipant(null);
        }
    }

    @Override
    public boolean OnDamaged(Player player, Creature creature, double d, double d2, double d3) {
        if (player.isOlyCompetitionFinished()) {
            return false;
        }
        for (int i = 0; i < this.a.length; ++i) {
            if (!this.a[i] || this.a[i].get() != player) continue;
            if (creature.isPlayer()) {
                int n = i;
                this.h[n] = this.h[n] + Math.min(d, d2);
            }
            if (!(d2 - d <= 0.5)) continue;
            this.a[i] = false;
            creature.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            creature.abortAttack(true, true);
            if (creature.isCastingNow()) {
                creature.abortCast(true, false);
            }
            creature.sendActionFailed();
            this.getCompetition().ValidateWinner();
        }
        return true;
    }

    @Override
    public void OnDisconnect(Player player) {
        if (player.isOlyCompetitionFinished()) {
            return;
        }
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = false;
        }
        this.getCompetition().ValidateWinner();
    }

    @Override
    public void sendPacket(L2GameServerPacket l2GameServerPacket) {
        for (Player player : this.getPlayers()) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public boolean isAlive() {
        for (boolean bl : this.a) {
            if (!bl) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlayerLoose(Player player) {
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i].get() != player) continue;
            return !this.a[i];
        }
        return false;
    }

    @Override
    public double getDamageOf(Player player) {
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i].get() != player) continue;
            return this.h[i];
        }
        return 0.0;
    }

    @Override
    public Player[] getPlayers() {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (HardReference<Player> hardReference : this.a) {
            Player player = hardReference.get();
            if (player == null) continue;
            arrayList.add(player);
        }
        return arrayList.toArray(new Player[arrayList.size()]);
    }

    @Override
    public double getTotalDamage() {
        double d = 0.0;
        for (double d2 : this.h) {
            d += d2;
        }
        return d;
    }

    @Override
    public boolean validateThis() {
        Participant participant = null;
        for (Participant participant2 : this.getCompetition().getParticipants()) {
            if (participant2 == this) continue;
            participant = participant2;
        }
        for (int i = 0; i < this.a.length; ++i) {
            Player player = this.a[i].get();
            if (player == null || !player.isOnline() || player.isLogoutStarted()) {
                participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
                return false;
            }
            if (player.isDead()) {
                this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD_WHILE_DEAD).addName(player));
                participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
                return false;
            }
            if (player.getActiveClassId() != this.aI[i] || !player.getActiveClass().isBase()) {
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
            if (systemMessage == null) continue;
            this.sendPacket(systemMessage);
            participant.sendPacket(new SystemMessage(SystemMsg.YOUR_OPPONENT_DOES_NOT_MEET_THE_REQUIREMENTS_TO_DO_BATTLE_THE_MATCH_HAS_BEEN_CANCELLED));
            return false;
        }
        return true;
    }
}
