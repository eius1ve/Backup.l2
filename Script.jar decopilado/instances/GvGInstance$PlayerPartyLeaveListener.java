/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 */
package instances;

import l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;

private class GvGInstance.PlayerPartyLeaveListener
implements OnPlayerPartyLeaveListener {
    private GvGInstance.PlayerPartyLeaveListener() {
    }

    public void onPartyLeave(Player player) {
        if (!GvGInstance.this.isActive()) {
            return;
        }
        Party party = player.getParty();
        if (party.getMemberCount() >= 3) {
            GvGInstance.this.a(player, false);
            return;
        }
        GvGInstance.this.b(party);
    }
}
