/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import java.util.List;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.events.impl.DuelEvent;
import l2.gameserver.model.entity.events.objects.DuelSnapshotObject;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExDuelAskStart;
import l2.gameserver.network.l2.s2c.ExDuelEnd;
import l2.gameserver.network.l2.s2c.ExDuelReady;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class PlayerVsPlayerDuelEvent
extends DuelEvent {
    public PlayerVsPlayerDuelEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
    }

    protected PlayerVsPlayerDuelEvent(int n, String string) {
        super(n, string);
    }

    @Override
    public boolean canDuel(Player player, Player player2, boolean bl) {
        IStaticPacket iStaticPacket = this.canDuel0(player, player2);
        if (iStaticPacket != null) {
            player.sendPacket(iStaticPacket);
            return false;
        }
        iStaticPacket = this.canDuel0(player2, player);
        if (iStaticPacket != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_UNABLE_TO_REQUEST_A_DUEL_AT_THIS_TIME);
            return false;
        }
        return true;
    }

    @Override
    public void askDuel(Player player, Player player2) {
        Request request = new Request(Request.L2RequestType.DUEL, player, player2).setTimeout(10000L);
        request.set("duelType", 0);
        player.setRequest(request);
        player2.setRequest(request);
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_HAS_BEEN_CHALLENGED_TO_A_DUEL).addName(player2));
        player2.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.C1_HAS_CHALLENGED_YOU_TO_A_DUEL).addName(player), new ExDuelAskStart(player.getName(), 0)});
    }

    @Override
    public void createDuel(Player player, Player player2) {
        PlayerVsPlayerDuelEvent playerVsPlayerDuelEvent = new PlayerVsPlayerDuelEvent(this.getDuelType(), player.getObjectId() + "_" + player2.getObjectId() + "_duel");
        this.cloneTo(playerVsPlayerDuelEvent);
        playerVsPlayerDuelEvent.addObject(BLUE_TEAM, new DuelSnapshotObject(player, TeamType.BLUE));
        playerVsPlayerDuelEvent.addObject(RED_TEAM, new DuelSnapshotObject(player2, TeamType.RED));
        playerVsPlayerDuelEvent.sendPacket(new ExDuelReady(this));
        playerVsPlayerDuelEvent.reCalcNextTime(false);
    }

    @Override
    public void stopEvent() {
        this.clearActions();
        if (this._duelState.compareAndSet(DuelEvent.DuelState.EInProgress, DuelEvent.DuelState.EEnd)) {
            Object object;
            this.updatePlayers(false, false);
            for (Object object2 : this) {
                ((DuelSnapshotObject)object2).getPlayer().sendPacket((IStaticPacket)new ExDuelEnd(this));
                object = ((DuelSnapshotObject)object2).getPlayer().getTarget();
                if (object == null) continue;
                ((DuelSnapshotObject)object2).getPlayer().getAI().notifyEvent(CtrlEvent.EVT_FORGET_OBJECT, object);
            }
            switch (this._winner) {
                case NONE: {
                    this.sendPacket(SystemMsg.THE_DUEL_HAS_ENDED_IN_A_TIE);
                    break;
                }
                case RED: 
                case BLUE: {
                    Object object2;
                    List list = this.getObjects(this._winner.name());
                    object2 = this.getObjects(this._winner.revert().name());
                    this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_HAS_WON_THE_DUEL).addName(((DuelSnapshotObject)list.get(0)).getPlayer()));
                    object = object2.iterator();
                    while (object.hasNext()) {
                        DuelSnapshotObject duelSnapshotObject = (DuelSnapshotObject)object.next();
                        Player player = duelSnapshotObject.getPlayer();
                        if (player == null) continue;
                        player.broadcastPacket(new SocialAction(duelSnapshotObject.getPlayer().getObjectId(), 7));
                    }
                    break;
                }
            }
        }
        this.removeObjects(RED_TEAM);
        this.removeObjects(BLUE_TEAM);
    }

    @Override
    public void onDie(Player player) {
        TeamType teamType = player.getTeam();
        if (teamType == TeamType.NONE || this._aborted) {
            return;
        }
        player.stopAttackStanceTask();
        boolean bl = true;
        List list = this.getObjects(teamType.name());
        for (DuelSnapshotObject duelSnapshotObject : list) {
            if (duelSnapshotObject.getPlayer() == player) {
                duelSnapshotObject.setDead();
            }
            if (duelSnapshotObject.isDead()) continue;
            bl = false;
        }
        if (bl) {
            this._winner = teamType.revert();
            this.stopEvent();
        }
    }

    @Override
    public int getDuelType() {
        return 0;
    }

    @Override
    public void playerExit(Player player) {
        if (this._winner != TeamType.NONE || this._aborted) {
            return;
        }
        this._winner = player.getTeam().revert();
        this._aborted = false;
        this.stopEvent();
    }

    @Override
    public void packetSurrender(Player player) {
        this.playerExit(player);
    }

    @Override
    protected long startTimeMillis() {
        return System.currentTimeMillis() + 5000L;
    }
}
