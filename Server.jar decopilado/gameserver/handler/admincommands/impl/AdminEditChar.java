/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.handler.admincommands.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import l2.commons.lang.reference.HardReference;
import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.database.mysql;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SubClass;
import l2.gameserver.model.World;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.PlayerClass;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.ConfirmDlg;
import l2.gameserver.network.l2.s2c.ExPCCafePointInfo;
import l2.gameserver.network.l2.s2c.ExWorldChatCnt;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.PositionUtils;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.math.NumberUtils;

public class AdminEditChar
implements IAdminCommandHandler {
    /*
     * WARNING - void declaration
     */
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, final Player player) {
        Commands commands = (Commands)enum_;
        if (player.getPlayerAccess().CanRename) {
            if (string.startsWith("admin_settitle")) {
                try {
                    String string2 = string.substring(15);
                    GameObject gameObject = player.getTarget();
                    Player player2 = null;
                    if (gameObject == null) {
                        return false;
                    }
                    if (gameObject.isPlayer()) {
                        player2 = (Player)gameObject;
                        player2.setTitle(string2);
                        player2.sendMessage("Your title has been changed by a GM");
                        player2.sendChanges();
                    } else if (gameObject.isNpc()) {
                        ((NpcInstance)gameObject).setTitle(string2);
                        gameObject.decayMe();
                        gameObject.spawnMe();
                    }
                    return true;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("You need to specify the new title.");
                    return false;
                }
            }
            if (string.startsWith("admin_setclass")) {
                try {
                    String string3 = string.substring(15);
                    int n = Integer.parseInt(string3.trim());
                    GameObject gameObject = player.getTarget();
                    if (gameObject == null || !gameObject.isPlayer()) {
                        gameObject = player;
                    }
                    if (n > 118) {
                        player.sendMessage("There are no classes over 118 id.");
                        return false;
                    }
                    if (!Config.EVERYBODY_HAS_ADMIN_RIGHTS && !player.getPlayerAccess().CanChangeClass) {
                        player.sendMessage("You have no rights to change class.");
                        return false;
                    }
                    Player player3 = gameObject.getPlayer();
                    player3.setClassId(n, false, false);
                    player3.sendMessage("Your class has been changed by a GM");
                    player3.broadcastCharInfo();
                    return true;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("You need to specify the new class id.");
                    return false;
                }
            }
            if (string.startsWith("admin_setname")) {
                try {
                    String string4 = string.substring(14);
                    GameObject gameObject = player.getTarget();
                    if (gameObject == null || !gameObject.isPlayer()) {
                        return false;
                    }
                    Player player4 = (Player)gameObject;
                    if (mysql.simple_get_int("count(*)", "characters", "`char_name` like '" + string4 + "'") > 0) {
                        player.sendMessage("Name already exist.");
                        return false;
                    }
                    Log.add("Character " + player4.getName() + " renamed to " + string4 + " by GM " + player.getName(), "renames");
                    player4.reName(string4);
                    player4.sendMessage("Your name has been changed by a GM");
                    return true;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("You need to specify the new name.");
                    return false;
                }
            }
        }
        if (!player.getPlayerAccess().CanEditChar && !player.getPlayerAccess().CanViewChar) {
            return false;
        }
        if (string.equals("admin_current_player")) {
            AdminEditChar.showCharacterList(player, null);
        } else if (string.startsWith("admin_character_list")) {
            try {
                String string5 = string.substring(21);
                Player player5 = GameObjectsStorage.getPlayer(string5);
                AdminEditChar.showCharacterList(player, player5);
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
        } else if (string.startsWith("admin_show_characters_by_ip")) {
            try {
                String string6 = string.substring(28).trim();
                String[] stringArray2 = string6.split("\\s+");
                this.a(player, stringArray2[0], stringArray2.length > 1 ? Integer.parseInt(stringArray2[1]) : 0);
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
        } else if (string.startsWith("admin_show_characters_by_hwid")) {
            try {
                String string7 = string.substring(30).trim();
                String[] stringArray3 = string7.split("\\s+");
                this.b(player, stringArray3[0], stringArray3.length > 1 ? Integer.parseInt(stringArray3[1]) : 0);
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
        } else if (string.startsWith("admin_show_characters")) {
            try {
                String string8 = string.substring(22);
                int n = Integer.parseInt(string8);
                this.e(player, n);
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
        } else if (string.startsWith("admin_find_character")) {
            try {
                String string9 = string.substring(21);
                this.e(player, string9);
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                player.sendMessage("You didnt enter a character name to find.");
                this.e(player, 0);
            }
        } else {
            if (!player.getPlayerAccess().CanEditChar) {
                return false;
            }
            if (string.equals("admin_edit_character")) {
                this.s(player);
            } else if (string.equals("admin_character_actions")) {
                this.t(player);
            } else if (string.equals("admin_nokarma")) {
                this.f(player, 0);
            } else if (string.startsWith("admin_setkarma")) {
                try {
                    String string10 = string.substring(15);
                    int n = Integer.parseInt(string10);
                    this.f(player, n);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("Please specify new karma value.");
                }
            } else if (string.startsWith("admin_save_modifications")) {
                try {
                    String string11 = string.substring(24);
                    this.d(player, string11);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("Error while modifying character.");
                    this.e(player, 0);
                }
            } else if (string.startsWith("admin_rec")) {
                try {
                    String string12 = string.substring(10);
                    int n = Integer.parseInt(string12);
                    GameObject gameObject = player.getTarget();
                    Player player6 = null;
                    if (gameObject == null || !gameObject.isPlayer()) {
                        return false;
                    }
                    player6 = (Player)gameObject;
                    player6.setReceivedRec(Math.min(255, player6.getGivableRec() + n));
                    player6.broadcastUserInfo(false, new UserInfoType[0]);
                    player6.sendMessage("You have been recommended by a GM");
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Command format is //rec <number>");
                }
            } else if (string.startsWith("admin_add_wp")) {
                try {
                    String string13 = string.substring(13);
                    int n = Integer.parseInt(string13);
                    GameObject gameObject = player.getTarget();
                    Player player7 = null;
                    if (gameObject == null || !gameObject.isPlayer()) {
                        return false;
                    }
                    player7 = (Player)gameObject;
                    int n2 = player7.getVarInt("used_world_chat_points", 0);
                    int n3 = Config.WORLD_CHAT_MESSAGE_COUNT + player7.getWorldChatBonus() - n2;
                    player7.setVar("used_world_chat_points", n2 - n, -1L);
                    player.sendPacket((IStaticPacket)new ExWorldChatCnt(n3));
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Command format is //add_wp <target> <number>");
                }
            } else if (string.startsWith("admin_sethero")) {
                Player player8;
                GameObject gameObject = player.getTarget();
                if (stringArray.length > 1 && stringArray[1] != null) {
                    player8 = GameObjectsStorage.getPlayer(stringArray[1]);
                    if (player8 == null) {
                        player.sendMessage("Character " + stringArray[1] + " not found in game.");
                        return false;
                    }
                } else if (gameObject != null && gameObject.isPlayer()) {
                    player8 = (Player)gameObject;
                } else {
                    player.sendMessage("You must specify the name or target character.");
                    return false;
                }
                if (player8.isHero()) {
                    player8.setHero(false);
                    player8.updatePledgeClass();
                    HeroController.removeSkills(player8, true);
                } else {
                    player8.setHero(true);
                    player8.updatePledgeClass();
                    HeroController.addSkills(player8, true);
                }
                player8.sendSkillList();
                player8.sendMessage("Admin has changed your hero status.");
                player8.broadcastUserInfo(false, new UserInfoType[0]);
            } else if (string.startsWith("admin_setnoble")) {
                Player player9;
                GameObject gameObject = player.getTarget();
                if (stringArray.length > 1 && stringArray[1] != null) {
                    player9 = GameObjectsStorage.getPlayer(stringArray[1]);
                    if (player9 == null) {
                        player.sendMessage("Character " + stringArray[1] + " not found in game.");
                        return false;
                    }
                } else if (gameObject != null && gameObject.isPlayer()) {
                    player9 = (Player)gameObject;
                } else {
                    player.sendMessage("You must specify the name or target character.");
                    return false;
                }
                if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player9)) {
                    player.sendMessage("The player must have a 3rd profession on the main class.");
                    return false;
                }
                if (player9.isNoble()) {
                    player9.setNoble(false);
                    NoblesController.getInstance().removeNoble(player9);
                    player9.sendMessage("Admin changed your noble status, now you are not nobless.");
                } else {
                    player9.setNoble(true);
                    NoblesController.getInstance().addNoble(player9);
                    player9.sendMessage("Admin changed your noble status, now you are Nobless.");
                }
                player9.updatePledgeClass();
                player9.updateNobleSkills();
                player9.sendSkillList();
                player9.broadcastUserInfo(false, new UserInfoType[0]);
            } else if (string.startsWith("admin_setsex")) {
                GameObject gameObject = player.getTarget();
                Player player10 = null;
                if (gameObject == null || !gameObject.isPlayer()) {
                    return false;
                }
                player10 = (Player)gameObject;
                player10.changeSex();
                player10.sendMessage("Your gender has been changed by a GM");
                player10.broadcastUserInfo(true, UserInfoType.BASIC_INFO);
            } else if (string.startsWith("admin_setcolor")) {
                try {
                    String string14 = string.substring(15);
                    GameObject gameObject = player.getTarget();
                    Player player11 = null;
                    if (gameObject == null || !gameObject.isPlayer()) {
                        return false;
                    }
                    player11 = (Player)gameObject;
                    player11.setNameColor(Integer.decode("0x" + string14));
                    player11.sendMessage("Your name color has been changed by a GM");
                    player11.broadcastUserInfo(true, UserInfoType.COLOR);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("You need to specify the new color.");
                }
            } else if (string.startsWith("admin_setcolortitle")) {
                try {
                    String string15 = string.substring(15);
                    GameObject gameObject = player.getTarget();
                    Player player12 = null;
                    if (gameObject == null || !gameObject.isPlayer()) {
                        return false;
                    }
                    player12 = (Player)gameObject;
                    player12.setTitleColor(Integer.decode("0x" + string15));
                    player12.sendMessage("Your title color has been changed by a GM");
                    player12.broadcastUserInfo(true, UserInfoType.COLOR);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("You need to specify the new color.");
                }
            } else if (string.startsWith("admin_add_exp_sp_to_character")) {
                this.u(player);
            } else if (string.startsWith("admin_add_exp_sp")) {
                try {
                    String string16 = string.substring(16).trim();
                    String[] stringArray4 = string16.split(" ");
                    long l = NumberUtils.toLong((String)stringArray4[0], (long)0L);
                    int n = stringArray4.length > 1 ? NumberUtils.toInt((String)stringArray4[1], (int)0) : 0;
                    this.a(player, l, n);
                }
                catch (Exception exception) {
                    player.sendMessage("Usage: //add_exp_sp <exp> <sp>");
                }
            } else if (string.startsWith("admin_trans")) {
                StringTokenizer stringTokenizer = new StringTokenizer(string);
                if (stringTokenizer.countTokens() > 1) {
                    stringTokenizer.nextToken();
                    int n = 0;
                    try {
                        n = Integer.parseInt(stringTokenizer.nextToken());
                    }
                    catch (Exception exception) {
                        player.sendMessage("Specify a valid integer value.");
                        return false;
                    }
                    if (n != 0 && player.getTransformation() != 0) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN);
                        return false;
                    }
                    player.setTransformation(n);
                    player.sendMessage("Transforming...");
                } else {
                    player.sendMessage("Usage: //trans <ID>");
                }
            } else if (string.startsWith("admin_setsubclass")) {
                GameObject gameObject = player.getTarget();
                if (gameObject == null || !gameObject.isPlayer()) {
                    player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
                    return false;
                }
                Player player13 = (Player)gameObject;
                StringTokenizer stringTokenizer = new StringTokenizer(string);
                if (stringTokenizer.countTokens() > 1) {
                    stringTokenizer.nextToken();
                    short s = Short.parseShort(stringTokenizer.nextToken());
                    if (!player13.addSubClass(s, true)) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.SubclassCouldNotBeAdded", player, new Object[0]));
                        return false;
                    }
                    player13.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS__YOUVE_COMPLETED_A_CLASS_TRANSFER);
                } else {
                    this.a(player, player13);
                }
            } else if (string.startsWith("admin_setbday")) {
                String string17 = "Usage: //setbday YYYY-MM-DD";
                String string18 = string.substring(14);
                if (string18.length() != 10 || !Util.isMatchingRegexp(string18, "[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                    player.sendMessage(string17);
                    return false;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    simpleDateFormat.parse(string18);
                }
                catch (ParseException parseException) {
                    player.sendMessage(string17);
                }
                if (player.getTarget() == null || !player.getTarget().isPlayer()) {
                    player.sendMessage("Please select a character.");
                    return false;
                }
                if (!mysql.set("update characters set createtime = UNIX_TIMESTAMP('" + string18 + "') where obj_Id = " + player.getTarget().getObjectId())) {
                    player.sendMessage(string17);
                    return false;
                }
                player.sendMessage("New Birthday for " + player.getTarget().getName() + ": " + string18);
                player.getTarget().getPlayer().sendMessage("Admin changed your birthday to: " + string18);
            } else {
                if (string.startsWith("admin_give_all_by_ip")) {
                    if (stringArray.length >= 3) {
                        ItemTemplate itemTemplate;
                        long l = 0L;
                        try {
                            itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(stringArray[1]));
                            l = Long.parseLong(stringArray[2]);
                        }
                        catch (NumberFormatException numberFormatException) {
                            player.sendMessage("only numbers");
                            return false;
                        }
                        HashMap<String, HardReference<Player>> hashMap = new HashMap<String, HardReference<Player>>();
                        for (Player object : GameObjectsStorage.getAllPlayersForIterate()) {
                            if (object == null || object.isInOfflineMode() || object.isLogoutStarted()) continue;
                            hashMap.putIfAbsent(object.getIP(), object.getRef());
                        }
                        for (HardReference player16 : hashMap.values()) {
                            Player player2 = (Player)player16.get();
                            if (player2 == null || !player2.getInventory().validateWeight(itemTemplate, l) || !player2.getInventory().validateCapacity(itemTemplate, l)) continue;
                            Functions.addItem(player2, itemTemplate.getItemId(), l);
                            player2.sendMessage(player2.isLangRus() ? "\u0412\u044b \u0431\u044b\u043b\u0438 \u0432\u043e\u0437\u043d\u0430\u0433\u0440\u0430\u0436\u0434\u0435\u043d\u044b! " + itemTemplate.getName() + " " + l : "You have been rewarded! " + itemTemplate.getName() + " " + l);
                        }
                    } else {
                        player.sendMessage("use: //give_all_by_ip itemId count");
                        return false;
                    }
                    return true;
                }
                if (string.startsWith("admin_give_all_by_hwid")) {
                    if (stringArray.length >= 3) {
                        ItemTemplate itemTemplate;
                        long l = 0L;
                        try {
                            itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(stringArray[1]));
                            l = Long.parseLong(stringArray[2]);
                        }
                        catch (NumberFormatException numberFormatException) {
                            player.sendMessage("only numbers");
                            return false;
                        }
                        HashMap<String, HardReference<Player>> hashMap = new HashMap<String, HardReference<Player>>();
                        for (Player player3 : GameObjectsStorage.getAllPlayersForIterate()) {
                            if (player3 == null || player3.getNetConnection() == null || player3.getNetConnection().getHwid() == null || player3.isInOfflineMode() || player3.isLogoutStarted()) continue;
                            hashMap.putIfAbsent(player3.getNetConnection().getHwid(), player3.getRef());
                        }
                        for (HardReference shortCut : hashMap.values()) {
                            Player player4 = (Player)shortCut.get();
                            if (player4 == null || player4.getNetConnection() == null || player4.getNetConnection().getHwid() == null || player4.isInOfflineMode() || player4.isLogoutStarted() || !player4.getInventory().validateWeight(itemTemplate, l) || !player4.getInventory().validateCapacity(itemTemplate, l)) continue;
                            Functions.addItem(player4, itemTemplate.getItemId(), l);
                            player4.sendMessage(new CustomMessage("admincommandhandlers.rewardall.hwid", player4, new Object[0]).addItemName(itemTemplate.getItemId()).addNumber(l));
                        }
                    } else {
                        player.sendMessage("use: //give_all_by_hwid itemId count");
                        return false;
                    }
                    return true;
                }
                if (string.startsWith("admin_give_all_radius")) {
                    if (stringArray.length >= 3) {
                        ItemTemplate itemTemplate;
                        long l = 0L;
                        int n = 0;
                        try {
                            itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(stringArray[1]));
                            l = Long.parseLong(stringArray[2]);
                            n = Integer.parseInt(stringArray[3]);
                        }
                        catch (Exception exception) {
                            player.sendMessage("use: //give_all_radius [itemId] [count] [radius] > only numbers");
                            return false;
                        }
                        for (Player player5 : World.getAroundPlayers(player, n, 200)) {
                            if (player5 == null || player5.isInOfflineMode() || player5.isLogoutStarted() || !player5.getInventory().validateWeight(itemTemplate, l) || !player5.getInventory().validateCapacity(itemTemplate, l) || player5 == player) continue;
                            Functions.addItem(player5, itemTemplate.getItemId(), l);
                            player5.sendMessage(new CustomMessage("admincommandhandlers.rewardall.radius", player5, new Object[0]).addItemName(itemTemplate.getItemId()).addNumber(l));
                            player.sendMessage("You make reward " + l + " " + itemTemplate.getName() + " for all players at radius " + n);
                        }
                    } else {
                        player.sendMessage("use: //give_all_radius [itemId] [count] [radius]");
                        return false;
                    }
                    return true;
                }
                if (string.startsWith("admin_give_all")) {
                    if (stringArray.length >= 3) {
                        ItemTemplate itemTemplate;
                        long l = 0L;
                        try {
                            itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(stringArray[1]));
                            l = Long.parseLong(stringArray[2]);
                        }
                        catch (NumberFormatException numberFormatException) {
                            player.sendMessage("only numbers");
                            return false;
                        }
                        for (Player player17 : GameObjectsStorage.getAllPlayersForIterate()) {
                            if (player17 == null || player17.isInOfflineMode() || player17.isLogoutStarted() || !player17.getInventory().validateWeight(itemTemplate, l) || !player17.getInventory().validateCapacity(itemTemplate, l)) continue;
                            player17.sendMessage(new CustomMessage("admincommandhandlers.rewardall", player17, new Object[0]).addItemName(itemTemplate.getItemId()).addNumber(l));
                            Functions.addItem(player17, itemTemplate.getItemId(), l);
                        }
                    } else {
                        player.sendMessage("use: //give_all itemId count");
                        return false;
                    }
                    return true;
                }
                if (string.startsWith("admin_give_item")) {
                    if (stringArray.length < 3) {
                        player.sendMessage("Usage: //give_item id count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    int n4 = Integer.parseInt(stringArray[2]);
                    if (n < 1 || n4 < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //give_item id count <target>");
                        return false;
                    }
                    ItemFunctions.addItem((Playable)player.getTarget().getPlayer(), n, (long)n4, true);
                } else if (string.startsWith("admin_set_pa")) {
                    void var11_144;
                    int n;
                    if (!Config.SERVICES_RATE_ENABLED) {
                        player.sendMessage("Service Premium Account is Disabled");
                        return false;
                    }
                    if (stringArray.length < 2) {
                        player.sendMessage("USAGE: //set_pa <pa_id> <target>");
                        return false;
                    }
                    Player player18 = null;
                    try {
                        n = Integer.parseInt(stringArray[1]);
                    }
                    catch (Exception exception) {
                        player.sendMessage("PA id unspecified.");
                        return false;
                    }
                    if (stringArray.length > 2 && (player18 = World.getPlayer(stringArray[2])) == null) {
                        player.sendMessage("Target \"" + stringArray[2] + "\" not found.");
                        return false;
                    }
                    if (player18 == null && player.getTarget() != null) {
                        player18 = player.getTarget().getPlayer();
                    }
                    if (player18 == null) {
                        player.sendMessage("Target is unspecified.");
                        return false;
                    }
                    Config.RateBonusInfo rateBonusInfo = null;
                    Config.RateBonusInfo[] rateBonusInfoArray = Config.SERVICES_RATE_BONUS_INFO;
                    int n2 = rateBonusInfoArray.length;
                    boolean bl = false;
                    while (var11_144 < n2) {
                        Config.RateBonusInfo rateBonusInfo2 = rateBonusInfoArray[var11_144];
                        if (rateBonusInfo2.id == n) {
                            rateBonusInfo = rateBonusInfo2;
                        }
                        ++var11_144;
                    }
                    if (rateBonusInfo == null) {
                        player.sendMessage("Undefined bonus!");
                        return false;
                    }
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Please select a character.");
                        return false;
                    }
                    AccountBonusDAO.getInstance().store(player18.getAccountName(), rateBonusInfo.makeBonus());
                    player18.stopBonusTask();
                    player18.startBonusTask();
                    if (player18.getParty() != null) {
                        player18.getParty().recalculatePartyData();
                    }
                    player18.broadcastUserInfo(false, new UserInfoType[0]);
                    Log.add("Admin Command PA Bonus added " + player18.getName() + "|" + player18.getObjectId() + "|rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "|", "services");
                    player.sendMessage("SYS: Premium Account added for " + player18.getName() + " id bonus is " + rateBonusInfo.id);
                } else if (string.startsWith("admin_remove_item")) {
                    Player player19 = null;
                    boolean bl = false;
                    if (stringArray.length >= 3) {
                        int n = Integer.parseInt(stringArray[1]);
                        int n5 = Integer.parseInt(stringArray[2]);
                        if (stringArray.length > 3) {
                            player19 = World.getPlayer(stringArray[3]);
                        }
                        if (player19 == null && player.getTarget() != null) {
                            player19 = player.getTarget().getPlayer();
                        }
                        if (player19 != null && n > 0 && n5 > 0) {
                            long l = ItemFunctions.getItemCount(player19, n);
                            if (l < (long)n5) {
                                bl = true;
                                player.sendMessage("Failed: '" + player19.getName() + "' have only " + l + " items.");
                            } else {
                                bl = true;
                                player.sendMessage("Removed " + ItemFunctions.removeItem(player19, n, n5, true) + " from '" + player19.getName() + "'");
                            }
                        }
                    }
                    if (!bl) {
                        player.sendMessage("Usage: //remove_item id count <target>");
                        return false;
                    }
                } else if (string.startsWith("admin_set_aug")) {
                    Player player20 = null;
                    boolean bl = false;
                    if (stringArray.length >= 3) {
                        int n = Integer.parseInt(stringArray[1]);
                        int n6 = Integer.parseInt(stringArray[2]);
                        if (stringArray.length > 3) {
                            player20 = World.getPlayer(stringArray[3]);
                        }
                        if (player20 == null && player.getTarget() != null) {
                            player20 = player.getTarget().getPlayer();
                        }
                        if (n < 1 || n6 < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                            player.sendMessage("Usage: //set_aug AugmentId1 AugmentId2 Target");
                            return false;
                        }
                        ItemInstance itemInstance = player.getInventory().getPaperdollItem(5);
                        if (itemInstance == null) {
                            player.sendMessage(new CustomMessage("services.VariationSellService.process.EquippedItemRequired", player, new Object[0]));
                            return false;
                        }
                        if (itemInstance.isAugmented()) {
                            player.sendPacket(SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC);
                            return false;
                        }
                        boolean bl2 = itemInstance.isEquipped();
                        if (bl2) {
                            player20.getInventory().unEquipItem(itemInstance);
                        }
                        itemInstance.setVariationStat1(Integer.parseInt(stringArray[1]));
                        itemInstance.setVariationStat2(Integer.parseInt(stringArray[2]));
                        if (bl2) {
                            player20.getInventory().equipItem(itemInstance);
                        }
                        player20.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
                        for (ShortCut shortCut : player20.getAllShortCuts()) {
                            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
                            player20.sendPacket((IStaticPacket)new ShortCutRegister(player20, shortCut));
                        }
                        player20.sendChanges();
                    } else {
                        player.sendMessage("Usage: //set_aug AugmentId1 AugmentId2 Target");
                    }
                } else if (string.startsWith("admin_unset_aug")) {
                    Player player21 = null;
                    boolean bl = false;
                    if (stringArray.length >= 1) {
                        if (stringArray.length > 1) {
                            player21 = World.getPlayer(stringArray[1]);
                        }
                        if (player21 == null && player.getTarget() != null) {
                            player21 = player.getTarget().getPlayer();
                        }
                        if (player.getTarget() == null || !player.getTarget().isPlayer()) {
                            player.sendMessage("Usage: //unset_aug Target");
                            return false;
                        }
                        ItemInstance itemInstance = player.getInventory().getPaperdollItem(5);
                        if (itemInstance == null) {
                            player.sendMessage(new CustomMessage("services.VariationSellService.process.EquippedItemRequired", player, new Object[0]));
                            return false;
                        }
                        if (!itemInstance.isAugmented()) {
                            player.sendPacket(SystemMsg.AUGMENTATION_REMOVAL_CAN_ONLY_BE_DONE_ON_AN_AUGMENTED_ITEM, ActionFail.STATIC);
                            return false;
                        }
                        boolean bl3 = itemInstance.isEquipped();
                        if (bl3) {
                            player21.getInventory().unEquipItem(itemInstance);
                        }
                        itemInstance.setVariationStat1(0);
                        itemInstance.setVariationStat2(0);
                        if (bl3) {
                            player21.getInventory().equipItem(itemInstance);
                        }
                        player21.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
                        for (ShortCut shortCut : player21.getAllShortCuts()) {
                            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
                            player21.sendPacket((IStaticPacket)new ShortCutRegister(player21, shortCut));
                        }
                        player21.sendChanges();
                    } else {
                        player.sendMessage("Usage: //unset_aug Target");
                    }
                } else if (string.startsWith("admin_destroy_items")) {
                    Player player22 = null;
                    if (stringArray.length > 1) {
                        player22 = World.getPlayer(stringArray[1]);
                    }
                    if (player22 == null && player.getTarget() != null) {
                        player22 = player.getTarget().getPlayer();
                    }
                    if (player22 != null) {
                        final Player player23 = player22;
                        ConfirmDlg confirmDlg = (ConfirmDlg)new ConfirmDlg(SystemMsg.S1, -1).addString("Are you sure you want to remove all items from " + player23.getName() + "?");
                        player.ask(confirmDlg, new OnAnswerListener(){

                            @Override
                            public void sayYes() {
                                for (ItemInstance itemInstance : player23.getInventory().getItems()) {
                                    player23.getInventory().destroyItem(itemInstance);
                                    player23.sendPacket((IStaticPacket)SystemMessage.removeItems(itemInstance.getItemId(), itemInstance.getCount()));
                                }
                                player.sendMessage("All items at " + player23.getName() + " were successfully removed.");
                            }

                            @Override
                            public void sayNo() {
                                player.sendMessage("Removal of items from " + player23.getName() + " has been cancelled.");
                            }
                        });
                    } else {
                        player.sendMessage("Target not found. Use command: //destroy_items <target>");
                    }
                } else if (string.startsWith("admin_add_bang")) {
                    if (!Config.ALT_PCBANG_POINTS_ENABLED) {
                        player.sendMessage("Error! Pc Bang Points service disabled!");
                        return true;
                    }
                    if (stringArray.length < 1) {
                        player.sendMessage("Usage: //add_bang count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //add_bang count <target>");
                        return false;
                    }
                    Player player24 = player.getTarget().getPlayer();
                    player24.addPcBangPoints(n, false);
                    player.sendMessage("You have added " + n + " Pc Bang Points to " + player24.getName());
                } else if (string.startsWith("admin_add_vip_points")) {
                    if (!Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
                        player.sendMessage("Error! VIP Points service disabled!");
                        return true;
                    }
                    if (stringArray.length < 1) {
                        player.sendMessage("Usage: //add_vip_points count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //admin_add_vip_points count <target>");
                        return false;
                    }
                    Player player25 = player.getTarget().getPlayer();
                    player25.updateVipPoints(n);
                    player.sendMessage("You have added " + n + " VIP Points to " + player25.getName());
                } else if (string.startsWith("admin_set_bang")) {
                    if (!Config.ALT_PCBANG_POINTS_ENABLED) {
                        player.sendMessage("Error! Pc Bang Points service disabled!");
                        return true;
                    }
                    if (stringArray.length < 1) {
                        player.sendMessage("Usage: //set_bang count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //set_bang count <target>");
                        return false;
                    }
                    Player player26 = player.getTarget().getPlayer();
                    player26.setPcBangPoints(n, "Admin add PC Bang");
                    player26.sendPacket((IStaticPacket)new ExPCCafePointInfo(player26, n, 1, 2, 12));
                    player.sendMessage("You have set " + player26.getName() + "'s Pc Bang Points to " + n);
                    player26.sendMessage("Your Pc Bang Points count is now " + player26.getPcBangPoints());
                } else if (string.startsWith("admin_set_raidpoints")) {
                    if (stringArray.length < 1) {
                        player.sendMessage("Usage: //set_raidpoints count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //set_raidpoints count <target>");
                        return false;
                    }
                    Player player27 = player.getTarget().getPlayer();
                    if ((long)n >= Integer.MAX_VALUE) {
                        player27.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_REACHED_THE_MAXIMUM_AMOUNT_OF_RAID_POINTS_AND_CAN_ACQUIRE_NO_MORE);
                        return false;
                    }
                    player27.setRaidBossPoints(n);
                    player27.sendUserInfo(true, UserInfoType.STATS);
                    player27.sendMessage("Your Raid Points count is now " + n);
                    player.sendMessage("You have set " + n + "'s Raid Points to " + player27.getName());
                } else if (string.startsWith("admin_add_raidpoints")) {
                    if (stringArray.length < 1) {
                        player.sendMessage("Usage: //set_raidpoints count <target>");
                        return false;
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    if (n < 1 || player.getTarget() == null || !player.getTarget().isPlayer()) {
                        player.sendMessage("Usage: //set_raidpoints count <target>");
                        return false;
                    }
                    Player player28 = player.getTarget().getPlayer();
                    if ((long)n + (long)player28.getRaidBossPoints() >= Integer.MAX_VALUE) {
                        player28.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_REACHED_THE_MAXIMUM_AMOUNT_OF_RAID_POINTS_AND_CAN_ACQUIRE_NO_MORE);
                        return false;
                    }
                    player28.addRaidBossPoints(n);
                    player28.sendMessage("Your Raid Points count is now " + player28.getRaidBossPoints() + n);
                    player.sendMessage("You have add " + n + "'s Raid Points to " + player28.getName());
                }
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void a(Player player, String string, int n) {
        int n2;
        List<Player> list = GameObjectsStorage.getAllPlayers();
        LinkedList<Player> linkedList = new LinkedList<Player>();
        for (Player player2 : list) {
            String string2;
            if (player2 == null || player2.isInOfflineMode() || !player2.isConnected() || player2.getNetConnection() == null || (string2 = player2.getNetConnection().getIpAddr()) == null || !string.trim().equals(string2)) continue;
            linkedList.add(player2);
        }
        int n3 = 20;
        int n4 = linkedList.size() / n3;
        if (linkedList.size() > n3 * n4) {
            ++n4;
        }
        if (n > n4) {
            n = n4;
        }
        int n5 = n3 * n;
        int n6 = linkedList.size();
        if (n6 - n5 > n3) {
            n6 = n5 + n3;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Characters with IP \"").append(string).append("\"</center>");
        for (n2 = 0; n2 < n4; ++n2) {
            int n7 = n2 + 1;
            stringBuilder.append("<center><a action=\"bypass -h admin_show_characters_by_ip " + string + " " + n2 + "\">Page " + n7 + "</a></center>");
        }
        stringBuilder.append("<br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=80>Name:</td><td width=110>Class:</td><td width=40>Level:</td></tr>");
        for (n2 = n5; n2 < n6; ++n2) {
            Player player3 = (Player)linkedList.get(n2);
            stringBuilder.append("<tr><td width=80><a action=\"bypass -h admin_character_list " + player3.getName() + AdminEditChar.a(player3) + "\">" + player3.getName() + "</a></td><td width=110>" + player3.getTemplate().className + "</td><td width=40>" + player3.getLevel() + "</td></tr>");
        }
        stringBuilder.append("</table>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void b(Player player, String string, int n) {
        int n2;
        List<Player> list = GameObjectsStorage.getAllPlayers();
        LinkedList<Player> linkedList = new LinkedList<Player>();
        for (Player player2 : list) {
            String string2;
            if (player2 == null || player2.getNetConnection() == null || player2.isInOfflineMode() || !player2.isConnected() || (string2 = player2.getNetConnection().getHwid()) == null || !string.trim().equals(string2)) continue;
            linkedList.add(player2);
        }
        int n3 = 20;
        int n4 = linkedList.size() / n3;
        if (linkedList.size() > n3 * n4) {
            ++n4;
        }
        if (n > n4) {
            n = n4;
        }
        int n5 = n3 * n;
        int n6 = linkedList.size();
        if (n6 - n5 > n3) {
            n6 = n5 + n3;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Characters with HIWD \"").append(string).append("\"</center>");
        for (n2 = 0; n2 < n4; ++n2) {
            int n7 = n2 + 1;
            stringBuilder.append("<center><a action=\"bypass -h admin_show_characters_by_hwid " + string + " " + n2 + "\">Page " + n7 + "</a></center>");
        }
        stringBuilder.append("<br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=80>Name:</td><td width=110>Class:</td><td width=40>Level:</td></tr>");
        for (n2 = n5; n2 < n6; ++n2) {
            Player player3 = (Player)linkedList.get(n2);
            stringBuilder.append("<tr><td width=80><a action=\"bypass -h admin_character_list " + player3.getName() + "\">" + player3.getName() + "</a></td><td width=110>" + player3.getTemplate().className + "</td><td width=40>" + player3.getLevel() + "</td></tr>");
        }
        stringBuilder.append("</table>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void e(Player player, int n) {
        int n2;
        List<Player> list = GameObjectsStorage.getAllPlayers();
        int n3 = 20;
        int n4 = list.size() / n3;
        if (list.size() > n3 * n4) {
            ++n4;
        }
        if (n > n4) {
            n = n4;
        }
        int n5 = n3 * n;
        int n6 = list.size();
        if (n6 - n5 > n3) {
            n6 = n5 + n3;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=270>You can find a character by writing his name and</td></tr>");
        stringBuilder.append("<tr><td width=270>clicking Find bellow.<br></td></tr>");
        stringBuilder.append("<tr><td width=270>Note: Names should be written case sensitive.</td></tr>");
        stringBuilder.append("</table><br>");
        stringBuilder.append("<center><table><tr><td>");
        stringBuilder.append("<edit var=\"character_name\" width=80></td><td><button value=\"Find\" action=\"bypass -h admin_find_character $character_name\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
        stringBuilder.append("</td></tr></table></center><br><br>");
        for (n2 = 0; n2 < n4; ++n2) {
            int n7 = n2 + 1;
            stringBuilder.append("<center><a action=\"bypass -h admin_show_characters " + n2 + "\">Page " + n7 + "</a></center>");
        }
        stringBuilder.append("<br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=80>Name:</td><td width=110>Class:</td><td width=40>Level:</td></tr>");
        for (n2 = n5; n2 < n6; ++n2) {
            Player player2 = list.get(n2);
            stringBuilder.append("<tr><td width=80><a action=\"bypass -h admin_character_list " + player2.getName() + "\">" + player2.getName() + "</a></td><td width=110>" + player2.getTemplate().className + "</td><td width=40>" + player2.getLevel() + "</td></tr>");
        }
        stringBuilder.append("</table>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void showCharacterList(Player player, Player player2) {
        Object object;
        if (player2 == null) {
            object = player.getTarget();
            if (object == null || !((GameObject)object).isPlayer()) return;
            player2 = (Player)object;
        } else {
            player.setTarget(player2);
            player.sendPacket((IStaticPacket)new MyTargetSelected(player2.getObjectId(), 0));
        }
        object = "No Clan";
        if (player2.getClan() != null) {
            object = player2.getClan().getName() + "/" + player2.getClan().getLevel();
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setMaximumFractionDigits(4);
        numberFormat.setMinimumFractionDigits(1);
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_characters 0\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table><br1>");
        stringBuilder.append("<table width=270>");
        if (player.getPlayerAccess().CanSeeIp) {
            stringBuilder.append("<tr><td width=100>Account/IP:</td><td>" + player2.getAccountName() + "/<a action=\"bypass -h admin_show_characters_by_ip " + player2.getIP() + "\">" + player2.getIP() + "</a></td></tr>");
        } else {
            stringBuilder.append("<tr><td width=100>Account:</td><td>" + player2.getAccountName() + "</td></tr>");
        }
        if (player2.getNetConnection() != null) {
            int n;
            if (player2.getNetConnection().getHwid() != null && !player2.getNetConnection().getHwid().isEmpty() && player.getPlayerAccess().CanSeeHwid) {
                String string = player2.getNetConnection().getHwid();
                stringBuilder.append("<tr><td width=100>HWID:</td><td><a action=\"bypass -h admin_show_characters_by_hwid " + string + "\">" + string + "</a></td></tr>");
            }
            if ((n = player2.getNetConnection().getFps()) > 0) {
                stringBuilder.append("<tr><td width=100>FPS:</td><td>" + n + "</td></tr>");
            }
        }
        stringBuilder.append("<tr><td width=100>Name/Level:</td><td>" + player2.getName() + AdminEditChar.a(player2) + "/" + player2.getLevel() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Class/Id:</td><td>" + player2.getTemplate().className + "/" + player2.getClassId().getId() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Clan/Level:</td><td>" + (String)object + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Exp/Sp:</td><td>" + player2.getExp() + "/" + player2.getSp() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Cur/Max Hp:</td><td>" + (int)player2.getCurrentHp() + "/" + player2.getMaxHp() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Cur/Max Mp:</td><td>" + (int)player2.getCurrentMp() + "/" + player2.getMaxMp() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Cur/Max Load:</td><td>" + player2.getCurrentLoad() + "/" + player2.getMaxLoad() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Patk/Matk:</td><td>" + player2.getPAtk(null) + "/" + player2.getMAtk(null, null) + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Pdef/Mdef:</td><td>" + player2.getPDef(null) + "/" + player2.getMDef(null, null) + "</td></tr>");
        stringBuilder.append("<tr><td width=100>PAtkSpd/MAtkSpd:</td><td>" + player2.getPAtkSpd() + "/" + player2.getMAtkSpd() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Acc/Evas:</td><td>" + player2.getAccuracy() + "/" + player2.getEvasionRate(null) + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Crit/MCrit:</td><td>" + player2.getCriticalHit(null, null) + "/" + numberFormat.format(player2.getMagicCriticalRate(null, null)) + "%</td></tr>");
        stringBuilder.append("<tr><td width=100>Walk/Run:</td><td>" + player2.getWalkSpeed() + "/" + player2.getRunSpeed() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>PvP/PK:</td><td>" + player2.getPvpKills() + "/" + player2.getPkKills() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Coordinates:</td><td>" + player2.getX() + "," + player2.getY() + "," + player2.getZ() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>AI:</td><td>" + player2.getAI().getIntention() + "/" + player2.getAI().getNextAction() + "</td></tr>");
        stringBuilder.append("<tr><td width=100>Direction:</td><td>" + PositionUtils.getDirectionTo(player2, player) + "</td></tr>");
        stringBuilder.append("</table><br1>");
        stringBuilder.append("<table<tr>");
        stringBuilder.append("<td><button value=\"Skills\" action=\"bypass -h admin_show_skills\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Effects\" action=\"bypass -h admin_show_effects\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Actions\" action=\"bypass -h admin_character_actions\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr><tr>");
        stringBuilder.append("<td><button value=\"Stats\" action=\"bypass -h admin_edit_character\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Exp & Sp\" action=\"bypass -h admin_add_exp_sp_to_character\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td></td>");
        stringBuilder.append("</tr></table></body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void f(Player player, int n) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        if (!gameObject.isPlayer()) {
            return;
        }
        Player player2 = (Player)gameObject;
        if (n >= 0) {
            int n2 = player2.getKarma();
            player2.setKarma(n, true);
            player2.sendMessage("Admin has changed your karma from " + n2 + " to " + n + ".");
            player.sendMessage("Successfully Changed karma for " + player2.getName() + " from (" + n2 + ") to (" + n + ").");
        } else {
            player.sendMessage("You must enter a value for karma greater than or equal to 0.");
        }
    }

    private void d(Player player, String string) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer()) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        String[] stringArray = string.split("&");
        Integer[] integerArray = new Integer[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = stringArray[i].trim();
            integerArray[i] = stringArray[i].isEmpty() ? null : Integer.valueOf(stringArray[i]);
        }
        if (integerArray[0] != null) {
            player2.setCurrentHp(integerArray[0].intValue(), false);
            player2.broadcastUserInfo(true, UserInfoType.MAX_HPCPMP, UserInfoType.CURRENT_HPMPCP_EXP_SP);
        }
        if (integerArray[1] != null) {
            player2.setCurrentMp(integerArray[1].intValue());
            player2.broadcastUserInfo(true, UserInfoType.MAX_HPCPMP, UserInfoType.CURRENT_HPMPCP_EXP_SP);
        }
        if (integerArray[2] != null) {
            player2.setKarma(integerArray[2], true);
            player2.broadcastUserInfo(true, UserInfoType.SOCIAL);
        }
        if (integerArray[3] != null) {
            player2.setPvpFlag(integerArray[3]);
            player2.broadcastUserInfo(true, UserInfoType.SOCIAL);
        }
        if (integerArray[4] != null) {
            player2.setPvpKills(integerArray[4]);
            player2.broadcastUserInfo(true, UserInfoType.SOCIAL);
        }
        if (integerArray[5] != null) {
            player2.setClassId(integerArray[5], true, false);
            player2.broadcastUserInfo(true, UserInfoType.BASIC_INFO);
        }
        if (integerArray[6] != null) {
            player2.setPkKills(integerArray[6]);
            player2.broadcastUserInfo(true, UserInfoType.SOCIAL);
        }
        this.s(player);
        player2.decayMe();
        player2.spawnMe(player.getLoc());
    }

    private void s(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer()) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Editing character: " + player2.getName() + "</center><br>");
        stringBuilder.append("<table width=250>");
        stringBuilder.append("<tr><td width=40></td><td width=70>Curent:</td><td width=70>Max:</td><td width=70></td></tr>");
        stringBuilder.append("<tr><td width=40>HP:</td><td width=70>" + player2.getCurrentHp() + "</td><td width=70>" + player2.getMaxHp() + "</td><td width=70>Karma: " + player2.getKarma() + "</td></tr>");
        stringBuilder.append("<tr><td width=40>MP:</td><td width=70>" + player2.getCurrentMp() + "</td><td width=70>" + player2.getMaxMp() + "</td><td width=70>Pvp Kills: " + player2.getPvpKills() + "</td></tr>");
        stringBuilder.append("<tr><td width=40>Load:</td><td width=70>" + player2.getCurrentLoad() + "</td><td width=70>" + player2.getMaxLoad() + "</td><td width=70>Pvp Flag: " + player2.getPvpFlag() + "</td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("<table width=270><tr><td>Class<?> Template Id: " + player2.getClassId() + "/" + player2.getClassId().getId() + "</td></tr></table><br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td>Note: Fill all values before saving the modifications.</td></tr>");
        stringBuilder.append("</table><br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=50>Hp:</td><td><edit var=\"hp\" width=50></td><td width=50>Mp:</td><td><edit var=\"mp\" width=50></td></tr>");
        stringBuilder.append("<tr><td width=50>Pvp Flag:</td><td><edit var=\"pvpflag\" width=50></td><td width=50>Karma:</td><td><edit var=\"karma\" width=50></td></tr>");
        stringBuilder.append("<tr><td width=50>Class<?> Id:</td><td><edit var=\"classid\" width=50></td><td width=50>Pvp Kills:</td><td><edit var=\"pvpkills\" width=50></td></tr>");
        stringBuilder.append("<tr><td width=50>Pk Kills:</td><td><edit var=\"pkkills\" width=50></td></tr>");
        stringBuilder.append("</table><br>");
        stringBuilder.append("<center><button value=\"Save Changes\" action=\"bypass -h admin_save_modifications $hp & $mp & $karma & $pvpflag & $pvpkills & $classid & $pkkills &\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></center><br>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void t(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer()) {
            return;
        }
        Player player2 = (Player)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table><br><br>");
        stringBuilder.append("<center>Admin Actions for: " + player2.getName() + "</center><br>");
        stringBuilder.append("<center><table width=200><tr>");
        stringBuilder.append("<td width=100>Argument(*):</td><td width=100><edit var=\"arg\" width=100></td>");
        stringBuilder.append("</tr></table><br></center>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td width=90><button value=\"Teleport\" action=\"bypass -h admin_teleportto " + player2.getName() + "\" width=85 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=90><button value=\"Recall\" action=\"bypass -h admin_recall " + player2.getName() + "\" width=85 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=90><button value=\"Quests\" action=\"bypass -h admin_quests " + player2.getName() + "\" width=85 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void e(Player player, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        int n = 0;
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_characters 0\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        String string2 = string.toLowerCase();
        for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player2.getName() == null || !player2.getName().toLowerCase().startsWith(string2)) continue;
            ++n;
            stringBuilder.append("<table width=270>");
            stringBuilder.append("<tr><td width=80>Name</td><td width=110>Class</td><td width=40>Level</td></tr>");
            stringBuilder.append("<tr><td width=80><a action=\"bypass -h admin_character_list " + player2.getName() + "\">" + player2.getName() + "</a></td><td width=110>" + player2.getTemplate().className + "</td><td width=40>" + player2.getLevel() + "</td></tr>");
            stringBuilder.append("</table>");
        }
        if (n == 0) {
            stringBuilder.append("<table width=270>");
            stringBuilder.append("<tr><td width=270>Your search did not find any characters.</td></tr>");
            stringBuilder.append("<tr><td width=270>Please try again.<br></td></tr>");
            stringBuilder.append("</table><br>");
            stringBuilder.append("<center><table><tr><td>");
            stringBuilder.append("<edit var=\"character_name\" width=80></td><td><button value=\"Find\" action=\"bypass -h admin_find_character $character_name\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
            stringBuilder.append("</td></tr></table></center>");
        } else {
            stringBuilder.append("<center><br>Found " + n + " character");
            if (n == 1) {
                stringBuilder.append(".");
            } else if (n > 1) {
                stringBuilder.append("s.");
            }
        }
        stringBuilder.append("</center></body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void u(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<table width=270><tr><td>Name: " + player2.getName() + "</td></tr>");
        stringBuilder.append("<tr><td>Lv: " + player2.getLevel() + " " + player2.getTemplate().className + "</td></tr>");
        stringBuilder.append("<tr><td>Exp: " + player2.getExp() + "</td></tr>");
        stringBuilder.append("<tr><td>Sp: " + player2.getSp() + "</td></tr></table>");
        stringBuilder.append("<br><table width=270><tr><td>Note: Dont forget that modifying players skills can</td></tr>");
        stringBuilder.append("<tr><td>ruin the game...</td></tr></table><br>");
        stringBuilder.append("<table width=270><tr><td>Note: Fill all values before saving the modifications.,</td></tr>");
        stringBuilder.append("<tr><td>Note: Use 0 if no changes are needed.</td></tr></table><br>");
        stringBuilder.append("<center><table><tr>");
        stringBuilder.append("<td>Exp: <edit var=\"exp_to_add\" width=50></td>");
        stringBuilder.append("<td>Sp:  <edit var=\"sp_to_add\" width=50></td>");
        stringBuilder.append("<td>&nbsp;<button value=\"Save Changes\" action=\"bypass -h admin_add_exp_sp $exp_to_add $sp_to_add\" width=80 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(Player player, long l, int n) {
        if (!player.getPlayerAccess().CanEditCharAll) {
            player.sendMessage("You have not enough privileges, for use this function.");
            return;
        }
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        if (!gameObject.isPlayable()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Playable playable = (Playable)gameObject;
        playable.addExpAndSp(l, n);
        player.sendMessage("Added " + l + " experience and " + n + " SP to " + playable.getName() + ".");
    }

    private void a(Player player, Player player2) {
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        Set<PlayerClass> set = this.a(player2);
        if (set != null && !set.isEmpty()) {
            stringBuilder.append("Add Subclass:<br>Which subclass do you wish to add?<br>");
            for (PlayerClass playerClass : set) {
                stringBuilder.append("<a action=\"bypass -h admin_setsubclass " + playerClass.ordinal() + "\">" + this.a(playerClass) + "</a><br>");
            }
        } else {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2VillageMasterInstance.NoSubAtThisTime", player, new Object[0]));
            return;
        }
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private Set<PlayerClass> a(Player player) {
        int n = player.getBaseSubClass().getClassId();
        PlayerClass playerClass = PlayerClass.values()[n];
        Set<PlayerClass> set = playerClass.getAvailableSubclasses();
        if (set == null) {
            return null;
        }
        set.remove((Object)playerClass);
        for (PlayerClass playerClass2 : set) {
            for (SubClass subClass : player.getSubClasses().values()) {
                if (playerClass2.ordinal() == subClass.getClassId()) {
                    set.remove((Object)playerClass2);
                    continue;
                }
                ClassId classId = ClassId.VALUES[playerClass2.ordinal()].getParent();
                if (classId != null && classId.getId() == subClass.getClassId()) {
                    set.remove((Object)playerClass2);
                    continue;
                }
                ClassId classId2 = ClassId.VALUES[subClass.getClassId()].getParent();
                if (classId2 == null || classId2.getId() != playerClass2.ordinal()) continue;
                set.remove((Object)playerClass2);
            }
        }
        return set;
    }

    private String a(PlayerClass playerClass) {
        Object object = playerClass.toString();
        char[] cArray = ((String)object).toCharArray();
        for (int i = 1; i < cArray.length; ++i) {
            if (!Character.isUpperCase(cArray[i])) continue;
            object = ((String)object).substring(0, i) + " " + ((String)object).substring(i);
        }
        return object;
    }

    private static String a(Player player) {
        String string = player.getAccountName();
        if (player.isGM()) {
            return " <font color=\"17D745\">{GM}</font>";
        }
        if (player.isInOfflineMode()) {
            return " <font color=\"A0FFFF\">{Off Trade}</font>";
        }
        if (string.equals("bot_account")) {
            return " <font color=\"fff802\">{Alt Bot}</font>";
        }
        if (string.startsWith("phantom_bot_")) {
            return " <font color=\"fff802\">{AI Bot}</font>";
        }
        return "";
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_edit_character = new Commands();
        public static final /* enum */ Commands admin_character_actions = new Commands();
        public static final /* enum */ Commands admin_current_player = new Commands();
        public static final /* enum */ Commands admin_nokarma = new Commands();
        public static final /* enum */ Commands admin_setkarma = new Commands();
        public static final /* enum */ Commands admin_character_list = new Commands();
        public static final /* enum */ Commands admin_show_characters = new Commands();
        public static final /* enum */ Commands admin_show_characters_by_ip = new Commands();
        public static final /* enum */ Commands admin_show_characters_by_hwid = new Commands();
        public static final /* enum */ Commands admin_find_character = new Commands();
        public static final /* enum */ Commands admin_save_modifications = new Commands();
        public static final /* enum */ Commands admin_rec = new Commands();
        public static final /* enum */ Commands admin_settitle = new Commands();
        public static final /* enum */ Commands admin_setclass = new Commands();
        public static final /* enum */ Commands admin_setname = new Commands();
        public static final /* enum */ Commands admin_setsex = new Commands();
        public static final /* enum */ Commands admin_setcolor = new Commands();
        public static final /* enum */ Commands admin_setcolortitle = new Commands();
        public static final /* enum */ Commands admin_add_exp_sp_to_character = new Commands();
        public static final /* enum */ Commands admin_add_exp_sp = new Commands();
        public static final /* enum */ Commands admin_sethero = new Commands();
        public static final /* enum */ Commands admin_setnoble = new Commands();
        public static final /* enum */ Commands admin_trans = new Commands();
        public static final /* enum */ Commands admin_setsubclass = new Commands();
        public static final /* enum */ Commands admin_setfame = new Commands();
        public static final /* enum */ Commands admin_setbday = new Commands();
        public static final /* enum */ Commands admin_give_item = new Commands();
        public static final /* enum */ Commands admin_give_all = new Commands();
        public static final /* enum */ Commands admin_give_all_by_ip = new Commands();
        public static final /* enum */ Commands admin_give_all_by_hwid = new Commands();
        public static final /* enum */ Commands admin_give_all_radius = new Commands();
        public static final /* enum */ Commands admin_add_wp = new Commands();
        public static final /* enum */ Commands admin_remove_item = new Commands();
        public static final /* enum */ Commands admin_destroy_items = new Commands();
        public static final /* enum */ Commands admin_add_bang = new Commands();
        public static final /* enum */ Commands admin_add_vip_points = new Commands();
        public static final /* enum */ Commands admin_set_bang = new Commands();
        public static final /* enum */ Commands admin_set_raidpoints = new Commands();
        public static final /* enum */ Commands admin_add_raidpoints = new Commands();
        public static final /* enum */ Commands admin_set_pa = new Commands();
        public static final /* enum */ Commands admin_set_aug = new Commands();
        public static final /* enum */ Commands admin_unset_aug = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_edit_character, admin_character_actions, admin_current_player, admin_nokarma, admin_setkarma, admin_character_list, admin_show_characters, admin_show_characters_by_ip, admin_show_characters_by_hwid, admin_find_character, admin_save_modifications, admin_rec, admin_settitle, admin_setclass, admin_setname, admin_setsex, admin_setcolor, admin_setcolortitle, admin_add_exp_sp_to_character, admin_add_exp_sp, admin_sethero, admin_setnoble, admin_trans, admin_setsubclass, admin_setfame, admin_setbday, admin_give_item, admin_give_all, admin_give_all_by_ip, admin_give_all_by_hwid, admin_give_all_radius, admin_add_wp, admin_remove_item, admin_destroy_items, admin_add_bang, admin_add_vip_points, admin_set_bang, admin_set_raidpoints, admin_add_raidpoints, admin_set_pa, admin_set_aug, admin_unset_aug};
        }

        static {
            a = Commands.a();
        }
    }
}
