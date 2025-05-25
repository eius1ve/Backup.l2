/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.network.l2.components.CustomMessage
 */
package instances;

import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.network.l2.components.CustomMessage;

private class GvGInstance.TeleportListener
implements OnTeleportListener {
    private GvGInstance.TeleportListener() {
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        if (GvGInstance.this.x.checkIfInZone(n, n2, n3, reflection) || GvGInstance.this.z.checkIfInZone(n, n2, n3, reflection) || GvGInstance.this.B.checkIfInZone(n, n2, n3, reflection)) {
            return;
        }
        GvGInstance.this.a(player, false);
        player.sendMessage(new CustomMessage("scripts.event.gvg.expelled", player, new Object[0]));
    }
}
