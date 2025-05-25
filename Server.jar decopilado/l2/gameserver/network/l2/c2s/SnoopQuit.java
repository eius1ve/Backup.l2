/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.listener.actor.player.impl.SnoopPlayerSayListener;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class SnoopQuit
extends L2GameClientPacket {
    private int sg;

    @Override
    protected void readImpl() {
        this.sg = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Player player2 = GameObjectsStorage.getPlayer(this.sg);
        if (player2 == null) {
            return;
        }
        for (SnoopPlayerSayListener snoopPlayerSayListener : player2.getListeners().getListeners(SnoopPlayerSayListener.class)) {
            if (snoopPlayerSayListener.getOwner() != player) continue;
            player2.removeListener(snoopPlayerSayListener);
            player.getVars().remove("snoop_target");
            break;
        }
    }
}
