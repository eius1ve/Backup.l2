/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.matching;

import l2.gameserver.listener.actor.player.OnPlayerPartyInviteListener;
import l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener;
import l2.gameserver.model.Player;

private class MatchingRoom.PartyListenerImpl
implements OnPlayerPartyInviteListener,
OnPlayerPartyLeaveListener {
    private MatchingRoom.PartyListenerImpl() {
    }

    @Override
    public void onPartyInvite(Player player) {
        MatchingRoom.this.broadcastPlayerUpdate(player);
    }

    @Override
    public void onPartyLeave(Player player) {
        MatchingRoom.this.broadcastPlayerUpdate(player);
    }
}
