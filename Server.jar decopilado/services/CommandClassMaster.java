/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.listener.actor.player.OnGainExpSpListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.scripts.Scripts
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.listener.actor.player.OnGainExpSpListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CommandClassMaster
extends Functions
implements IVoicedCommandHandler,
OnGainExpSpListener,
ScriptFile {
    private static final CommandClassMaster a = new CommandClassMaster();
    private static final String hx = "cmd_class_master_show_cnt";
    private static Map<ClassId, List<ClassMasterPath>> cn = Collections.emptyMap();
    private static String[] a = ArrayUtils.EMPTY_STRING_ARRAY;

    public static CommandClassMaster getInstance() {
        return a;
    }

    private static List<ClassMasterPath> a(ClassId classId) {
        return cn.getOrDefault(classId, Collections.emptyList());
    }

    public static String getMinPlayerLevelForClassIdMessageAddress(int n) {
        switch (n) {
            case 2: {
                return "ClassMaster.Need20Level";
            }
            case 3: {
                return "ClassMaster.Need40Level";
            }
            case 4: {
                return "ClassMaster.Need76Level";
            }
        }
        return "ClassMaster.NothingToUp";
    }

    private static List<Pair<Integer, Long>> f(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
        LinkedList<Pair> linkedList = new LinkedList<Pair>();
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.isEmpty()) continue;
            int n = string2.indexOf(":");
            if (n < 0) {
                throw new RuntimeException("Can't parse items \"" + string + "\"");
            }
            int n2 = Integer.parseInt(string2.substring(0, n).trim());
            long l = Long.parseLong(string2.substring(n + 1).trim());
            linkedList.add(Pair.of((Object)n2, (Object)l));
        }
        return Collections.unmodifiableList(linkedList);
    }

    private boolean a(Player player, boolean bl) {
        if (player == null || player.isLogoutStarted() || player.isOutOfControl() || player.isDead() || player.isInDuel() || player.isAlikeDead() || player.isOlyParticipant() || player.isFlying() || player.isSitting() || player.getTeam() != TeamType.NONE || player.isInStoreMode() || player.entering) {
            if (bl && player != null && !player.entering) {
                player.sendMessage(new CustomMessage("common.TryLater", player, new Object[0]));
            }
            return false;
        }
        return true;
    }

    private void a(Player player, List<ClassMasterPath> list) {
        if (!this.a(player, false) || list == null || list.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < list.size(); ++i) {
            String string;
            ClassMasterPath classMasterPath = list.get(i);
            if (classMasterPath.getAvailableClassIds().isEmpty()) continue;
            String string2 = StringHolder.getInstance().getNotNull(player, "scripts.services.CommandClassMaster.pathHtml");
            StringBuilder stringBuilder2 = new StringBuilder();
            for (ClassId classId : classMasterPath.getAvailableClassIds()) {
                stringBuilder2.append(new CustomMessage("CommandClassMaster.htmlButton", player, new Object[0]).addNumber((long)i).addNumber((long)classId.getId()).addString(HtmlUtils.htmlClassName((int)classId.getId(), (Player)player)));
            }
            string2 = string2.replace("%class_master_list%", stringBuilder2.toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            for (Pair<Integer, Long> pair : classMasterPath.getPrice()) {
                Pair<Integer, Long> pair2 = ItemHolder.getInstance().getTemplate(((Integer)pair.getKey()).intValue());
                string = StringHolder.getInstance().getNotNull(player, "scripts.services.CommandClassMaster.requiredItem");
                string = string.replace("%item_id%", String.valueOf(pair.getKey()));
                string = string.replace("%item_name%", pair2.getName());
                string = string.replace("%item_count%", String.valueOf(pair.getValue()));
                stringBuilder3.append(string);
            }
            string2 = string2.replace("%required_items_list%", stringBuilder3.toString());
            StringBuilder object22 = new StringBuilder();
            for (Pair<Integer, Long> pair2 : classMasterPath.getReward()) {
                string = ItemHolder.getInstance().getTemplate(((Integer)pair2.getKey()).intValue());
                String string3 = StringHolder.getInstance().getNotNull(player, "scripts.services.CommandClassMaster.rewardItem");
                string3 = string3.replace("%item_id%", String.valueOf(pair2.getKey()));
                string3 = string3.replace("%item_name%", string.getName());
                string3 = string3.replace("%item_count%", String.valueOf(pair2.getValue()));
                object22.append(string3);
            }
            string2 = string2.replace("%reward_items_list%", object22.length() > 0 ? object22.toString() : " ");
            stringBuilder.append(string2);
            bl = true;
        }
        if (!bl) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/command_class_master.htm");
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void ar(Player player) {
        List<ClassMasterPath> list = CommandClassMaster.a(player.getClassId());
        if (list == null || list.isEmpty() || !list.stream().map(ClassMasterPath::getAvailableClassIds).flatMap(Collection::stream).findAny().isPresent()) {
            return;
        }
        this.a(player, list);
    }

    public void classMaster() {
        this.classMaster(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public void classMaster(String ... stringArray) {
        int n;
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.COMMAND_CLASS_MASTER_ENABLED) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        if (!this.a(player, true)) {
            return;
        }
        ClassId classId = player.getClassId();
        List<ClassMasterPath> list = CommandClassMaster.a(classId);
        if (list == null || list.isEmpty()) {
            CommandClassMaster.show((CustomMessage)new CustomMessage("ClassMaster.NothingToUp", player, new Object[0]), (Player)player);
            return;
        }
        if (list.stream().mapToInt(ClassMasterPath::getMinPlayerLevel).min().orElse(Integer.MAX_VALUE) > player.getLevel()) {
            player.sendMessage(new CustomMessage(CommandClassMaster.getMinPlayerLevelForClassIdMessageAddress(classId.getLevel() + 1), player, new Object[0]));
            return;
        }
        if (stringArray.length > 1) {
            ClassMasterPath classMasterPath;
            n = Integer.parseInt(stringArray[0]);
            int n2 = Integer.parseInt(stringArray[1]);
            if (list == null || list.isEmpty() || n < 0 || n >= list.size() || (classMasterPath = list.get(n)) == null || classMasterPath.getAvailableClassIds() == null || classMasterPath.getAvailableClassIds().isEmpty()) {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/npc_class_master_nothingtoup.htm"));
                return;
            }
            ClassId classId2 = null;
            for (ClassId classId3 : classMasterPath.getAvailableClassIds()) {
                if (classId3.getId() != n2) continue;
                classId2 = classId3;
                break;
            }
            if (classId2 == null) {
                return;
            }
            for (Pair pair : classMasterPath.getPrice()) {
                if (ItemFunctions.getItemCount((Playable)player, (int)((Integer)pair.getKey())) >= (Long)pair.getValue()) continue;
                if ((Integer)pair.getKey() == 57) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                }
                return;
            }
            long l = 0L;
            long l2 = 0L;
            for (Pair<Integer, Long> pair : classMasterPath.getReward()) {
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(((Integer)pair.getKey()).intValue());
                l += (long)itemTemplate.getWeight() * (Long)pair.getValue();
                l2 += itemTemplate.isStackable() ? 1L : (Long)pair.getValue();
            }
            if (!player.getInventory().validateWeight(l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                return;
            }
            if (!player.getInventory().validateCapacity(l2)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                return;
            }
            for (Pair<Integer, Long> pair : classMasterPath.getPrice()) {
                if (ItemFunctions.removeItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true) >= (Long)pair.getValue()) continue;
                return;
            }
            this.a(player, classId2);
            for (Pair<Integer, Long> pair : classMasterPath.getReward()) {
                ItemFunctions.addItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true);
            }
            Log.service((String)"CommandClassMaster", (Player)player, (String)(" got a classId " + classId2));
        }
        if ((n = CommandClassMaster.o(player.getClassId().getLevel() + 1)) > 0 && n <= player.getLevel()) {
            this.ar(player);
        } else if (n < 0) {
            CommandClassMaster.sendMessage((CustomMessage)new CustomMessage("ClassMaster.NothingToUp", player, new Object[0]), (Player)player);
        } else if (n <= 20) {
            CommandClassMaster.sendMessage((CustomMessage)new CustomMessage("ClassMaster.Need20Level", player, new Object[0]), (Player)player);
        } else if (n <= 40) {
            CommandClassMaster.sendMessage((CustomMessage)new CustomMessage("ClassMaster.Need40Level", player, new Object[0]), (Player)player);
        } else if (n <= 76) {
            CommandClassMaster.sendMessage((CustomMessage)new CustomMessage("ClassMaster.Need76Level", player, new Object[0]), (Player)player);
        }
    }

    private void a(Player player, ClassId classId) {
        if (player.getClassId().getLevel() == 3) {
            player.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS__YOUVE_COMPLETED_YOUR_THIRDCLASS_TRANSFER_QUEST);
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS__YOUVE_COMPLETED_A_CLASS_TRANSFER);
        }
        player.setClassId(classId.getId(), false, false);
        player.broadcastCharInfo();
        player.broadcastPacket(new L2GameServerPacket[]{new SocialAction(player.getObjectId(), 3)});
        player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 4339, 1, 0, 0L)});
    }

    public void onGainExpSp(Player player, long l, long l2) {
        if (!Config.COMMAND_CLASS_MASTER_ENABLED || Config.COMMAND_CLASS_POPUP_LIMIT == 0) {
            return;
        }
        if (!this.a(player, false)) {
            return;
        }
        List<ClassMasterPath> list = CommandClassMaster.a(player.getClassId());
        if (list == null || list.isEmpty() || list.stream().mapToInt(ClassMasterPath::getMinPlayerLevel).min().orElse(Integer.MAX_VALUE) > player.getLevel()) {
            return;
        }
        if (Config.COMMAND_CLASS_POPUP_LIMIT == -1) {
            this.ar(player);
        } else if (Config.COMMAND_CLASS_POPUP_LIMIT > 0) {
            int n = player.getVarInt(hx, 0);
            int n2 = n & 0xFF;
            int n3 = n >> 8;
            if (n2 != player.getActiveClassId() || n3 < Config.COMMAND_CLASS_POPUP_LIMIT) {
                player.setVar(hx, n3 + 1 << 8 | player.getActiveClassId() & 0xFF, -1L);
                this.ar(player);
            }
        }
    }

    public void onLoad() {
        if (Config.COMMAND_CLASS_MASTER_ENABLED) {
            cn = CommandClassMaster.parseConfig(Config.COMMAND_CLASS_MASTER_CLASSES);
            if (Config.COMMAND_CLASS_POPUP_LIMIT != 0) {
                PlayerListenerList.addGlobal((Listener)CommandClassMaster.getInstance());
            }
            if (Config.COMMAND_CLASS_MASTER_VOICE_COMMANDS.length > 0) {
                a = Config.COMMAND_CLASS_MASTER_VOICE_COMMANDS;
                VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)a);
            }
        }
    }

    public void onReload() {
        if (Config.COMMAND_CLASS_MASTER_ENABLED) {
            cn = CommandClassMaster.parseConfig(Config.COMMAND_CLASS_MASTER_CLASSES);
        }
    }

    public void onShutdown() {
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        for (String string3 : a) {
            if (string3 == null || string3.isEmpty() || !string3.equalsIgnoreCase(string)) continue;
            Scripts.getInstance().callScripts(player, CommandClassMaster.class.getName(), "classMaster");
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.COMMAND_CLASS_MASTER_ENABLED) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return Config.COMMAND_CLASS_MASTER_VOICE_COMMANDS;
    }

    public static Map<ClassId, List<ClassMasterPath>> parseConfig(String string) {
        if (StringUtils.isEmpty((CharSequence)string)) {
            return Collections.emptyMap();
        }
        TreeMap<Object, List> treeMap = new TreeMap<Object, List>();
        StringTokenizer stringTokenizer = new StringTokenizer(string.trim(), ";");
        while (stringTokenizer.hasMoreTokens()) {
            int n;
            String string2 = stringTokenizer.nextToken().trim();
            int n2 = string2.indexOf("-");
            List<Pair<Integer, Long>> list3 = Collections.emptyList();
            List<Pair<Integer, Long>> list4 = Collections.emptyList();
            if (n2 < 0) {
                n = Integer.parseInt(string2.trim());
            } else {
                n = Integer.parseInt(string2.substring(0, n2).trim());
                String string3 = string2.substring(n2 + 1).trim();
                int n3 = string3.indexOf("/");
                if (n3 < 0) {
                    list3 = CommandClassMaster.f(string3);
                } else {
                    list3 = CommandClassMaster.f(string3.substring(0, n3).trim());
                    list4 = CommandClassMaster.f(string3.substring(n3 + 1).trim());
                }
            }
            for (ClassId classId : ClassId.VALUES) {
                if (classId.getLevel() != n) continue;
                LinkedList<ClassId> linkedList = new LinkedList<ClassId>();
                for (ClassId classId2 : ClassId.VALUES) {
                    if (!classId2.childOf(classId) || classId.getLevel() + 1 != classId2.getLevel()) continue;
                    linkedList.add(classId2);
                }
                treeMap.merge(classId, Collections.singletonList(new ClassMasterPath(linkedList, classId, list3, list4)), (list, list2) -> new ArrayList<ClassMasterPath>(){
                    {
                        this.addAll(list);
                        this.addAll(list2);
                    }
                });
            }
        }
        return Collections.unmodifiableMap(treeMap);
    }

    private static int o(int n) {
        switch (n) {
            case 1: {
                return 1;
            }
            case 2: {
                return 20;
            }
            case 3: {
                return 40;
            }
            case 4: {
                return 76;
            }
        }
        return -1;
    }

    public static class ClassMasterPath {
        private final List<ClassId> dP;
        private final ClassId b;
        private final List<Pair<Integer, Long>> dQ;
        private final List<Pair<Integer, Long>> dR;

        public ClassMasterPath(List<ClassId> list, ClassId classId, List<Pair<Integer, Long>> list2, List<Pair<Integer, Long>> list3) {
            this.dP = list;
            this.b = classId;
            this.dQ = list2;
            this.dR = list3;
        }

        public int getMinPlayerLevel() {
            return CommandClassMaster.o(this.b.getLevel() + 1);
        }

        public List<ClassId> getAvailableClassIds() {
            return this.dP;
        }

        public ClassId getFromClassId() {
            return this.b;
        }

        public List<Pair<Integer, Long>> getPrice() {
            return this.dQ;
        }

        public List<Pair<Integer, Long>> getReward() {
            return this.dR;
        }
    }
}
