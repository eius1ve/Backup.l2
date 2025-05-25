/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.dao.CharacterDAO
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.CategoryData
 *  l2.gameserver.model.base.Experience
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExBuySellList$BuyList
 *  l2.gameserver.network.l2.s2c.ExBuySellList$SellRefundList
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Util
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExBuySellList;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.community.custom.ACbConfigManager;
import services.community.custom.CommunityTools;

public class CommunityServices
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger er = LoggerFactory.getLogger(CommunityServices.class);

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            er.info("CommunityBoard: CommunityServices loaded.");
            CommunityBoardManager.getInstance().registerHandler((ICommunityBoardHandler)this);
        }
    }

    public void onReload() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            CommunityBoardManager.getInstance().removeHandler((ICommunityBoardHandler)this);
        }
    }

    public void onShutdown() {
    }

    public String[] getBypassCommands() {
        return new String[]{"_cbbsservicesdelvl", "_cbbsservicesbuynoble", "_cbbsserviceschangesex", "_cbbsserviceschangename", "_bbssell", "_bbsclanup", "_bbsclanexpire"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onBypassCommand(Player player, String string) {
        String string2;
        block33: {
            block36: {
                block35: {
                    block34: {
                        block32: {
                            if (!CommunityTools.checkConditions(player)) {
                                String string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
                                string3 = string3.replace("%name%", player.getName());
                                ShowBoard.separateAndSend((String)string3, (Player)player);
                                return;
                            }
                            StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
                            String string4 = stringTokenizer.nextToken();
                            string2 = "";
                            if (!"_bbssell".equals(string4)) break block32;
                            if (!player.getPlayerAccess().UseShop) {
                                player.sendMessage("\u0412\u0430\u043c \u0437\u0430\u043f\u0440\u0435\u0449\u0435\u043d\u043e \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u043c\u0430\u0433\u0430\u0437\u0438\u043d!");
                                return;
                            }
                            player.sendPacket(new IStaticPacket[]{new ExBuySellList.BuyList(null, player, 0.0), new ExBuySellList.SellRefundList(player, false)});
                            break block33;
                        }
                        if (!string.startsWith("_cbbsservicesdelvl")) break block34;
                        if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_DELVLV_ITEM_ID) < ACbConfigManager.ALT_CB_DELVL_ITEM_COUNT) {
                            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                            return;
                        }
                        if (player.getLevel() < 3 || (long)player.getLevel() > player.getMaxExp()) {
                            return;
                        }
                        Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_DELVLV_ITEM_ID, (long)ACbConfigManager.ALT_CB_DELVL_ITEM_COUNT);
                        player.addExpAndSp(Experience.LEVEL[player.getLevel() - 2] - player.getExp(), 0L, 0L, 0L, false, false);
                        break block33;
                    }
                    if (!string.startsWith("_cbbsservicesbuynoble")) break block35;
                    if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_NOBLES_ITEM_ID) < ACbConfigManager.ALT_CB_NOBLES_ITEM_COUNT) {
                        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                        return;
                    }
                    if (player.isNoble()) {
                        player.sendMessage("You already have a noble status.");
                        return;
                    }
                    if (player.getLevel() < 76) {
                        player.sendMessage("You must be 76 lvl or greater.");
                        return;
                    }
                    if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player)) {
                        player.sendMessage("You must have a 3rd profession");
                        return;
                    }
                    Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_NOBLES_ITEM_ID, (long)ACbConfigManager.ALT_CB_NOBLES_ITEM_COUNT);
                    NoblesController.getInstance().addNoble(player);
                    player.setNoble(true);
                    player.updatePledgeClass();
                    player.updateNobleSkills();
                    player.sendSkillList();
                    player.broadcastUserInfo(true, new UserInfoType[0]);
                    player.sendMessage("Congratulation! You become a nobles.");
                    break block33;
                }
                if (!string.startsWith("_cbbsserviceschangesex")) break block36;
                if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_CHANGESEX_ITEM_ID) < ACbConfigManager.ALT_CB_CHANGESEX_ITEM_COUNT) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    return;
                }
                Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_CHANGESEX_ITEM_ID, (long)ACbConfigManager.ALT_CB_CHANGESEX_ITEM_COUNT);
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("UPDATE characters SET sex = ? WHERE obj_Id = ?");
                    preparedStatement.setInt(1, player.getSex() == 1 ? 0 : 1);
                    preparedStatement.setInt(2, player.getObjectId());
                    preparedStatement.executeUpdate();
                }
                catch (Exception exception) {
                    try {
                        exception.printStackTrace();
                    }
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly((Connection)connection, preparedStatement);
                        throw throwable;
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                    return;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                player.setHairColor(0);
                player.setHairStyle(0);
                player.setFace(0);
                player.logout();
                break block33;
            }
            if (string.startsWith("_cbbsserviceschangename")) {
                if (player.isHero()) {
                    player.sendMessage("Rename is unavailable for hero character.");
                    return;
                }
                if (player.getEvent(SiegeEvent.class) != null) {
                    player.sendMessage("Rename is unavailable in siege period.");
                    return;
                }
                String[] stringArray = null;
                try {
                    stringArray = string.split(" ");
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (stringArray == null || stringArray.length != 2 || stringArray[1] == null) {
                    player.sendMessage("Incorrect name.");
                    return;
                }
                String string5 = stringArray[1];
                if (!Util.isMatchingRegexp((String)string5, (String)ACbConfigManager.ALT_CB_CUSTOM_CNAME_TEMPLATE) || string5.length() > Config.CNAME_MAXLEN) {
                    player.sendMessage("Incorrect name.");
                    return;
                }
                if (CharacterDAO.getInstance().getObjectIdByName(string5) > 0) {
                    player.sendMessage("Name already used.");
                    return;
                }
                if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_CHANGENAME_ITEM_ID) < ACbConfigManager.ALT_CB_CHANGENAME_ITEM_COUNT) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    return;
                }
                Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_CHANGENAME_ITEM_ID, (long)ACbConfigManager.ALT_CB_CHANGENAME_ITEM_COUNT);
                String string6 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/services.htm", player);
                ShowBoard.separateAndSend((String)string6, (Player)player);
                player.reName(string5, true);
                return;
            }
            if (string.startsWith("_bbsclanup")) {
                Clan clan = player.getClan();
                if (clan == null) {
                    player.sendMessage("Get clan first.");
                    return;
                }
                if (clan.getLeaderId() != player.getObjectId()) {
                    player.sendMessage("Only clan leader can do that.");
                    return;
                }
                if (clan.getLevel() < 1 || clan.getLevel() > 7) {
                    player.sendMessage("Clan level to high.");
                    return;
                }
                if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_CLANUP_ITEM_ID) < ACbConfigManager.ALT_CB_CLANUP_ITEM_COUNT) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    return;
                }
                Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_CLANUP_ITEM_ID, (long)ACbConfigManager.ALT_CB_CLANUP_ITEM_COUNT);
                clan.setLevel(clan.getLevel() + 1);
                clan.updateClanInDB();
                clan.broadcastClanStatus(true, true, true);
            } else if (string.startsWith("_bbsclanexpire")) {
                Clan clan = player.getClan();
                if (clan == null) {
                    player.sendMessage("Get clan first.");
                    return;
                }
                if (clan.getLeaderId() != player.getObjectId()) {
                    player.sendMessage("Only clan leader can do that.");
                    return;
                }
                if (Functions.getItemCount((Playable)player, (int)ACbConfigManager.ALT_CB_CLAN_PENALTY_ITEM_ID) < ACbConfigManager.ALT_CB_CLAN_PENALTY_ITEM_COUNT) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    return;
                }
                Functions.removeItem((Playable)player, (int)ACbConfigManager.ALT_CB_CLAN_PENALTY_ITEM_ID, (long)ACbConfigManager.ALT_CB_CLAN_PENALTY_ITEM_COUNT);
                player.getClan().setExpelledMemberTime(0L);
                player.sendMessage("The penalty for a clan has been lifted");
            }
        }
        ShowBoard.separateAndSend((String)string2, (Player)player);
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
    }
}
