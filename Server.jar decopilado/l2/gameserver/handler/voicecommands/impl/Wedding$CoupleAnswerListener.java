/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.instancemanager.CoupleManager;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;

private static class Wedding.CoupleAnswerListener
implements OnAnswerListener {
    private HardReference<Player> o;
    private HardReference<Player> p;

    public Wedding.CoupleAnswerListener(Player player, Player player2) {
        this.o = player.getRef();
        this.p = player2.getRef();
    }

    @Override
    public void sayYes() {
        Player player;
        Player player2 = this.o.get();
        if (player2 == null || (player = this.p.get()) == null) {
            return;
        }
        CoupleManager.getInstance().createCouple(player2, player);
        player2.sendMessage(new CustomMessage("l2p.gameserver.model.L2Player.EngageAnswerYes", player, new Object[0]));
    }

    @Override
    public void sayNo() {
        Player player;
        Player player2 = this.o.get();
        if (player2 == null || (player = this.p.get()) == null) {
            return;
        }
        player2.sendMessage(new CustomMessage("l2p.gameserver.model.L2Player.EngageAnswerNo", player, new Object[0]));
    }
}
