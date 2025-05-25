/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.player.OnPvpPkKillListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillRewardZone
implements ScriptFile {
    private static final Logger eF = LoggerFactory.getLogger(KillRewardZone.class);
    private static final String iY = "playerKillReward";
    private static final String iZ = "karmaPlayerKillReward";
    private static final String ja = "playerKillCheck";
    private static final String jb = "ZoneRewardInterval";
    private static final String jc = "LastZoneRewardKill";
    private static final List<ZoneListener> ee = new CopyOnWriteArrayList<ZoneListener>();

    private static List<Pair<Pair<ItemTemplate, Long>, Integer>> i(String string) {
        if (string == null || (string = string.trim()).isEmpty()) {
            return Collections.emptyList();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.isEmpty()) continue;
            int n = string2.indexOf(58);
            if (n <= 0) {
                throw new IllegalArgumentException("Wrong reward definition: \"" + string2 + "\"");
            }
            int n2 = string2.indexOf(44, n);
            int n3 = Integer.parseInt(string2.substring(0, n).trim());
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n3);
            if (n2 <= 0) {
                arrayList.add(Pair.of((Object)Pair.of((Object)itemTemplate, (Object)Long.parseLong(string2.substring(n + 1).trim())), (Object)100));
                continue;
            }
            arrayList.add(Pair.of((Object)Pair.of((Object)itemTemplate, (Object)Long.parseLong(string2.substring(n + 1, n2).trim())), (Object)Integer.parseInt(string2.substring(n2 + 1).trim())));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public void onLoad() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            boolean bl = zone.getParams().isSet((Object)iY);
            boolean bl2 = zone.getParams().isSet((Object)iZ);
            if (!bl && !bl2) continue;
            List<PlayerCheck> list = zone.getParams().isSet((Object)ja) ? Stream.of(StringUtils.split((String)zone.getParams().getString((Object)ja, "none"), (char)'|')).map(string -> string.trim().toLowerCase()).filter(string -> !string.isEmpty()).map(string -> PlayerCheck.valueOf(string.toLowerCase())).collect(Collectors.toList()) : Collections.emptyList();
            ZoneListener zoneListener = new ZoneListener(KillRewardZone.i(zone.getParams().getString((Object)iY, "")), KillRewardZone.i(zone.getParams().getString((Object)iZ, "")), list, zone.getParams().getLong((Object)jb, 0L) * 60L * 1000L);
            zone.addListener((Listener)zoneListener);
            ee.add(zoneListener);
            zone.getInsidePlayers().forEach(player -> zoneListener.onZoneEnter(zone, (Creature)player));
        }
        eF.info("KillRewardZone: Loaded " + ee.size() + " player kill reward zone(s).");
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            LinkedList<ZoneListener> linkedList = new LinkedList<ZoneListener>();
            for (ZoneListener zoneListener : ee) {
                if (!zone.removeListener((Listener)zoneListener)) continue;
                linkedList.add(zoneListener);
                zone.getInsidePlayers().forEach(player -> zoneListener.onZoneLeave(zone, (Creature)player));
            }
            if (linkedList.isEmpty()) continue;
            ee.removeAll(linkedList);
        }
    }

    private static class ZoneListener
    implements OnZoneEnterLeaveListener {
        private final List<Pair<Pair<ItemTemplate, Long>, Integer>> ef;
        private final List<Pair<Pair<ItemTemplate, Long>, Integer>> eg;
        private final List<PlayerCheck> eh;
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
                        PlayerCheck playerCheck = (PlayerCheck)((Object)object.next());
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

        private ZoneListener(List<Pair<Pair<ItemTemplate, Long>, Integer>> list, List<Pair<Pair<ItemTemplate, Long>, Integer>> list2, List<PlayerCheck> list3, long l) {
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

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static abstract class PlayerCheck
    extends Enum<PlayerCheck> {
        public static final /* enum */ PlayerCheck hwid = new PlayerCheck(){

            @Override
            boolean check(Player player, Player player2) {
                if (!player.isConnected() || !player2.isConnected()) {
                    return false;
                }
                if (player.getNetConnection().getHwid() != null) {
                    return !StringUtils.equals((CharSequence)player.getNetConnection().getHwid(), (CharSequence)player2.getNetConnection().getHwid());
                }
                return true;
            }
        };
        public static final /* enum */ PlayerCheck ip = new PlayerCheck(){

            @Override
            boolean check(Player player, Player player2) {
                if (!player.isConnected() || !player2.isConnected()) {
                    return false;
                }
                return !StringUtils.equals((CharSequence)player.getIP(), (CharSequence)player2.getIP());
            }
        };
        private static final /* synthetic */ PlayerCheck[] a;

        public static PlayerCheck[] values() {
            return (PlayerCheck[])a.clone();
        }

        public static PlayerCheck valueOf(String string) {
            return Enum.valueOf(PlayerCheck.class, string);
        }

        abstract boolean check(Player var1, Player var2);

        private static /* synthetic */ PlayerCheck[] a() {
            return new PlayerCheck[]{hwid, ip};
        }

        static {
            a = PlayerCheck.a();
        }
    }
}
