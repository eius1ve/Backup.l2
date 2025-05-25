/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.listener.actor.player.OnPvpPkKillListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package zones;

import java.util.Iterator;
import l2.commons.util.Rnd;
import l2.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import zones.KillRewardZone;

class KillRewardZone.ZoneListener.1
implements OnPvpPkKillListener {
    KillRewardZone.ZoneListener.1() {
    }

    private boolean c(Player player, Player player2) {
        if (!player.isConnected() || !player2.isConnected()) {
            return false;
        }
        if (StringUtils.equals((CharSequence)player.getIP(), (CharSequence)player2.getIP())) {
            return true;
        }
        if (player.getNetConnection().getHwid() != null) {
            return StringUtils.equals((CharSequence)player.getNetConnection().getHwid(), (CharSequence)player2.getNetConnection().getHwid());
        }
        return false;
    }

    public void onPvpPkKill(Player player, Player player2, boolean bl) {
        Object object;
        if (!ZoneListener.this.eh.isEmpty()) {
            object = ZoneListener.this.eh.iterator();
            while (object.hasNext()) {
                KillRewardZone.PlayerCheck playerCheck = (KillRewardZone.PlayerCheck)((Object)object.next());
                if (playerCheck.check(player, player2)) continue;
                return;
            }
        }
        Object object2 = object = bl ? ZoneListener.this.eg : ZoneListener.this.ef;
        if (object.isEmpty()) {
            return;
        }
        long l = player.getVarLong(KillRewardZone.jc, 0L);
        long l2 = System.currentTimeMillis();
        if (l2 - l < ZoneListener.this.eC) {
            return;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            Pair pair = (Pair)iterator.next();
            if (!Rnd.chance((int)((Integer)pair.getRight()))) continue;
            Pair pair2 = (Pair)pair.getLeft();
            if (!ItemFunctions.canAddItem((Player)player, (ItemTemplate)((ItemTemplate)pair2.getKey()), (long)((Long)pair2.getValue()))) {
                return;
            }
            ItemFunctions.addItem((Playable)player, (ItemTemplate)((ItemTemplate)pair2.getKey()), (long)((Long)pair2.getValue()), (boolean)true);
        }
        player.setVar(KillRewardZone.jc, l2, -1L);
    }
}
