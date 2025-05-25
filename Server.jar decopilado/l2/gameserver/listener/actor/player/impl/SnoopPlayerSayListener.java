/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player.impl;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.listener.actor.player.OnPlayerSayListener;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Snoop;

public class SnoopPlayerSayListener
implements OnPlayerExitListener,
OnPlayerSayListener {
    private HardReference<Player> q;

    public SnoopPlayerSayListener(Player player) {
        this.q = player.getRef();
    }

    @Override
    public void onSay(Player player, ChatType chatType, String string, String string2) {
        Player player2 = this.q.get();
        if (player2 == null) {
            player.removeListener(this);
            return;
        }
        Object object = chatType == ChatType.TELL ? "->" + string : player.getName();
        player2.sendPacket((IStaticPacket)new Snoop(player.getObjectId(), player.getName(), chatType, (String)object, string2));
    }

    @Override
    public void onPlayerExit(Player player) {
        Player player2 = this.q.get();
        if (player2 == null) {
            return;
        }
        player2.getVars().remove("snoop_target");
    }

    public Player getOwner() {
        return this.q.get();
    }
}
