/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.Functions
 */
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;

public class WardrobePlayer
extends Functions
implements OnPlayerEnterListener,
OnPlayerExitListener {
    public void onPlayerExit(Player p) {
        p.unsetVar("IsTrying");
        p.unsetVar("tryEnchantColor");
        p.unsetVar("TryagathionCustom");
    }

    public void onPlayerEnter(Player arg0) {
    }
}
