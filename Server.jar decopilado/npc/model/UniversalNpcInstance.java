/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SubClass
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.ClassType
 *  l2.gameserver.model.base.PlayerClass
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.entity.oly.ParticipantPool
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.HennaEquipList
 *  l2.gameserver.network.l2.s2c.HennaUnequipList
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.PackageToList
 *  l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Util
 *  l2.gameserver.utils.WarehouseFunctions
 */
package npc.model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import l2.gameserver.Config;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SubClass;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.ClassType;
import l2.gameserver.model.base.PlayerClass;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.HennaEquipList;
import l2.gameserver.network.l2.s2c.HennaUnequipList;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.PackageToList;
import l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Util;
import l2.gameserver.utils.WarehouseFunctions;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UniversalNpcInstance
extends MerchantInstance {
    public UniversalNpcInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    private boolean l(Player player) {
        if (player.getEnchantScroll() != null) {
            Log.add((String)("Player " + player.getName() + " trying to use enchant exploit[CastleWarehouse], ban this player!"), (String)"illegal-actions");
            player.kick();
            return false;
        }
        return true;
    }

    public void onBypassFeedback(Player player, String string) {
        if (!UniversalNpcInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equals("Draw")) {
            player.sendPacket((IStaticPacket)new HennaEquipList(player));
            return;
        }
        if (string.equals("RemoveList")) {
            player.sendPacket((IStaticPacket)new HennaUnequipList(player));
            return;
        }
        if (string.equalsIgnoreCase("deposit_items")) {
            player.sendPacket((IStaticPacket)new PackageToList(player));
            return;
        }
        if (string.equalsIgnoreCase("withdraw_items")) {
            WarehouseFunctions.showFreightWindow((Player)player);
            return;
        }
        if ((string.startsWith("Withdraw") || string.startsWith("Deposit")) && this.l(player)) {
            if (string.startsWith("WithdrawP")) {
                int n = Integer.parseInt(string.substring(10));
                if (n == 99) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                    npcHtmlMessage.setFile("warehouse/personal.htm");
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                } else {
                    WarehouseFunctions.showRetrieveWindow((Player)player, (int)n);
                }
            } else if (string.equals("DepositP")) {
                WarehouseFunctions.showDepositWindow((Player)player);
            } else if (string.startsWith("WithdrawC")) {
                int n = Integer.parseInt(string.substring(10));
                if (n == 99) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                    npcHtmlMessage.setFile("warehouse/clan.htm");
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                } else {
                    WarehouseFunctions.showWithdrawWindowClan((Player)player, (int)n);
                }
            } else if (string.equals("DepositC")) {
                WarehouseFunctions.showDepositWindowClan((Player)player);
            }
            return;
        }
        if (string.equalsIgnoreCase("SkillList")) {
            this.showSkillList(player);
        } else if (string.startsWith("AltSkillList")) {
            int n = Integer.parseInt(string.substring(13).trim());
            this.showSkillList(player, ClassId.VALUES[n]);
        } else if (string.equalsIgnoreCase("FishingSkillList")) {
            UniversalNpcInstance.showFishingSkillList((Player)player);
        } else if (string.equalsIgnoreCase("SkillEnchantList")) {
            this.showSkillEnchantList(player);
        } else if (string.equals("create_clan_check")) {
            if (player.getLevel() < Config.CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE) {
                this.showChatWindow(player, "villagemaster/pl002.htm", new Object[0]);
            } else if (player.isClanLeader()) {
                this.showChatWindow(player, "villagemaster/pl003.htm", new Object[0]);
            } else if (player.getClan() != null) {
                this.showChatWindow(player, "villagemaster/pl004.htm", new Object[0]);
            } else {
                this.showChatWindow(player, "villagemaster/pl005.htm", new Object[0]);
            }
        } else if (string.startsWith("create_clan") && string.length() > 12) {
            String string2 = string.substring(12);
            this.a((NpcInstance)this, player, string2);
        } else if (string.startsWith("Subclass")) {
            if (player.getPet() != null) {
                player.sendPacket((IStaticPacket)SystemMsg.A_SUBCLASS_MAY_NOT_BE_CREATED_OR_CHANGED_WHILE_A_SERVITOR_OR_PET_IS_SUMMONED);
                return;
            }
            if (player.isActionsDisabled() || player.getTransformation() != 0 || player.isCursedWeaponEquipped()) {
                player.sendPacket((IStaticPacket)SystemMsg.SUBCLASSES_MAY_NOT_BE_CREATED_OR_CHANGED_WHILE_A_SKILL_IS_IN_USE);
                return;
            }
            if (player.getWeightPenalty() >= 3) {
                player.sendPacket((IStaticPacket)SystemMsg.A_SUBCLASS_CANNOT_BE_CREATED_OR_CHANGED_WHILE_YOU_ARE_OVER_YOUR_WEIGHT_LIMIT);
                return;
            }
            if ((double)player.getInventoryLimit() * 0.8 < (double)player.getInventory().getSize()) {
                player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.InventoryLimit", player, new Object[0]));
                return;
            }
            StringBuilder stringBuilder = new StringBuilder("<html><body>");
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            Map map = player.getSubClasses();
            if (player.getLevel() < 40) {
                stringBuilder.append("You must be level 40 or more to operate with your sub-classes.");
                stringBuilder.append("</body></html>");
                npcHtmlMessage.setHtml(stringBuilder.toString());
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                return;
            }
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            try {
                for (SubClass subClass2 : string.substring(9, string.length()).split(" ")) {
                    if (n3 == 0) {
                        n3 = Integer.parseInt((String)subClass2);
                        continue;
                    }
                    if (n > 0) {
                        n2 = Integer.parseInt((String)subClass2);
                        continue;
                    }
                    n = Integer.parseInt((String)subClass2);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            switch (n3) {
                case 1: {
                    Set<PlayerClass> set = this.a(player, true);
                    if (set != null && !set.isEmpty()) {
                        stringBuilder.append("Add Subclass:<br>Which subclass do you wish to add?<br>");
                        if (Config.ALT_ALLOW_SUBCLASS_FOR_CUSTOM_ITEM && !player.getVarB("SubclassCustomItem") && !player.isQuestCompleted("_235_MimirsElixir")) {
                            stringBuilder.append(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubClassPriceForCustomItem", player, new Object[0]));
                            stringBuilder.append("<br>");
                        }
                        for (PlayerClass playerClass : set) {
                            stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 4 ").append(playerClass.ordinal()).append("\">").append(HtmlUtils.htmlClassName((int)playerClass.ordinal(), (Player)player)).append("</a><br>");
                        }
                        break;
                    }
                    player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubAtThisTime", player, new Object[0]));
                    return;
                }
                case 2: {
                    stringBuilder.append("Change Subclass:<br>");
                    SubClass subClass = player.getBaseSubClass();
                    int n4 = subClass.getClassId();
                    if (map.size() < 2) {
                        stringBuilder.append("You can't change subclasses when you don't have a subclass to begin with.<br><a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 1\">Add subclass.</a>");
                        break;
                    }
                    stringBuilder.append("Which class would you like to switch to?<br>");
                    if (n4 == player.getActiveClassId()) {
                        stringBuilder.append(HtmlUtils.htmlClassName((int)n4, (Player)player)).append(" <font color=\"LEVEL\">(Base Class)</font><br><br>");
                    } else {
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 5 ").append(n4).append("\">").append(HtmlUtils.htmlClassName((int)n4, (Player)player)).append("</a> <font color=\"LEVEL\">(Base Class)</font><br><br>");
                    }
                    for (SubClass subClass2 : map.values()) {
                        if (subClass2.isBase()) continue;
                        int n5 = subClass2.getClassId();
                        if (n5 == player.getActiveClassId()) {
                            stringBuilder.append(HtmlUtils.htmlClassName((int)n5, (Player)player)).append("<br>");
                            continue;
                        }
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 5 ").append(n5).append("\">").append(HtmlUtils.htmlClassName((int)n5, (Player)player)).append("</a><br>");
                    }
                    break;
                }
                case 3: {
                    stringBuilder.append("Change Subclass:<br>Which of the following sub-classes would you like to change?<br>");
                    for (SubClass subClass2 : map.values()) {
                        stringBuilder.append("<br>");
                        if (subClass2.isBase()) continue;
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 6 ").append(subClass2.getClassId()).append("\">").append(HtmlUtils.htmlClassName((int)subClass2.getClassId(), (Player)player)).append("</a><br>");
                    }
                    stringBuilder.append("<br>If you change a sub-class, you'll start at level 40 after the 2nd class transfer.");
                    break;
                }
                case 4: {
                    int n6 = 1;
                    if (player.getLevel() < Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubBeforeLevel", player, new Object[0]).addNumber((long)Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS));
                        n6 = 0;
                    }
                    if (!map.isEmpty()) {
                        for (SubClass subClass : map.values()) {
                            if (subClass.getLevel() >= Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS) continue;
                            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubBeforeLevel", player, new Object[0]).addNumber((long)Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS));
                            n6 = 0;
                            break;
                        }
                    }
                    if (player.isInDuel()) {
                        n6 = 0;
                    }
                    if (Config.OLY_ENABLED && (ParticipantPool.getInstance().isRegistred(player) || player.isOlyParticipant())) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER);
                        return;
                    }
                    if (!Config.ALT_GAME_SUBCLASS_WITHOUT_QUESTS && !map.isEmpty() && map.size() < 2) {
                        if (Config.ALT_GAME_SUBCLASS_NOT_CHECK_QUEST_234 || player.isQuestCompleted("_234_FatesWhisper")) {
                            n6 = player.isQuestCompleted("_235_MimirsElixir") ? 1 : 0;
                            if (n6 == 0) {
                                player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.QuestMimirsElixir", player, new Object[0]));
                            }
                        } else {
                            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.QuestFatesWhisper", player, new Object[0]));
                            n6 = 0;
                        }
                    }
                    if (Config.ALT_ALLOW_SUBCLASS_FOR_CUSTOM_ITEM && !player.getVarB("SubclassCustomItem") && !player.isQuestCompleted("_235_MimirsElixir")) {
                        long l;
                        int n7;
                        int n8;
                        boolean bl = true;
                        for (n8 = 0; n8 < Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID.length; ++n8) {
                            n7 = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID[n8];
                            l = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT[n8];
                            if (ItemFunctions.getItemCount((Playable)player, (int)n7) >= l) continue;
                            bl = false;
                            break;
                        }
                        if (!bl) {
                            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                            return;
                        }
                        for (n8 = 0; n8 < Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID.length; ++n8) {
                            n7 = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID[n8];
                            l = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT[n8];
                            if (ItemFunctions.removeItem((Playable)player, (int)n7, (long)l, (boolean)true) >= l) continue;
                            bl = false;
                            break;
                        }
                        if (bl) {
                            player.setVar("SubclassCustomItem", 1, -1L);
                        } else {
                            return;
                        }
                    }
                    if (n6 != 0) {
                        if (!player.addSubClass(n, true)) {
                            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubclassCouldNotBeAdded", player, new Object[0]));
                            return;
                        }
                        stringBuilder.append("Add Subclass:<br>The subclass of <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName((int)n, (Player)player)).append("</font> has been added.");
                        player.sendPacket((IStaticPacket)SystemMsg.THE_NEW_SUBCLASS_HAS_BEEN_ADDED);
                        break;
                    }
                    npcHtmlMessage.setFile("villagemaster/SubClass_Fail.htm");
                    break;
                }
                case 5: {
                    if (Config.OLY_ENABLED && (ParticipantPool.getInstance().isRegistred(player) || player.isOlyParticipant())) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER);
                        return;
                    }
                    if (player.isInDuel()) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubclassCouldNotBeAdded", player, new Object[0]));
                        return;
                    }
                    this.l(player, n);
                    int n9 = player.getClassId().getId();
                    player.setActiveSubClass(n, true);
                    player.getListeners().onSetActiveSubClass(n);
                    stringBuilder.append("Change Subclass:<br>Your active subclass is now a <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName((int)player.getActiveClassId(), (Player)player)).append("</font>.");
                    player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_SWITCHED_S1_TO_S2).addClassId(n9)).addClassId(player.getActiveClassId()));
                    break;
                }
                case 6: {
                    stringBuilder.append("Please choose a subclass to change to. If the one you are looking for is not here, please seek out the appropriate master for that class.<br><font color=\"LEVEL\">Warning!</font> All classes and skills for this class will be removed.<br><br>");
                    Set<PlayerClass> set = this.a(player, false);
                    if (!set.isEmpty()) {
                        for (PlayerClass playerClass : set) {
                            stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 7 ").append(n).append(" ").append(playerClass.ordinal()).append("\">").append(HtmlUtils.htmlClassName((int)playerClass.ordinal(), (Player)player)).append("</a><br>");
                        }
                        break;
                    }
                    player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubAtThisTime", player, new Object[0]));
                    return;
                }
                case 7: {
                    if (Config.OLY_ENABLED && (ParticipantPool.getInstance().isRegistred(player) || player.isOlyParticipant())) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER);
                        return;
                    }
                    this.l(player, n2);
                    if (player.modifySubClass(n, n2)) {
                        stringBuilder.append("Change Subclass:<br>Your subclass has been changed to <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName((int)n2, (Player)player)).append("</font>.");
                        player.sendPacket((IStaticPacket)SystemMsg.THE_NEW_SUBCLASS_HAS_BEEN_ADDED);
                        break;
                    }
                    player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubclassCouldNotBeAdded", player, new Object[0]));
                    return;
                }
            }
            stringBuilder.append("</body></html>");
            if (stringBuilder.length() > 26) {
                npcHtmlMessage.setHtml(stringBuilder.toString());
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    private Set<PlayerClass> a(Player player, boolean bl) {
        SubClass subClass = player.getSubClasses().values().stream().filter(SubClass::isBase).findFirst().get();
        int n = subClass.getClassId();
        Race race = this.a();
        ClassType classType = this.a();
        PlayerClass playerClass = PlayerClass.values()[n];
        Set set = playerClass.getAvailableSubclasses();
        if (set == null) {
            return Collections.emptySet();
        }
        set.remove(playerClass);
        for (PlayerClass playerClass2 : set) {
            for (SubClass subClass2 : player.getSubClasses().values()) {
                if (playerClass2.ordinal() == subClass2.getClassId()) {
                    set.remove(playerClass2);
                    continue;
                }
                ClassId classId = ClassId.VALUES[playerClass2.ordinal()].getParent();
                if (classId != null && classId.getId() == subClass2.getClassId()) {
                    set.remove(playerClass2);
                    continue;
                }
                ClassId classId2 = ClassId.VALUES[subClass2.getClassId()].getParent();
                if (classId2 == null || classId2.getId() != playerClass2.ordinal()) continue;
                set.remove(playerClass2);
            }
            if (Config.ALTSUBCLASS_LIST_ALL) continue;
            if (!playerClass2.isOfRace(Race.human) && !playerClass2.isOfRace(Race.elf)) {
                if (playerClass2.isOfRace(race)) continue;
                set.remove(playerClass2);
                continue;
            }
            if (playerClass2.isOfType(classType) && race == Race.human) continue;
            set.remove(playerClass2);
        }
        return set;
    }

    private Race a() {
        switch (this.getTemplate().getRace()) {
            case 14: {
                return Race.human;
            }
            case 15: {
                return Race.elf;
            }
            case 16: {
                return Race.darkelf;
            }
            case 17: {
                return Race.orc;
            }
            case 18: {
                return Race.dwarf;
            }
        }
        return null;
    }

    private ClassType a() {
        switch (this.getNpcId()) {
            case 30022: 
            case 30030: 
            case 30031: 
            case 30032: 
            case 30036: 
            case 30037: 
            case 30067: 
            case 30070: 
            case 30116: 
            case 30117: 
            case 30118: 
            case 30120: 
            case 30129: 
            case 30130: 
            case 30131: 
            case 30132: 
            case 30133: 
            case 30141: 
            case 30188: 
            case 30191: 
            case 30289: 
            case 30305: 
            case 30358: 
            case 30359: 
            case 30404: 
            case 30419: 
            case 30421: 
            case 30422: 
            case 30424: 
            case 30502: 
            case 30507: 
            case 30510: 
            case 30515: 
            case 30537: 
            case 30538: 
            case 30571: 
            case 30572: 
            case 30575: 
            case 30598: 
            case 30614: 
            case 30657: 
            case 30665: 
            case 30682: 
            case 30706: 
            case 30857: 
            case 30858: 
            case 30859: 
            case 30905: 
            case 30906: 
            case 30927: 
            case 30981: 
            case 31279: 
            case 31290: 
            case 31291: 
            case 31328: 
            case 31335: 
            case 31336: 
            case 31348: 
            case 31349: 
            case 31350: 
            case 31424: 
            case 31428: 
            case 31429: 
            case 31452: 
            case 31454: 
            case 31524: 
            case 31581: 
            case 31591: 
            case 31602: 
            case 31613: 
            case 31644: 
            case 31856: 
            case 31968: 
            case 31973: 
            case 31979: 
            case 31980: 
            case 32008: 
            case 32010: 
            case 32019: 
            case 32095: {
                return ClassType.Priest;
            }
            case 30017: 
            case 30019: 
            case 30033: 
            case 30034: 
            case 30035: 
            case 30068: 
            case 30069: 
            case 30110: 
            case 30111: 
            case 30112: 
            case 30114: 
            case 30115: 
            case 30144: 
            case 30145: 
            case 30154: 
            case 30158: 
            case 30171: 
            case 30174: 
            case 30175: 
            case 30176: 
            case 30189: 
            case 30190: 
            case 30194: 
            case 30293: 
            case 30330: 
            case 30344: 
            case 30375: 
            case 30377: 
            case 30461: 
            case 30464: 
            case 30473: 
            case 30476: 
            case 30609: 
            case 30610: 
            case 30612: 
            case 30634: 
            case 30635: 
            case 30637: 
            case 30638: 
            case 30639: 
            case 30640: 
            case 30666: 
            case 30680: 
            case 30694: 
            case 30695: 
            case 30696: 
            case 30701: 
            case 30715: 
            case 30717: 
            case 30720: 
            case 30721: 
            case 30854: 
            case 30855: 
            case 30861: 
            case 30864: 
            case 30907: 
            case 30908: 
            case 30912: 
            case 30915: 
            case 30988: 
            case 31001: 
            case 31046: 
            case 31047: 
            case 31048: 
            case 31049: 
            case 31050: 
            case 31051: 
            case 31052: 
            case 31053: 
            case 31281: 
            case 31282: 
            case 31283: 
            case 31285: 
            case 31326: 
            case 31330: 
            case 31331: 
            case 31332: 
            case 31333: 
            case 31337: 
            case 31339: 
            case 31359: 
            case 31415: 
            case 31425: 
            case 31426: 
            case 31427: 
            case 31430: 
            case 31431: 
            case 31605: 
            case 31608: 
            case 31614: 
            case 31620: 
            case 31643: 
            case 31740: 
            case 31755: 
            case 31953: 
            case 31969: 
            case 31970: 
            case 31971: 
            case 31972: 
            case 31976: 
            case 31977: 
            case 31996: 
            case 32056: 
            case 32074: 
            case 32082: 
            case 32083: 
            case 32084: 
            case 32085: 
            case 32086: 
            case 32087: 
            case 32088: 
            case 32089: 
            case 32098: {
                return ClassType.Mystic;
            }
        }
        return ClassType.Fighter;
    }

    private void a(NpcInstance npcInstance, Player player, String string) {
        if (player.getLevel() < Config.CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_MEET_THE_CRITERIA_IN_ORDER_TO_CREATE_A_CLAN);
            return;
        }
        if (player.getClanId() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_CREATE_A_CLAN);
            return;
        }
        if (!player.canCreateClan()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MUST_WAIT_10_DAYS_BEFORE_CREATING_A_NEW_CLAN);
            return;
        }
        if (string.length() > 16) {
            player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAMES_LENGTH_IS_INCORRECT);
            return;
        }
        if (!Util.isMatchingRegexp((String)string, (String)Config.CLAN_NAME_TEMPLATE)) {
            player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAME_IS_INVALID);
            return;
        }
        Clan clan = ClanTable.getInstance().createClan(player, string);
        if (clan == null) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_NAME_ALREADY_EXISTS);
            return;
        }
        player.sendPacket(clan.listAll());
        player.sendPacket(new IStaticPacket[]{new PledgeShowInfoUpdate(clan), SystemMsg.YOUR_CLAN_HAS_BEEN_CREATED});
        player.updatePledgeClass();
        player.broadcastCharInfo();
        npcInstance.showChatWindow(player, "villagemaster/pl006.htm", new Object[0]);
    }

    public boolean canEnchantSkills() {
        return true;
    }

    private void l(Player player, int n) {
        if (!Config.ALT_PARTY_CLASS_LIMIT.isEmpty() && Config.ALT_PARTY_CLASS_LIMIT.containsKey(n)) {
            Party party = player.getParty();
            int n2 = 0;
            if (party != null) {
                for (Player player2 : party.getPartyMembers()) {
                    if (player2.getActiveClass().getClassId() != n) continue;
                    ++n2;
                }
                if (n2 >= (Integer)Config.ALT_PARTY_CLASS_LIMIT.get(n)) {
                    party.removePartyMember(player, true);
                    player.sendMessage(new CustomMessage("PARTY_PARTICIPATION_HAS_FAILED_BECAUSE_REQUIREMENTS_ARE_NOT_MET", player, new Object[0]));
                }
            }
        }
    }
}
