/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.CollectionUtils
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.TimeUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.DropEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.commons.collections.CollectionUtils;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DropEvent
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger m = LoggerFactory.getLogger(DropEvent.class);
    private static final String C = "DropEvent";
    private static final DropEvent a = new DropEvent();
    private static final int bJ = 100;
    private static boolean R = false;
    private static DropEventItem[][] a;
    private static Map<Integer, List<DropEventItem>> f;
    private static DropEventItem[][] b;
    private static Map<Integer, List<DropEventItem>> g;

    private static boolean isActive() {
        return DropEvent.IsActive((String)C);
    }

    /*
     * WARNING - void declaration
     */
    private static DropEventItem a(String string) {
        Pattern pattern = Pattern.compile("(\\d+)[:-](\\d+)\\-?(\\d+)?\\(([\\d+\\.]+)\\)(<(\\d+)-(\\d+)>)?(\\[([\\d,]+)\\])?");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            void var14_13;
            Object[] objectArray;
            int n = Integer.parseInt(matcher.group(1));
            long l = Long.parseLong(matcher.group(2));
            long l2 = StringUtils.isBlank((CharSequence)matcher.group(3)) ? l : Long.parseLong(matcher.group(3));
            double d = Double.parseDouble(matcher.group(4));
            int n2 = matcher.group(6) != null ? Integer.parseInt(matcher.group(6)) : 1;
            int n3 = matcher.group(6) != null ? Integer.parseInt(matcher.group(7)) : 100;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            if (matcher.group(9) != null) {
                n2 = 0;
                n3 = -1;
                for (int n4 : objectArray = (Object[])matcher.group(9).split(",")) {
                    arrayList.add(Integer.parseInt((String)n4));
                }
            }
            objectArray = new int[arrayList.size()];
            boolean i = false;
            while (var14_13 < objectArray.length) {
                objectArray[var14_13] = (Integer)arrayList.get((int)var14_13);
                ++var14_13;
            }
            return new DropEventItem(n, l, l2, d, (int[])objectArray, n2, n3);
        }
        throw new RuntimeException("Can't parse drop event item \"" + string + "\"");
    }

    private static List<DropEventItem> a(String string) {
        ArrayList<DropEventItem> arrayList = new ArrayList<DropEventItem>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            arrayList.add(DropEvent.a(string2));
        }
        return arrayList;
    }

    private static void a(Map<Integer, List<DropEventItem>> map, DropEventItem[][] dropEventItemArray, String string) {
        List<DropEventItem> list = DropEvent.a(string);
        for (DropEventItem dropEventItem : list) {
            for (int i = dropEventItem.getMinLvl(); i <= dropEventItem.getMaxLvl(); ++i) {
                if (i <= 0) continue;
                Object[] objectArray = dropEventItemArray[i];
                dropEventItemArray[i] = (DropEventItem[])ArrayUtils.add((Object[])objectArray, (Object)dropEventItem);
            }
            int[] nArray = dropEventItem.getNpcIds();
            int n = nArray.length;
            for (int i = 0; i < n; ++i) {
                Integer n2 = nArray[i];
                List<DropEventItem> list2 = map.get(n2);
                if (list2 == null) {
                    list2 = new ArrayList<DropEventItem>();
                    map.put(n2, list2);
                }
                list2.add(dropEventItem);
            }
        }
    }

    private static void y() {
        if (DropEvent.isActive()) {
            f = new HashMap<Integer, List<DropEventItem>>();
            a = new DropEventItem[100][];
            DropEvent.a(f, a, Config.EVENT_DropEvent_Items);
            g = new HashMap<Integer, List<DropEventItem>>();
            b = new DropEventItem[100][];
            DropEvent.a(g, b, Config.EVENT_DropEvent_PartyItems);
        }
    }

    private void a(GameObject gameObject, List<Player> list, Set<DropEventItem> set) {
        Object object;
        if (set == null || set.isEmpty()) {
            return;
        }
        int n = Calendar.getInstance().get(11) * 100 + Calendar.getInstance().get(12);
        if (!Config.Event_DropEvent_Time_Period.isEmpty() && !TimeUtils.isWithinTime((String)Config.Event_DropEvent_Time_Period, (int)n)) {
            return;
        }
        if (Config.Event_DropEvent_Check_Hwid) {
            ArrayList<Player> arrayList = new ArrayList<Player>(list);
            list = new ArrayList<Player>(arrayList.size());
            Iterator object2 = arrayList.iterator();
            while (object2.hasNext()) {
                object = (Player)object2.next();
                boolean bl = true;
                int n2 = arrayList.size();
                for (int player = 0; bl && player < n2; ++player) {
                    Player player2 = (Player)arrayList.get(player);
                    if (object == player2 || object.getNetConnection() == null || player2.getNetConnection() == null || !Objects.equals(object.getNetConnection().getHwid(), player2.getNetConnection().getHwid())) continue;
                    bl = false;
                }
                if (!bl) continue;
                list.add((Player)object);
            }
        }
        if (Config.EVENT_DropEvent_PartyItems_Distribute_Random) {
            list = new ArrayList<Player>(list);
            Collections.shuffle(list);
        }
        for (DropEventItem dropEventItem : set) {
            if (!Rnd.chance((double)dropEventItem.getChance())) continue;
            object = new ArrayList(list.size());
            for (Player player : list) {
                if (player == null || player.isDead() || gameObject.getDistance3D((GameObject)player) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                object.add(player);
            }
            if (object.isEmpty()) continue;
            long l = dropEventItem.getItemCount();
            long l2 = Math.max(1L, l / (long)object.size());
            block4: while (l >= l2) {
                Iterator iterator = object.iterator();
                while (iterator.hasNext()) {
                    long l3;
                    Player player = (Player)iterator.next();
                    long l4 = l3 = Config.EVENT_DropEvent_Rate ? (long)((double)l2 * player.getRateItems()) : l2;
                    if (ArrayUtils.contains((int[])Config.AUTO_LOOT_EXCLUDE_ITEM_IDS, (int)dropEventItem.getItemId())) {
                        ItemInstance itemInstance = ItemFunctions.createItem((int)dropEventItem.getItemId(), (long)l3);
                        itemInstance.dropToTheGround(player.getPlayer(), this.getNpc());
                    } else {
                        DropEvent.addItem((Playable)player.getPlayer(), (int)dropEventItem.getItemId(), (long)l3);
                    }
                    if ((l -= l2) > 0L) continue;
                    continue block4;
                }
            }
        }
    }

    public void onDeath(Creature creature, Creature creature2) {
        try {
            if (creature == null || creature2 == null || !DropEvent.a(creature, creature2)) {
                return;
            }
            Player player = creature2.getPlayer();
            if (player == null) {
                return;
            }
            if (!(creature instanceof NpcInstance)) {
                m.error("Actor is not an instance of NpcInstance.");
                return;
            }
            NpcInstance npcInstance = (NpcInstance)creature;
            Party party = player.getParty();
            if (b == null || g == null || a == null || f == null) {
                m.error("DropEvent configuration arrays/maps are not initialized.");
                return;
            }
            int n = npcInstance.getLevel();
            int n2 = npcInstance.getNpcId();
            if (n >= b.length || n >= a.length) {
                m.error("NPC level {} exceeds the configured arrays' length.", (Object)n);
                return;
            }
            if (party != null) {
                this.a((GameObject)npcInstance, party.getPartyMembers(), new HashSet<DropEventItem>(CollectionUtils.join((List[])new List[]{b[n] != null ? Arrays.asList(b[n]) : Collections.emptyList(), g.getOrDefault(n2, Collections.emptyList())})));
            } else {
                this.a((GameObject)npcInstance, Collections.singletonList(player), new HashSet<DropEventItem>(CollectionUtils.join((List[])new List[]{a[n] != null ? Arrays.asList(a[n]) : Collections.emptyList(), f.getOrDefault(n2, Collections.emptyList())})));
            }
        }
        catch (NullPointerException nullPointerException) {
            m.error("NullPointerException in DropEvent: Possible null in NPC, Player, or drop configuration", (Throwable)nullPointerException);
        }
        catch (Exception exception) {
            m.error("Unexpected exception in DropEvent: ", (Throwable)exception);
        }
    }

    private static boolean a(Creature creature, Creature creature2) {
        if (creature == null || !creature.isMonster() || creature2 == null || creature2.getPlayer() == null || creature2.getLevel() - creature.getLevel() >= Config.Event_DropEvent_Level_Penalty) {
            return false;
        }
        return !Config.SERVICE_CAPTCHA_BOT_CHECK || !creature2.getPlayer().getVarB("botCheckPenalty");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (DropEvent.SetActive((String)C, (boolean)true)) {
            if (!R) {
                CharListenerList.addGlobal((Listener)a);
            }
            player.sendMessage("Event 'DropEvent' started.");
        } else {
            player.sendMessage("Event 'DropEvent' already started.");
        }
        R = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (DropEvent.SetActive((String)C, (boolean)false)) {
            if (R) {
                CharListenerList.removeGlobal((Listener)a);
            }
            System.out.println("Event: 'DropEvent' stopped.");
        } else {
            player.sendMessage("Event: 'DropEvent' not started.");
        }
        R = false;
        this.show("admin/events/events.htm", player);
    }

    public void onLoad() {
        DropEvent.y();
        if (DropEvent.isActive()) {
            CharListenerList.addGlobal((Listener)a);
            R = true;
            m.info("Loaded Event: Drop Event [state: activated]");
        } else {
            m.info("Loaded Event: Drop Event [state: deactivated]");
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        if (R) {
            CharListenerList.removeGlobal((Listener)a);
            R = false;
        }
    }

    private static class DropEventItem {
        private final int _itemId;
        private final long W;
        private final long X;
        private final double c;
        private final int[] E;
        private final int bK;
        private final int _maxLvl;

        private DropEventItem(int n, long l, long l2, double d, int[] nArray, int n2, int n3) {
            Arrays.sort(nArray);
            this._itemId = n;
            this.W = l;
            this.X = l2;
            this.c = d;
            this.E = nArray;
            this.bK = n2;
            this._maxLvl = n3;
        }

        public double getChance() {
            return this.c;
        }

        public long getItemCountMin() {
            return this.W;
        }

        public long getItemCountMax() {
            return this.X;
        }

        public long getItemCount() {
            if (this.getItemCountMin() == this.getItemCountMax()) {
                return this.getItemCountMin();
            }
            return Rnd.get((long)this.getItemCountMin(), (long)this.getItemCountMax());
        }

        public int getItemId() {
            return this._itemId;
        }

        public int getMaxLvl() {
            return this._maxLvl;
        }

        public int getMinLvl() {
            return this.bK;
        }

        public int[] getNpcIds() {
            return this.E;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            DropEventItem dropEventItem = (DropEventItem)object;
            return new EqualsBuilder().append(this._itemId, dropEventItem._itemId).append(this.W, dropEventItem.W).append(this.X, dropEventItem.X).append(this.c, dropEventItem.c).append(this.bK, dropEventItem.bK).append(this._maxLvl, dropEventItem._maxLvl).append(this.E, dropEventItem.E).isEquals();
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(this._itemId).append(this.W).append(this.X).append(this.c).append(this.E).append(this.bK).append(this._maxLvl).toHashCode();
        }
    }
}
