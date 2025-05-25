/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.SubClass;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.ClassType;
import l2.gameserver.model.base.PlayerClass;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.PledgeReceiveSubPledgeCreated;
import l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListUpdate;
import l2.gameserver.network.l2.s2c.PledgeStatusChanged;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Util;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class VillageMasterInstance
extends NpcInstance {
    public VillageMasterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    public void onBypassFeedback(Player player, String string) {
        if (!VillageMasterInstance.canBypassCheck(player, this)) {
            return;
        }
        if (string.equals("create_clan_check")) {
            if (player.getLevel() < Config.CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE) {
                this.showChatWindow(player, "villagemaster/pl002.htm", new Object[0]);
            } else if (player.isClanLeader()) {
                this.showChatWindow(player, "villagemaster/pl003.htm", new Object[0]);
            } else if (player.getClan() != null) {
                this.showChatWindow(player, "villagemaster/pl004.htm", new Object[0]);
            } else {
                this.showChatWindow(player, "villagemaster/pl005.htm", new Object[0]);
            }
        } else if (string.equals("disband_clan_check")) {
            if (VillageMasterInstance.a((NpcInstance)this, player)) {
                this.showChatWindow(player, "villagemaster/pl007.htm", new Object[0]);
            }
        } else if (string.equals("restore_clan_check")) {
            if (VillageMasterInstance.a((NpcInstance)this, player)) {
                this.showChatWindow(player, "villagemaster/pl010.htm", new Object[0]);
            }
        } else if (string.startsWith("create_clan") && string.length() > 12) {
            String string2 = string.substring(12);
            this.a(this, player, string2);
        } else if (string.startsWith("create_academy") && string.length() > 15) {
            Clan clan = player.getClan();
            String string3 = string.substring(15, string.length());
            this.a(player, string3, -1, 5, "");
            clan.setRankPrivs(9, 528392);
        } else if (string.startsWith("create_royal") && string.length() > 15) {
            String[] stringArray = string.substring(13, string.length()).split(" ", 2);
            if (stringArray.length == 2) {
                this.a(player, stringArray[1], 100, 6, stringArray[0]);
            }
        } else if (string.startsWith("create_knight") && string.length() > 16) {
            String[] stringArray = string.substring(14, string.length()).split(" ", 2);
            if (stringArray.length == 2) {
                this.a(player, stringArray[1], 1001, 7, stringArray[0]);
            }
        } else if (string.startsWith("assign_subpl_leader") && string.length() > 22) {
            String[] stringArray = string.substring(20, string.length()).split(" ", 2);
            if (stringArray.length == 2) {
                this.a(player, stringArray[1], stringArray[0]);
            }
        } else if (string.startsWith("assign_new_clan_leader") && string.length() > 23) {
            String string4 = string.substring(23);
            this.o(player, string4);
        } else if (string.startsWith("cancel_new_clan_leader")) {
            this.aa(player);
        } else if (string.startsWith("create_ally") && string.length() > 12) {
            String string5 = string.substring(12);
            this.p(player, string5);
        } else if (string.startsWith("dissolve_ally")) {
            this.dissolveAlly(player);
        } else if (string.startsWith("dissolve_clan")) {
            VillageMasterInstance.c(this, player);
        } else if (string.startsWith("restore_clan")) {
            VillageMasterInstance.a(this, player);
        } else if (string.startsWith("increase_clan_level")) {
            this.ab(player);
        } else if (string.startsWith("learn_clan_skills")) {
            VillageMasterInstance.showClanSkillList(player);
        } else if (string.startsWith("ShowCouponExchange")) {
            string = Functions.getItemCount(player, 8869) > 0L || Functions.getItemCount(player, 8870) > 0L ? "Multisell 800" : "Link villagemaster/reflect_weapon_master_noticket.htm";
            super.onBypassFeedback(player, string);
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
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
            Map<Integer, SubClass> map = player.getSubClasses();
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
                for (String object : string.substring(9, string.length()).split(" ")) {
                    if (n3 == 0) {
                        n3 = Integer.parseInt(object);
                        continue;
                    }
                    if (n > 0) {
                        n2 = Integer.parseInt(object);
                        continue;
                    }
                    n = Integer.parseInt(object);
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
                            stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 4 ").append(playerClass.ordinal()).append("\">").append(HtmlUtils.htmlClassName(playerClass.ordinal(), player)).append("</a><br>");
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
                        stringBuilder.append(HtmlUtils.htmlClassName(n4, player)).append(" <font color=\"LEVEL\">(Base Class)</font><br><br>");
                    } else {
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 5 ").append(n4).append("\">").append(HtmlUtils.htmlClassName(n4, player)).append("</a> <font color=\"LEVEL\">(Base Class)</font><br><br>");
                    }
                    for (SubClass n9 : map.values()) {
                        if (n9.isBase()) continue;
                        int n5 = n9.getClassId();
                        if (n5 == player.getActiveClassId()) {
                            stringBuilder.append(HtmlUtils.htmlClassName(n5, player)).append("<br>");
                            continue;
                        }
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 5 ").append(n5).append("\">").append(HtmlUtils.htmlClassName(n5, player)).append("</a><br>");
                    }
                    break;
                }
                case 3: {
                    stringBuilder.append("Change Subclass:<br>Which of the following sub-classes would you like to change?<br>");
                    for (SubClass subClass : map.values()) {
                        stringBuilder.append("<br>");
                        if (subClass.isBase()) continue;
                        stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 6 ").append(subClass.getClassId()).append("\">").append(HtmlUtils.htmlClassName(subClass.getClassId(), player)).append("</a><br>");
                    }
                    stringBuilder.append("<br>If you change a sub-class, you'll start at level 40 after the 2nd class transfer.");
                    break;
                }
                case 4: {
                    int n6 = 1;
                    if (player.getLevel() < Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubBeforeLevel", player, new Object[0]).addNumber(Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS));
                        n6 = 0;
                    }
                    if (!map.isEmpty()) {
                        for (SubClass subClass : map.values()) {
                            if (subClass.getLevel() >= Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS) continue;
                            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubBeforeLevel", player, new Object[0]).addNumber(Config.ALT_GAME_LEVEL_TO_GET_SUBCLASS));
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
                        boolean bl;
                        boolean bl2;
                        long l;
                        int n7;
                        int n8;
                        boolean bl3 = true;
                        for (n8 = 0; n8 < Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID.length; ++n8) {
                            n7 = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID[n8];
                            l = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT[n8];
                            if (ItemFunctions.getItemCount(player, n7) >= l) continue;
                            bl2 = false;
                            break;
                        }
                        if (!bl2) {
                            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                            return;
                        }
                        for (n8 = 0; n8 < Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID.length; ++n8) {
                            n7 = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID[n8];
                            l = Config.ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT[n8];
                            if (ItemFunctions.removeItem(player, n7, l, true) >= l) continue;
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
                        stringBuilder.append("Add Subclass:<br>The subclass of <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName(n, player)).append("</font> has been added.");
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
                    stringBuilder.append("Change Subclass:<br>Your active subclass is now a <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName(player.getActiveClassId(), player)).append("</font>.");
                    player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_SWITCHED_S1_TO_S2).addClassId(n9)).addClassId(player.getActiveClassId()));
                    break;
                }
                case 6: {
                    stringBuilder.append("Please choose a subclass to change to. If the one you are looking for is not here, please seek out the appropriate master for that class.<br><font color=\"LEVEL\">Warning!</font> All classes and skills for this class will be removed.<br><br>");
                    Set<PlayerClass> set = this.a(player, false);
                    if (!set.isEmpty()) {
                        for (PlayerClass playerClass : set) {
                            stringBuilder.append("<a action=\"bypass -h npc_").append(this.getObjectId()).append("_Subclass 7 ").append(n).append(" ").append(playerClass.ordinal()).append("\">").append(HtmlUtils.htmlClassName(playerClass.ordinal(), player)).append("</a><br>");
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
                        stringBuilder.append("Change Subclass:<br>Your subclass has been changed to <font color=\"LEVEL\">").append(HtmlUtils.htmlClassName(n2, player)).append("</font>.");
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

    @Override
    public String getHtmlPath(int n, int n2, Player player) {
        String string = n2 == 0 ? "" + n : n + "-" + n2;
        return "villagemaster/" + string + ".htm";
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
        if (!Util.isMatchingRegexp(string, Config.CLAN_NAME_TEMPLATE)) {
            player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAME_IS_INVALID);
            return;
        }
        Clan clan = ClanTable.getInstance().createClan(player, string);
        if (clan == null) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_NAME_ALREADY_EXISTS);
            return;
        }
        player.sendPacket(clan.listAll());
        player.sendPacket(new PledgeShowInfoUpdate(clan), SystemMsg.YOUR_CLAN_HAS_BEEN_CREATED);
        player.updatePledgeClass();
        player.broadcastCharInfo();
        npcInstance.showChatWindow(player, "villagemaster/pl006.htm", new Object[0]);
    }

    private void aa(Player player) {
        if (!player.isClanLeader()) {
            this.showChatWindow(player, "villagemaster/pl_err_master.htm", new Object[0]);
            return;
        }
        if (player.getEvent(SiegeEvent.class) != null) {
            player.sendMessage(new CustomMessage("scripts.services.Rename.SiegeNow", player, new Object[0]));
            return;
        }
        Clan clan = player.getClan();
        SubUnit subUnit = clan.getSubUnit(0);
        UnitMember unitMember = subUnit.getLeader();
        if (unitMember.getObjectId() != player.getObjectId() || subUnit.getNextLeaderObjectId() == 0 || subUnit.getNextLeaderObjectId() == player.getObjectId()) {
            this.showChatWindow(player, "villagemaster/pl_not_transfer.htm", new Object[0]);
            return;
        }
        VillageMasterInstance.setLeader(player, clan, subUnit, unitMember);
        this.showChatWindow(player, "villagemaster/pl_cancel_success.htm", new Object[0]);
    }

    private void o(Player player, String string) {
        if (!player.isClanLeader()) {
            this.showChatWindow(player, "villagemaster/pl_err_master.htm", new Object[0]);
            return;
        }
        if (player.getClan().isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return;
        }
        if (player.getEvent(SiegeEvent.class) != null) {
            player.sendMessage(new CustomMessage("scripts.services.Rename.SiegeNow", player, new Object[0]));
            return;
        }
        Clan clan = player.getClan();
        SubUnit subUnit = clan.getSubUnit(0);
        UnitMember unitMember = subUnit.getUnitMember(string);
        if (unitMember == null) {
            this.showChatWindow(player, "villagemaster/pl_err_sm2.htm", new Object[0]);
            return;
        }
        if (unitMember.getLeaderOf() == 100 || unitMember.getLeaderOf() == 200) {
            this.showChatWindow(player, "villagemaster/pl_err_sm3.htm", new Object[0]);
            return;
        }
        if (unitMember.getLeaderOf() == 1001 || unitMember.getLeaderOf() == 1002 || unitMember.getLeaderOf() == 2001 || unitMember.getLeaderOf() == 2002) {
            this.showChatWindow(player, "villagemaster/pl_err_sm4.htm", new Object[0]);
            return;
        }
        if (subUnit.getNextLeaderObjectId() != 0 && subUnit.getNextLeaderObjectId() != player.getObjectId()) {
            this.showChatWindow(player, "villagemaster/pl_transfer_already.htm", new Object[0]);
            return;
        }
        VillageMasterInstance.setLeader(player, clan, subUnit, unitMember);
        this.showChatWindow(player, "villagemaster/pl_transfer_success.htm", new Object[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setLeader(Player player, Clan clan, SubUnit subUnit, UnitMember unitMember) {
        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.ClanLeaderWillBeChangedFromS1ToS2", player, new Object[0]).addString(clan.getLeaderName()).addString(unitMember.getName()));
        if (Config.CLAN_LEADER_CHANGE_METHOD) {
            Iterable<UnitMember> iterable;
            if (clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
                if (clan.getLeader() != null && (iterable = clan.getLeader().getPlayer()) != null) {
                    Clan.removeClanLeaderSkills((Player)iterable);
                }
                if ((iterable = unitMember.getPlayer()) != null) {
                    Clan.addClanLeaderSkills((Player)iterable);
                }
            }
            iterable = clan;
            synchronized (iterable) {
                subUnit.setLeader(unitMember, true);
            }
            clan.broadcastClanStatus(true, true, false);
        } else {
            subUnit.updateDbLeader(unitMember);
            clan.broadcastClanStatus(true, true, false);
        }
    }

    public static void setNowLeader(Clan clan, SubUnit subUnit, UnitMember unitMember) {
        if (clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
            Player player;
            if (clan.getLeader() != null && (player = clan.getLeader().getPlayer()) != null) {
                Clan.removeClanLeaderSkills(player);
            }
            if ((player = unitMember.getPlayer()) != null) {
                Clan.addClanLeaderSkills(player);
            }
        }
        subUnit.setLeader(unitMember, true);
        clan.broadcastClanStatus(true, true, false);
    }

    /*
     * WARNING - void declaration
     */
    private void a(Player player, String string, int n, int n2, String string2) {
        void var10_15;
        UnitMember unitMember = null;
        Clan clan = player.getClan();
        if (clan == null || !player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_CREATE_A_CLAN);
            return;
        }
        if (!Util.isMatchingRegexp(string, Config.CLAN_NAME_TEMPLATE)) {
            player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAME_IS_INVALID);
            return;
        }
        Collection<SubUnit> collection = clan.getAllSubUnits();
        for (SubUnit object2 : collection) {
            if (!object2.getName().equals(string)) continue;
            player.sendPacket((IStaticPacket)SystemMsg.ANOTHER_MILITARY_UNIT_IS_ALREADY_USING_THAT_NAME_PLEASE_ENTER_A_DIFFERENT_NAME);
            return;
        }
        if (ClanTable.getInstance().getClanByName(string) != null) {
            player.sendPacket((IStaticPacket)SystemMsg.ANOTHER_MILITARY_UNIT_IS_ALREADY_USING_THAT_NAME_PLEASE_ENTER_A_DIFFERENT_NAME);
            return;
        }
        if (clan.getLevel() < n2) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_CONDITIONS_NECESSARY_TO_CREATE_A_MILITARY_UNIT_HAVE_NOT_BEEN_MET);
            return;
        }
        SubUnit subUnit = clan.getSubUnit(0);
        if (n != -1) {
            unitMember = subUnit.getUnitMember(string2);
            if (unitMember == null) {
                player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.PlayerCantBeAssignedAsSubUnitLeader", player, new Object[0]));
                return;
            }
            if (unitMember.getLeaderOf() != -128) {
                player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.ItCantBeSubUnitLeader", player, new Object[0]));
                return;
            }
        }
        if ((n = clan.createSubPledge(player, n, unitMember, string)) == -128) {
            return;
        }
        clan.broadcastToOnlineMembers(new PledgeReceiveSubPledgeCreated(clan.getSubUnit(n)));
        if (n == -1) {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.CONGRATULATIONS_THE_S1S_CLAN_ACADEMY_HAS_BEEN_CREATED);
            systemMessage.addString(player.getClan().getName());
        } else if (n >= 1001) {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_KNIGHTS_OF_S1_HAVE_BEEN_CREATED);
            systemMessage.addString(player.getClan().getName());
        } else if (n >= 100) {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_ROYAL_GUARD_OF_S1_HAVE_BEEN_CREATED);
            systemMessage.addString(player.getClan().getName());
        } else {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.YOUR_CLAN_HAS_BEEN_CREATED);
        }
        player.sendPacket((IStaticPacket)var10_15);
        if (unitMember != null) {
            clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember));
            if (unitMember.isOnline()) {
                unitMember.getPlayer().updatePledgeClass();
                unitMember.getPlayer().broadcastCharInfo();
            }
        }
    }

    private void a(Player player, String string, String string2) {
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.ClanDoesntExist", player, new Object[0]));
            return;
        }
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return;
        }
        SubUnit object2 = null;
        for (SubUnit object3 : clan.getAllSubUnits()) {
            if (object3.getType() == 0 || object3.getType() == -1 || !object3.getName().equalsIgnoreCase(string)) continue;
            object2 = object3;
        }
        if (object2 == null) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubUnitNotFound", player, new Object[0]));
            return;
        }
        SubUnit subUnit = clan.getSubUnit(0);
        UnitMember unitMember = subUnit.getUnitMember(string2);
        if (unitMember == null) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.PlayerCantBeAssignedAsSubUnitLeader", player, new Object[0]));
            return;
        }
        if (unitMember.getObjectId() == subUnit.getNextLeaderObjectId()) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.PlayerCantBeAssignedAsSubUnitLeader", player, new Object[0]));
            return;
        }
        if (unitMember.getLeaderOf() != -128) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.ItCantBeSubUnitLeader", player, new Object[0]));
            return;
        }
        object2.setLeader(unitMember, true);
        clan.broadcastToOnlineMembers(new PledgeReceiveSubPledgeCreated(object2));
        clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember));
        if (unitMember.isOnline()) {
            unitMember.getPlayer().updatePledgeClass();
            unitMember.getPlayer().broadcastCharInfo();
        }
        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NewSubUnitLeaderHasBeenAssigned", player, new Object[0]));
    }

    private static boolean a(NpcInstance npcInstance, Player player) {
        if (player.getClan() == null) {
            npcInstance.showChatWindow(player, "villagemaster/pl_err_sm.htm", new Object[0]);
            return false;
        }
        if (!player.isClanLeader()) {
            npcInstance.showChatWindow(player, "villagemaster/pl_err_master.htm", new Object[0]);
            return false;
        }
        return true;
    }

    private static void c(NpcInstance npcInstance, Player player) {
        if (player == null || player.getClan() == null) {
            return;
        }
        Clan clan = player.getClan();
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return;
        }
        if (clan.isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return;
        }
        if (!clan.canDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_APPLY_FOR_DISSOLUTION_AGAIN_WITHIN_SEVEN_DAYS_AFTER_A_PREVIOUS_APPLICATION_FOR_DISSOLUTION);
            return;
        }
        if (clan.getAllyId() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DISPERSE_THE_CLANS_IN_YOUR_ALLIANCE);
            return;
        }
        if (clan.isAtWar() > 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DISSOLVE_A_CLAN_WHILE_ENGAGED_IN_A_WAR);
            return;
        }
        if (clan.getCastle() != 0 || clan.getHasHideout() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.UNABLE_TO_DISSOLVE_YOUR_CLAN_OWNS_ONE_OR_MORE_CASTLES_OR_HIDEOUTS);
            return;
        }
        for (Residence residence : ResidenceHolder.getInstance().getResidences()) {
            if (((SiegeEvent)residence.getSiegeEvent()).getSiegeClan("attackers", clan) == null && ((SiegeEvent)residence.getSiegeEvent()).getSiegeClan("defenders", clan) == null && ((SiegeEvent)residence.getSiegeEvent()).getSiegeClan("defenders_waiting", clan) == null) continue;
            player.sendPacket((IStaticPacket)SystemMsg.UNABLE_TO_DISSOLVE_YOUR_CLAN_HAS_REQUESTED_TO_PARTICIPATE_IN_A_CASTLE_SIEGE);
            return;
        }
        clan.placeForDisband();
        clan.broadcastClanStatus(true, true, false);
        npcInstance.showChatWindow(player, "villagemaster/pl009.htm", new Object[0]);
    }

    private static void a(VillageMasterInstance villageMasterInstance, Player player) {
        if (!VillageMasterInstance.a((NpcInstance)villageMasterInstance, player)) {
            return;
        }
        Clan clan = player.getClan();
        if (!clan.isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_REQUESTS_TO_DISPERSE);
            return;
        }
        clan.unPlaceDisband();
        clan.broadcastClanStatus(true, true, false);
        villageMasterInstance.showChatWindow(player, "villagemaster/pl012.htm", new Object[0]);
    }

    private void ab(Player player) {
        Clan clan = player.getClan();
        if (clan == null) {
            return;
        }
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return;
        }
        if (player.getClan().isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return;
        }
        boolean bl = false;
        switch (clan.getLevel()) {
            case 0: {
                if (player.getSp() < (long)Config.CLAN_FIRST_LEVEL_SP || player.getAdena() < (long)Config.CLAN_FIRST_LEVEL_ADENA) break;
                player.setSp(player.getSp() - (long)Config.CLAN_FIRST_LEVEL_SP);
                player.reduceAdena(Config.CLAN_FIRST_LEVEL_ADENA, true);
                bl = true;
                break;
            }
            case 1: {
                if (player.getSp() < (long)Config.CLAN_SECOND_LEVEL_SP || player.getAdena() < (long)Config.CLAN_SECOND_LEVEL_ADENA) break;
                player.setSp(player.getSp() - (long)Config.CLAN_SECOND_LEVEL_SP);
                player.reduceAdena(Config.CLAN_SECOND_LEVEL_ADENA, true);
                bl = true;
                break;
            }
            case 2: {
                if (player.getSp() < (long)Config.CLAN_THIRD_LEVEL_SP || !player.getInventory().destroyItemByItemId(1419, 1L)) break;
                player.setSp(player.getSp() - (long)Config.CLAN_THIRD_LEVEL_SP);
                bl = true;
                break;
            }
            case 3: {
                if (player.getSp() < (long)Config.CLAN_FOUR_LEVEL_SP || !player.getInventory().destroyItemByItemId(3874, 1L)) break;
                player.setSp(player.getSp() - (long)Config.CLAN_FOUR_LEVEL_SP);
                bl = true;
                break;
            }
            case 4: {
                if (player.getSp() < (long)Config.CLAN_FIVE_LEVEL_SP || !player.getInventory().destroyItemByItemId(3870, 1L)) break;
                player.setSp(player.getSp() - (long)Config.CLAN_FIVE_LEVEL_SP);
                bl = true;
                break;
            }
            case 5: {
                if (clan.getReputationScore() < Config.CLAN_SIX_LEVEL_CLAN_REPUTATION || clan.getAllSize() < Config.CLAN_SIX_LEVEL_CLAN_MEMBER_COUNT) break;
                clan.incReputation(-Config.CLAN_SIX_LEVEL_CLAN_REPUTATION, false, "LvlUpClan");
                bl = true;
                break;
            }
            case 6: {
                if (clan.getReputationScore() < Config.CLAN_SEVEN_LEVEL_CLAN_REPUTATION || clan.getAllSize() < Config.CLAN_SEVEN_LEVEL_CLAN_MEMBER_COUNT) break;
                clan.incReputation(-Config.CLAN_SEVEN_LEVEL_CLAN_REPUTATION, false, "LvlUpClan");
                bl = true;
                break;
            }
            case 7: {
                if (clan.getReputationScore() < Config.CLAN_EIGHT_LEVEL_CLAN_REPUTATION || clan.getAllSize() < Config.CLAN_EIGHT_LEVEL_CLAN_MEMBER_COUNT) break;
                clan.incReputation(-Config.CLAN_EIGHT_LEVEL_CLAN_REPUTATION, false, "LvlUpClan");
                bl = true;
            }
        }
        if (bl) {
            clan.setLevel(clan.getLevel() + 1);
            clan.updateClanInDB();
            player.broadcastCharInfo();
            this.doCast(SkillTable.getInstance().getInfo(5103, 1), player, true);
            if (clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
                Clan.addClanLeaderSkills(player);
                player.sendSkillList();
                player.sendEtcStatusUpdate();
                player.updateStats();
            }
            if (clan.getLevel() == 5) {
                player.sendPacket((IStaticPacket)SystemMsg.NOW_THAT_YOUR_CLAN_LEVEL_IS_ABOVE_LEVEL_5_IT_CAN_ACCUMULATE_CLAN_REPUTATION_POINTS);
            }
            PledgeShowInfoUpdate pledgeShowInfoUpdate = new PledgeShowInfoUpdate(clan);
            PledgeStatusChanged pledgeStatusChanged = new PledgeStatusChanged(clan);
            for (UnitMember unitMember : clan) {
                if (!unitMember.isOnline()) continue;
                unitMember.getPlayer().updatePledgeClass();
                unitMember.getPlayer().sendPacket(SystemMsg.YOUR_CLANS_LEVEL_HAS_INCREASED, pledgeShowInfoUpdate, pledgeStatusChanged);
                unitMember.getPlayer().broadcastCharInfo();
            }
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_HAS_FAILED_TO_INCREASE_ITS_LEVEL);
        }
    }

    private void p(Player player, String string) {
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_CLAN_LEADERS_MAY_CREATE_ALLIANCES);
            return;
        }
        if (player.getClan().getAllyId() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_BELONG_TO_ANOTHER_ALLIANCE);
            return;
        }
        if (player.getClan().isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return;
        }
        if (string.length() > 16) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_LENGTH_FOR_AN_ALLIANCE_NAME);
            return;
        }
        if (!Util.isMatchingRegexp(string, Config.ALLY_NAME_TEMPLATE)) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ALLIANCE_NAME__PLEASE_TRY_AGAIN);
            return;
        }
        if (player.getClan().getLevel() < 5) {
            player.sendPacket((IStaticPacket)SystemMsg.TO_CREATE_AN_ALLIANCE_YOUR_CLAN_MUST_BE_LEVEL_5_OR_HIGHER);
            return;
        }
        if (ClanTable.getInstance().getAllyByName(string) != null) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_ALLIANCE_NAME_ALREADY_EXISTS);
            return;
        }
        if (!player.getClan().canCreateAlly()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CREATE_A_NEW_ALLIANCE_WITHIN_1_DAY_OF_DISSOLUTION);
            return;
        }
        Alliance alliance = ClanTable.getInstance().createAlliance(player, string);
        if (alliance == null) {
            return;
        }
        player.broadcastCharInfo();
        player.sendMessage(new CustomMessage("L2VillageMasterInstance.AllianceCreated", player, new Object[0]).addString(string));
    }

    private void dissolveAlly(Player player) {
        if (player == null || player.getAlliance() == null) {
            return;
        }
        if (!player.isAllyLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_FEATURE_IS_ONLY_AVAILABLE_TO_ALLIANCE_LEADERS);
            return;
        }
        if (player.getAlliance().getMembersCount() > 1) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_DISSOLVE_THE_ALLIANCE);
            return;
        }
        ClanTable.getInstance().dissolveAlly(player);
    }

    private Set<PlayerClass> a(Player player, boolean bl) {
        SubClass subClass = player.getSubClasses().values().stream().filter(SubClass::isBase).findFirst().get();
        int n = subClass.getClassId();
        Race race = this.a();
        ClassType classType = this.a();
        PlayerClass playerClass = PlayerClass.values()[n];
        Set<PlayerClass> set = playerClass.getAvailableSubclasses();
        if (set == null) {
            return Collections.emptySet();
        }
        set.remove((Object)playerClass);
        for (PlayerClass playerClass2 : set) {
            for (SubClass subClass2 : player.getSubClasses().values()) {
                if (playerClass2.ordinal() == subClass2.getClassId()) {
                    set.remove((Object)playerClass2);
                    continue;
                }
                ClassId classId = ClassId.VALUES[playerClass2.ordinal()].getParent();
                if (classId != null && classId.getId() == subClass2.getClassId()) {
                    set.remove((Object)playerClass2);
                    continue;
                }
                ClassId classId2 = ClassId.VALUES[subClass2.getClassId()].getParent();
                if (classId2 == null || classId2.getId() != playerClass2.ordinal()) continue;
                set.remove((Object)playerClass2);
            }
            if (Config.ALTSUBCLASS_LIST_ALL) continue;
            if (!playerClass2.isOfRace(Race.human) && !playerClass2.isOfRace(Race.elf)) {
                if (playerClass2.isOfRace(race)) continue;
                set.remove((Object)playerClass2);
                continue;
            }
            if (playerClass2.isOfType(classType) && race == Race.human) continue;
            set.remove((Object)playerClass2);
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

    private void l(Player player, int n) {
        if (!Config.ALT_PARTY_CLASS_LIMIT.isEmpty() && Config.ALT_PARTY_CLASS_LIMIT.containsKey(n)) {
            Party party = player.getParty();
            int n2 = 0;
            if (party != null) {
                for (Player player2 : party.getPartyMembers()) {
                    if (player2.getActiveClass().getClassId() != n) continue;
                    ++n2;
                }
                if (n2 >= Config.ALT_PARTY_CLASS_LIMIT.get(n)) {
                    party.removePartyMember(player, true);
                    player.sendMessage(new CustomMessage("PARTY_PARTICIPATION_HAS_FAILED_BECAUSE_REQUIREMENTS_ARE_NOT_MET", player, new Object[0]));
                }
            }
        }
    }
}
