/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.listener.actor.player.OnPvpPkKillListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package zones;

import java.util.Iterator;
import java.util.List;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import zones.KillRewardZone;

private static class KillRewardZone.ZoneListener
implements OnZoneEnterLeaveListener {
    private final List<Pair<Pair<ItemTemplate, Long>, Integer>> ef;
    private final List<Pair<Pair<ItemTemplate, Long>, Integer>> eg;
    private final List<KillRewardZone.PlayerCheck> eh;
    private final long eC;
    private final OnPvpPkKillListener a = new OnPvpPkKillListener(){

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
            if (!eh.isEmpty()) {
                object = eh.iterator();
                while (object.hasNext()) {
                    KillRewardZone.PlayerCheck playerCheck = (KillRewardZone.PlayerCheck)((Object)object.next());
                    if (playerCheck.check(player, player2)) continue;
                    return;
                }
            }
            Object object2 = object = bl ? eg : ef;
            if (object.isEmpty()) {
                return;
            }
            long l = player.getVarLong(KillRewardZone.jc, 0L);
            long l2 = System.currentTimeMillis();
            if (l2 - l < eC) {
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
    };

    private KillRewardZone.ZoneListener(List<Pair<Pair<ItemTemplate, Long>, Integer>> list, List<Pair<Pair<ItemTemplate, Long>, Integer>> list2, List<KillRewardZone.PlayerCheck> list3, long l) {
        this.ef = list;
        this.eg = list2;
        this.eh = list3;
        this.eC = l;
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature == null || !creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        player.addListener((Listener)this.a);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (creature == null || !creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        player.removeListener((Listener)this.a);
    }
}
