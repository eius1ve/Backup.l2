/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.gameserver.listener.actor.player.OnAnswerListener
 *  l2.gameserver.model.Player
 */
package services;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import services.TeleportToRaid;

private static class TeleportToRaid.AnswerListener
implements OnAnswerListener {
    private final TeleportToRaid.RaidData a;
    private final HardReference<Player> af;

    public TeleportToRaid.AnswerListener(Player player, TeleportToRaid.RaidData raidData) {
        this.a = raidData;
        this.af = player.getRef();
    }

    public void sayYes() {
        Player player = (Player)this.af.get();
        if (player != null) {
            player.teleToLocation(this.a.f());
        }
    }

    public void sayNo() {
    }
}
