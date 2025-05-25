/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.JoinParty;

public class RequestAnswerJoinParty
extends L2GameClientPacket {
    private int dY;

    @Override
    protected void readImpl() {
        this.dY = this._buf.hasRemaining() ? this.readD() : 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        IStaticPacket iStaticPacket;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Request request = player.getRequest();
        if (request == null || !request.isTypeOf(Request.L2RequestType.PARTY)) {
            return;
        }
        if (!request.isInProgress()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (player.isOutOfControl()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        Player player2 = request.getRequestor();
        if (player2 == null) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.THAT_PLAYER_IS_NOT_ONLINE);
            player.sendActionFailed();
            return;
        }
        if (player2.getRequest() != request) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        int n = request.getInteger("itemDistribution");
        if (this.dY <= 0) {
            request.cancel();
            player2.sendPacket((IStaticPacket)new JoinParty(0, n));
            return;
        }
        if (player.isOlyParticipant()) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.A_PARTY_CANNOT_BE_FORMED_IN_THIS_AREA);
            player2.sendPacket((IStaticPacket)new JoinParty(0, n));
            return;
        }
        if (player2.isOlyParticipant()) {
            request.cancel();
            player2.sendPacket((IStaticPacket)new JoinParty(0, n));
            return;
        }
        Party party = player2.getParty();
        if (party != null && party.getMemberCount() >= Config.ALT_MAX_PARTY_SIZE) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.THE_PARTY_IS_FULL);
            player2.sendPacket((IStaticPacket)SystemMsg.THE_PARTY_IS_FULL);
            player2.sendPacket((IStaticPacket)new JoinParty(0, n));
            return;
        }
        if (!Config.ALT_PARTY_CLASS_LIMIT.isEmpty() && Config.ALT_PARTY_CLASS_LIMIT.containsKey(player.getActiveClass().getClassId())) {
            int n2 = 0;
            if (party != null) {
                for (Player player3 : party.getPartyMembers()) {
                    if (player3.getActiveClass().getClassId() != player.getActiveClass().getClassId()) continue;
                    ++n2;
                }
            } else if (player2.getActiveClass().getClassId() == player.getActiveClass().getClassId()) {
                ++n2;
            }
            if (n2 >= Config.ALT_PARTY_CLASS_LIMIT.get(player.getActiveClass().getClassId())) {
                request.cancel();
                player.sendPacket((IStaticPacket)SystemMsg.PARTY_PARTICIPATION_HAS_FAILED_BECAUSE_REQUIREMENTS_ARE_NOT_MET);
                player2.sendPacket(SystemMsg.PARTY_PARTICIPATION_HAS_FAILED_BECAUSE_REQUIREMENTS_ARE_NOT_MET, new JoinParty(0, n));
                return;
            }
        }
        if ((iStaticPacket = player.canJoinParty(player2)) != null) {
            request.cancel();
            player.sendPacket(iStaticPacket, ActionFail.STATIC);
            player2.sendPacket((IStaticPacket)new JoinParty(0, n));
            return;
        }
        if (party == null) {
            party = new Party(player2, n);
            player2.setParty(party);
        }
        try {
            player.joinParty(party);
            player2.sendPacket((IStaticPacket)new JoinParty(1, n));
        }
        finally {
            request.done();
        }
    }
}
