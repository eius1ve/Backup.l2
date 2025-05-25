/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 */
package services;

import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import services.OlyInform;

class OlyInform.1
implements OnPlayerEnterListener {
    OlyInform.1() {
    }

    public void onPlayerEnter(Player player) {
        OlyInform.informOlyEnd(player);
    }
}
