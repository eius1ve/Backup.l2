/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.scripts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.MultiValueSet;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExNoticePostArrived;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.NpcSay;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.MapRegionUtils;
import l2.gameserver.utils.NpcUtils;
import l2.gameserver.utils.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Functions {
    public HardReference<Player> self = HardReferences.emptyRef();
    public HardReference<NpcInstance> npc = HardReferences.emptyRef();
    private static final String fS = ",;/";
    private static final String fT = "-:_";

    public static ScheduledFuture<?> executeTask(final Player player, final String string, final String string2, final Object[] objectArray, final Map<String, Object> map, long l) {
        return ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                Functions.callScripts(player, string, string2, objectArray, map);
            }
        }, l);
    }

    public static ScheduledFuture<?> executeTask(String string, String string2, Object[] objectArray, Map<String, Object> map, long l) {
        return Functions.executeTask(null, string, string2, objectArray, map, l);
    }

    public static ScheduledFuture<?> executeTask(Player player, String string, String string2, Object[] objectArray, long l) {
        return Functions.executeTask(player, string, string2, objectArray, null, l);
    }

    public static ScheduledFuture<?> executeTask(String string, String string2, Object[] objectArray, long l) {
        return Functions.executeTask(string, string2, objectArray, null, l);
    }

    public static Object callScripts(String string, String string2, Object[] objectArray) {
        return Functions.callScripts(string, string2, objectArray, null);
    }

    public static Object callScripts(String string, String string2, Object[] objectArray, Map<String, Object> map) {
        return Functions.callScripts(null, string, string2, objectArray, map);
    }

    public static Object callScripts(Player player, String string, String string2, Object[] objectArray, Map<String, Object> map) {
        return Scripts.getInstance().callScripts(player, string, string2, objectArray, map);
    }

    public static List<Pair<ItemTemplate, Long>> parseItemIdAmountList(String string) {
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, fS);
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            StringTokenizer stringTokenizer2 = new StringTokenizer(string2, fT);
            int n = Integer.parseInt(stringTokenizer2.nextToken());
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
            long l = Long.parseLong(stringTokenizer2.nextToken());
            arrayList.add(Pair.of((Object)itemTemplate, (Object)l));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public void show(String string, Player player) {
        Functions.show(string, player, this.getNpc(), new Object[0]);
    }

    public static void show(String string, Player player, NpcInstance npcInstance, Object ... objectArray) {
        if (string == null || player == null) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        if (string.endsWith(".html") || string.endsWith(".htm")) {
            npcHtmlMessage.setFile(string);
        } else {
            npcHtmlMessage.setHtml(Strings.bbParse(string));
        }
        if (objectArray != null && objectArray.length % 2 == 0) {
            int n = 0;
            while (n < objectArray.length) {
                npcHtmlMessage.replace(String.valueOf(objectArray[n]), String.valueOf(objectArray[n + 1]));
                n = 2;
            }
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public static void show(CustomMessage customMessage, Player player) {
        Functions.show(customMessage.toString(), player, null, new Object[0]);
    }

    public static void sendMessage(String string, Player player) {
        player.sendMessage(string);
    }

    public static void sendMessage(CustomMessage customMessage, Player player) {
        player.sendMessage(customMessage);
    }

    public static void npcSayInRange(NpcInstance npcInstance, String string, int n) {
        Functions.npcSayInRange(npcInstance, n, NpcString.NONE, string);
    }

    public static void npcSayInRange(NpcInstance npcInstance, int n, NpcString npcString, String ... stringArray) {
        if (npcInstance == null) {
            return;
        }
        NpcSay npcSay = new NpcSay(npcInstance, ChatType.NPC_NORMAL, npcString, stringArray);
        for (Player player : World.getAroundPlayers(npcInstance, n, Math.max(n / 2, 200))) {
            if (npcInstance.getReflection() != player.getReflection()) continue;
            player.sendPacket((IStaticPacket)npcSay);
        }
    }

    public static void npcSay(NpcInstance npcInstance, String string) {
        Functions.npcSayInRange(npcInstance, string, 1500);
    }

    public static void npcSay(NpcInstance npcInstance, NpcString npcString, String ... stringArray) {
        Functions.npcSayInRange(npcInstance, 1500, npcString, stringArray);
    }

    public static void npcSayInRangeCustomMessage(NpcInstance npcInstance, int n, String string, Object ... objectArray) {
        if (npcInstance == null) {
            return;
        }
        for (Player player : World.getAroundPlayers(npcInstance, n, Math.max(n / 2, 200))) {
            if (npcInstance.getReflection() != player.getReflection()) continue;
            player.sendPacket((IStaticPacket)new NpcSay(npcInstance, ChatType.NPC_NORMAL, new CustomMessage(string, player, objectArray).toString()));
        }
    }

    public static void npcSayInRangeCustomMessage(NpcInstance npcInstance, ChatType chatType, int n, String string, Object ... objectArray) {
        if (npcInstance == null) {
            return;
        }
        for (Player player : World.getAroundPlayers(npcInstance, n, Math.max(n / 2, 200))) {
            if (npcInstance.getReflection() != player.getReflection()) continue;
            player.sendPacket((IStaticPacket)new NpcSay(npcInstance, chatType, new CustomMessage(string, player, objectArray).toString()));
        }
    }

    public static void npcSayCustomMessage(NpcInstance npcInstance, ChatType chatType, String string, Object ... objectArray) {
        Functions.npcSayInRangeCustomMessage(npcInstance, chatType, 1500, string, objectArray);
    }

    public static void npcSayCustomMessage(NpcInstance npcInstance, String string, Object ... objectArray) {
        Functions.npcSayCustomMessage(npcInstance, ChatType.NPC_NORMAL, string, objectArray);
    }

    public static void npcSayToPlayer(NpcInstance npcInstance, Player player, String string) {
        Functions.npcSayToPlayer(npcInstance, player, NpcString.NONE, string);
    }

    public static void npcSayToPlayer(NpcInstance npcInstance, Player player, NpcString npcString, String ... stringArray) {
        if (npcInstance == null) {
            return;
        }
        player.sendPacket((IStaticPacket)new NpcSay(npcInstance, ChatType.TELL, npcString, stringArray));
    }

    public static void npcShout(NpcInstance npcInstance, String string) {
        Functions.npcShout(npcInstance, NpcString.NONE, string);
    }

    public static void npcShout(NpcInstance npcInstance, NpcString npcString, String ... stringArray) {
        if (npcInstance == null) {
            return;
        }
        NpcSay npcSay = new NpcSay(npcInstance, ChatType.SHOUT, npcString, stringArray);
        int n = MapRegionUtils.regionX(npcInstance);
        int n2 = MapRegionUtils.regionY(npcInstance);
        int n3 = Config.SHOUT_OFFSET;
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player.getReflection() != npcInstance.getReflection()) continue;
            int n4 = MapRegionUtils.regionX(player);
            int n5 = MapRegionUtils.regionY(player);
            if (n4 < n - n3 || n4 > n + n3 || n5 < n2 - n3 || n5 > n2 + n3) continue;
            player.sendPacket((IStaticPacket)npcSay);
        }
    }

    public static void npcShoutCustomMessage(NpcInstance npcInstance, String string, Object ... objectArray) {
        if (npcInstance == null) {
            return;
        }
        int n = MapRegionUtils.regionX(npcInstance);
        int n2 = MapRegionUtils.regionY(npcInstance);
        int n3 = Config.SHOUT_OFFSET;
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player.getReflection() != npcInstance.getReflection()) continue;
            int n4 = MapRegionUtils.regionX(player);
            int n5 = MapRegionUtils.regionY(player);
            if ((n4 < n - n3 || n4 > n + n3 || n5 < n2 - n3 || n5 > n2 + n3) && !npcInstance.isInRange(player, (long)Config.CHAT_RANGE)) continue;
            player.sendPacket((IStaticPacket)new NpcSay(npcInstance, ChatType.SHOUT, new CustomMessage(string, player, objectArray).toString()));
        }
    }

    public static void npcSay(NpcInstance npcInstance, NpcString npcString, ChatType chatType, int n, String ... stringArray) {
        if (npcInstance == null) {
            return;
        }
        for (Player player : World.getAroundPlayers(npcInstance, n, Math.max(n / 2, 200))) {
            if (player.getReflection() != npcInstance.getReflection()) continue;
            player.sendPacket((IStaticPacket)new NpcSay(npcInstance, chatType, npcString, stringArray));
        }
    }

    public static void addItem(Playable playable, int n, long l) {
        ItemFunctions.addItem(playable, n, l, true);
    }

    public static long getItemCount(Playable playable, int n) {
        return ItemFunctions.getItemCount(playable, n);
    }

    public static long removeItem(Playable playable, int n, long l) {
        return ItemFunctions.removeItem(playable, n, l, true);
    }

    public static long removeItem(Playable playable, int n, long l, boolean bl) {
        return ItemFunctions.removeItem(playable, n, l, true, bl);
    }

    public static boolean ride(Player player, int n) {
        if (player.isMounted()) {
            player.setMount(0, 0, 0);
        }
        if (player.getPet() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_HAVE_A_PET);
            return false;
        }
        player.setMount(n, 0, 0);
        return true;
    }

    protected static void giveLimitedItem(Player player, String string, int n, int n2, long l, boolean bl) {
        long l2;
        Map<Integer, String> map;
        long l3 = System.currentTimeMillis();
        String string2 = player.getVar(string);
        if (bl && (map = player.getAccountChars()) != null) {
            long l4 = 0L;
            for (int n3 : map.keySet()) {
                long l5;
                String string3 = CharacterVariablesDAO.getVarForPlayer(n3, string);
                if (string3 == null || (l5 = Long.parseLong(string3)) <= l4) continue;
                l4 = l5;
            }
            if (l4 > 0L) {
                string2 = String.valueOf(l4);
            }
        }
        if ((l2 = string2 != null ? l3 - Long.parseLong(string2) : l) >= l) {
            Functions.addItem(player, n, n2);
            player.setVar(string, String.valueOf(l3), -1L);
        } else {
            int n4 = (int)(l - l2) / 3600000;
            int n5 = (int)(l - l2) % 3600000 / 60000;
            if (n4 > 0) {
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S1_HOURSS_AND_S2_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN).addNumber(n4)).addNumber(n5));
            } else if (n5 > 0) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_ARE_S1_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN).addNumber(n5));
            } else {
                Functions.addItem(player, n, n2);
                player.setVar(string, String.valueOf(l3), -1L);
            }
        }
    }

    protected static void buyLimitedItem(Player player, String string, int n, int n2, int n3, int n4, long l, boolean bl) {
        long l2;
        Map<Integer, String> map;
        long l3 = System.currentTimeMillis();
        String string2 = player.getVar(string);
        if (bl && (map = player.getAccountChars()) != null) {
            long l4 = 0L;
            for (int n5 : map.keySet()) {
                long l5;
                String string3 = CharacterVariablesDAO.getVarForPlayer(n5, string);
                if (string3 == null || (l5 = Long.parseLong(string3)) <= l4) continue;
                l4 = l5;
            }
            if (l4 > 0L) {
                string2 = String.valueOf(l4);
            }
        }
        if ((l2 = string2 != null ? l3 - Long.parseLong(string2) : l) >= l) {
            if (player.getInventory().destroyItemByItemId(n3, n4)) {
                Functions.addItem(player, n, n2);
                player.setVar(string, String.valueOf(l3), -1L);
            } else {
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_UNITS_OF_THE_ITEM_S1_ISARE_REQUIRED).addItemName(n3)).addNumber(n4));
            }
        } else {
            int n6 = (int)(l - l2) / 3600000;
            int n7 = (int)(l - l2) % 3600000 / 60000;
            if (n6 > 0) {
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S1_HOURSS_AND_S2_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN).addNumber(n6)).addNumber(n7));
            } else if (n7 > 0) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_ARE_S1_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN).addNumber(n7));
            } else if (player.getInventory().destroyItemByItemId(n3, n4)) {
                Functions.addItem(player, n, n2);
                player.setVar(string, String.valueOf(l3), -1L);
            } else {
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_UNITS_OF_THE_ITEM_S1_ISARE_REQUIRED).addItemName(n3)).addNumber(n4));
            }
        }
    }

    public static boolean giveItemOnce(Player player, String string, int n, long l, boolean bl, boolean bl2) {
        Object object;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (bl && (object = player.getAccountChars()) != null && !object.isEmpty()) {
            Iterator iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                String string2;
                int n2 = (Integer)iterator.next();
                if (n2 == player.getObjectId() || StringUtils.isBlank((CharSequence)(string2 = CharacterVariablesDAO.getVarForPlayer(n2, string)))) continue;
                arrayList.add(string2);
            }
        }
        if (!StringUtils.isBlank((CharSequence)(object = player.getVar(string)))) {
            arrayList.add(object);
        }
        boolean bl3 = false;
        block1: for (String string2 : arrayList) {
            String[] stringArray;
            for (String string3 : stringArray = StringUtils.split((String)string2, (char)';')) {
                int n2;
                if (StringUtils.isBlank((CharSequence)string3) || (n2 = Integer.parseInt(string3)) != n && (!bl2 || n2 == 0)) continue;
                bl3 = true;
                continue block1;
            }
        }
        if (!bl3) {
            Functions.addItem(player, n, l);
            String string4 = player.getVar(string);
            player.setVar(string, !StringUtils.isBlank((CharSequence)string4) ? String.format("%s;%d", string4, n) : String.valueOf(n), -1L);
            return true;
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CANNOT_RE_RECEIVE_ITEMS_FOR_THE_SAME_ACCOUNT));
        return false;
    }

    public static void unRide(Player player) {
        if (player.isMounted()) {
            player.setMount(0, 0, 0);
        }
    }

    public static void unSummonPet(Player player, boolean bl) {
        Summon summon = player.getPet();
        if (summon == null) {
            return;
        }
        if (summon.isPet() || !bl) {
            summon.unSummon();
        }
    }

    @Deprecated
    public static NpcInstance spawn(Location location, int n) {
        return Functions.spawn(location, n, ReflectionManager.DEFAULT);
    }

    @Deprecated
    public static NpcInstance spawn(Location location, int n, Reflection reflection) {
        return NpcUtils.spawnSingle(n, location, reflection, 0L);
    }

    public Player getSelf() {
        return this.self.get();
    }

    public NpcInstance getNpc() {
        return this.npc.get();
    }

    @Deprecated
    public static void SpawnNPCs(int n, int[][] nArray, List<SimpleSpawner> list) {
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n);
        if (npcTemplate == null) {
            System.out.println("WARNING! Functions.SpawnNPCs template is null for npc: " + n);
            Thread.dumpStack();
            return;
        }
        for (int[] nArray2 : nArray) {
            SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
            Location location = new Location(nArray2[0], nArray2[1], nArray2[2]);
            if (nArray2.length > 3) {
                location.setH(nArray2[3]);
            }
            simpleSpawner.setLoc(location);
            simpleSpawner.setAmount(1);
            simpleSpawner.setRespawnDelay(0);
            simpleSpawner.init();
            if (list == null) continue;
            list.add(simpleSpawner);
        }
    }

    public static void deSpawnNPCs(List<SimpleSpawner> list) {
        for (SimpleSpawner simpleSpawner : list) {
            simpleSpawner.deleteAll();
        }
        list.clear();
    }

    public static void teleportParty(Party party, Location location, int n) {
        for (Player player : party.getPartyMembers()) {
            player.teleToLocation(Location.findPointToStay(location, n, player.getGeoIndex()));
        }
    }

    public static boolean IsActive(String string) {
        return ServerVariables.getString(string, "off").equalsIgnoreCase("on");
    }

    public static boolean SetActive(String string, boolean bl) {
        if (bl == Functions.IsActive(string)) {
            return false;
        }
        if (bl) {
            ServerVariables.set(string, "on");
        } else {
            ServerVariables.unset(string);
        }
        return true;
    }

    public static boolean simpleCheckDrop(Creature creature, Creature creature2) {
        if (creature == null || !creature.isMonster() || creature2 == null || creature2.getPlayer() == null || creature2.getLevel() - creature.getLevel() >= 9) {
            return false;
        }
        return !Config.SERVICE_CAPTCHA_BOT_CHECK || !creature2.getPlayer().getVarB("botCheckPenalty");
    }

    public static boolean CheckPlayerConditions(Player player) {
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_AUGMENT_ITEMS_WHILE_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP_IS_IN_OPERATION);
            return false;
        }
        if (player.isInTrade()) {
            player.sendActionFailed();
            return false;
        }
        if (player.isDead()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_AUGMENT_ITEMS_WHILE_DEAD);
            return false;
        }
        if (player.isParalyzed()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_AUGMENT_ITEMS_WHILE_PARALYZED);
            return false;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_AUGMENT_ITEMS_WHILE_FISHING);
            return false;
        }
        if (player.isSitting()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_AUGMENT_ITEMS_WHILE_SITTING_DOWN);
            return false;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return false;
        }
        return true;
    }

    public static boolean isPvPEventStarted() {
        if (((Boolean)Functions.callScripts("events.TvT.TvT", "isRunned", new Object[0])).booleanValue()) {
            return true;
        }
        return (Boolean)Functions.callScripts("events.lastHero.LastHero", "isRunned", new Object[0]) != false;
    }

    public static MultiValueSet<String> parseParams(String string) {
        MultiValueSet<String> multiValueSet = new MultiValueSet<String>();
        char[] cArray = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = null;
        String string3 = null;
        for (int i = 0; i < cArray.length; ++i) {
            char c = cArray[i];
            if (c == '=' && string2 == null) {
                string2 = stringBuilder.toString();
                stringBuilder.setLength(0);
                continue;
            }
            if (c == '&') {
                string3 = stringBuilder.toString();
                multiValueSet.put(string2, string3);
                stringBuilder.setLength(0);
                string2 = null;
                string3 = null;
                continue;
            }
            stringBuilder.append(c);
        }
        if (string2 != null) {
            string3 = stringBuilder.toString();
            multiValueSet.put(string2, string3);
        }
        return multiValueSet;
    }

    public static void sendDebugMessage(Player player, String string) {
        if (!player.isGM()) {
            return;
        }
        player.sendMessage(string);
    }

    public static void sendSystemMail(Player player, String string, String string2, Map<Integer, Long> map) {
        if (player == null || !player.isOnline()) {
            return;
        }
        if (string == null) {
            return;
        }
        if (map.keySet().size() > 8) {
            return;
        }
        Mail mail = new Mail();
        mail.setSenderId(1);
        mail.setSenderName("Admin");
        mail.setReceiverId(player.getObjectId());
        mail.setReceiverName(player.getName());
        mail.setTopic(string);
        mail.setBody(string2);
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            ItemInstance itemInstance = ItemFunctions.createItem(entry.getKey());
            itemInstance.setLocation(ItemInstance.ItemLocation.MAIL);
            itemInstance.setCount(entry.getValue());
            itemInstance.setOwnerId(player.getObjectId());
            itemInstance.save();
            mail.addAttachment(itemInstance);
        }
        mail.setType(Mail.SenderType.NEWS_INFORMER);
        mail.setUnread(true);
        mail.setExpireTime(2592000 + (int)(System.currentTimeMillis() / 1000L));
        mail.save();
        player.sendPacket((IStaticPacket)ExNoticePostArrived.STATIC_TRUE);
        player.sendPacket((IStaticPacket)SystemMsg.THE_MAIL_HAS_ARRIVED);
    }

    public static final String truncateHtmlTagsSpaces(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        StringBuilder stringBuilder2 = new StringBuilder();
        boolean bl = false;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '<') {
                bl = false;
                if (stringBuilder2.length() > 0) {
                    stringBuilder.append(StringUtils.trim((String)stringBuilder2.toString()));
                    stringBuilder2.setLength(0);
                }
            }
            if (!bl) {
                stringBuilder.append(c);
            } else {
                stringBuilder2.append(c);
            }
            if (c != '>') continue;
            bl = true;
        }
        if (stringBuilder2.length() > 0) {
            stringBuilder.append(StringUtils.trim((String)stringBuilder2.toString()));
            stringBuilder2.setLength(0);
        }
        return stringBuilder.toString();
    }

    public static Map<String, ScheduledFuture<?>> ScheduleTimeStarts(Runnable runnable, String[] stringArray) {
        HashMap hashMap = new HashMap();
        if (runnable == null || stringArray == null || stringArray.length == 0) {
            return hashMap;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        for (String string : stringArray) {
            long l;
            String[] stringArray2 = string.trim().split(":");
            int n = Integer.parseInt(stringArray2[0].trim());
            int n2 = Integer.parseInt(stringArray2[1].trim());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(11, n);
            calendar2.set(12, n2);
            if (calendar2.getTimeInMillis() < calendar.getTimeInMillis()) {
                calendar2.add(5, 1);
            }
            if ((l = calendar2.getTimeInMillis() - calendar.getTimeInMillis()) <= 0L) continue;
            hashMap.put(simpleDateFormat.format(calendar2.getTime()), ThreadPoolManager.getInstance().schedule(runnable, l));
        }
        return hashMap;
    }
}
