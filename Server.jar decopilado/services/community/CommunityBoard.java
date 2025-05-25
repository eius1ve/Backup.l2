/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.BuyListHolder$NpcTradeList
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.handler.items.IRefineryHandler
 *  l2.gameserver.handler.items.RefineryHandler
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Experience
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.CloseBoard
 *  l2.gameserver.network.l2.s2c.ExBuySellList$BuyList
 *  l2.gameserver.network.l2.s2c.ExBuySellList$SellRefundList
 *  l2.gameserver.network.l2.s2c.ExEnSoulExtractionShow
 *  l2.gameserver.network.l2.s2c.ExShowEnsoulWindow
 *  l2.gameserver.network.l2.s2c.PackageToList
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.scripts.Scripts
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.TimeUtils
 *  l2.gameserver.utils.WarehouseFunctions
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.handler.items.IRefineryHandler;
import l2.gameserver.handler.items.RefineryHandler;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.CloseBoard;
import l2.gameserver.network.l2.s2c.ExBuySellList;
import l2.gameserver.network.l2.s2c.ExEnSoulExtractionShow;
import l2.gameserver.network.l2.s2c.ExShowEnsoulWindow;
import l2.gameserver.network.l2.s2c.PackageToList;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.TimeUtils;
import l2.gameserver.utils.WarehouseFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.community.custom.ACbConfigManager;

public class CommunityBoard
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger ek = LoggerFactory.getLogger(CommunityBoard.class);

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            ek.info("CommunityBoard: service loaded.");
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
        return new String[]{"_bbshome", "_bbslink", "_bbsmultisell", "_bbspage", "_bbsscripts", "_bbsselllist", "_bbssaugmentation", "_bbssaugmentcancel", "_bbsscargodeposit", "_bbsscargowithdraw", "_bbs_lang_ru", "_bbs_lang_en", "_bbsclose", "_show_ensoul_window", "_show_extract_ensoul_window"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onBypassCommand(Player player, String string) {
        String string2;
        Clan clan;
        String string3;
        block35: {
            String string4;
            block32: {
                block33: {
                    ResultSet resultSet;
                    PreparedStatement preparedStatement;
                    Connection connection;
                    int n;
                    block31: {
                        StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
                        string4 = stringTokenizer.nextToken();
                        string3 = "";
                        if (!"bbshome".equals(string4)) break block32;
                        clan = new StringTokenizer(Config.BBS_DEFAULT, "_");
                        string2 = clan.nextToken();
                        if (!string2.equals(string4)) break block33;
                        string3 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_top.htm", player);
                        n = 0;
                        connection = null;
                        preparedStatement = null;
                        resultSet = null;
                        try {
                            connection = DatabaseFactory.getInstance().getConnection();
                            preparedStatement = connection.prepareStatement("SELECT count(*) as cnt FROM `bbs_favorites` WHERE `object_id` = ?");
                            preparedStatement.setInt(1, player.getObjectId());
                            resultSet = preparedStatement.executeQuery();
                            if (!resultSet.next()) break block31;
                            n = resultSet.getInt("cnt");
                        }
                        catch (Exception exception) {
                            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
                            catch (Throwable throwable) {
                                DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                                throw throwable;
                            }
                        }
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
                    string3 = string3.replace("<?fav_count?>", String.valueOf(n));
                    string3 = string3.replace("<?clan_count?>", String.valueOf(ClanTable.getInstance().getClans().length));
                    string3 = string3.replace("<?market_count?>", String.valueOf(CommunityBoardManager.getInstance().getIntProperty("col_count")));
                    break block35;
                }
                this.onBypassCommand(player, Config.BBS_DEFAULT);
                return;
            }
            if ("bbslink".equals(string4)) {
                string3 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_homepage.htm", player);
            } else if (string.startsWith("_bbspage")) {
                clan = string.split(":");
                string2 = clan[1];
                string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/" + string2 + ".htm", player);
            } else {
                if (string.startsWith("_bbsmultisell")) {
                    ICommunityBoardHandler iCommunityBoardHandler;
                    String string5;
                    StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
                    String[] stringArray = stringTokenizer.nextToken().split(":");
                    String string6 = string5 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
                    if (string5 != null && (iCommunityBoardHandler = CommunityBoardManager.getInstance().getCommunityHandler(string5, player)) != null) {
                        iCommunityBoardHandler.onBypassCommand(player, string5);
                    }
                    int n = Integer.parseInt(stringArray[1]);
                    MultiSellHolder.getInstance().SeparateAndSend(n, player, 0.0);
                    return;
                }
                if (string.equalsIgnoreCase("_bbsselllist")) {
                    BuyListHolder.NpcTradeList npcTradeList = new BuyListHolder.NpcTradeList(0);
                    player.sendPacket(new IStaticPacket[]{new ExBuySellList.BuyList(npcTradeList, player, 0.0), new ExBuySellList.SellRefundList(player, false)});
                    return;
                }
                if (string.equalsIgnoreCase("_bbs_lang_ru")) {
                    player.setVar("lang@", "ru", -1L);
                    player.sendMessage(new CustomMessage("usercommandhandlers.LangRu", player, new Object[0]));
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else if (string.equalsIgnoreCase("_bbs_lang_en")) {
                    player.setVar("lang@", "en", -1L);
                    player.sendMessage(new CustomMessage("usercommandhandlers.LangEn", player, new Object[0]));
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else if (string.equalsIgnoreCase("_bbssaugmentation") && ACbConfigManager.ALT_CB_ALLOW_AUGMENT_BBS) {
                    player.setRefineryHandler((IRefineryHandler)RefineryHandler.getInstance());
                    RefineryHandler.getInstance().onInitRefinery(player);
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else if (string.equalsIgnoreCase("_bbssaugmentcancel") && ACbConfigManager.ALT_CB_ALLOW_AUGMENT_CANCEL_BBS) {
                    player.setRefineryHandler((IRefineryHandler)RefineryHandler.getInstance());
                    RefineryHandler.getInstance().onInitRefineryCancel(player);
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else if (string.equalsIgnoreCase("_show_ensoul_window") && ACbConfigManager.ALT_CB_ALLOW_ENSOUL) {
                    player.sendPacket((IStaticPacket)ExShowEnsoulWindow.STATIC);
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else if (string.equalsIgnoreCase("_show_extract_ensoul_window") && ACbConfigManager.ALT_CB_ALLOW_ENSOUL) {
                    player.sendPacket((IStaticPacket)ExEnSoulExtractionShow.STATIC);
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/index.htm", player);
                } else {
                    if (string.equalsIgnoreCase("_bbsclose")) {
                        player.sendPacket((IStaticPacket)new CloseBoard());
                        return;
                    }
                    if (string.equalsIgnoreCase("_bbsscargodeposit")) {
                        player.sendPacket((IStaticPacket)new PackageToList(player));
                        return;
                    }
                    if (string.equalsIgnoreCase("_bbsscargowithdraw")) {
                        WarehouseFunctions.showFreightWindow((Player)player);
                        return;
                    }
                    if (string.startsWith("_bbsscripts")) {
                        Object[] objectArray;
                        ICommunityBoardHandler iCommunityBoardHandler;
                        String string7;
                        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
                        String string8 = stringTokenizer.nextToken().substring(12);
                        String string9 = string7 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
                        if (string7 != null && (iCommunityBoardHandler = CommunityBoardManager.getInstance().getCommunityHandler(string7, player)) != null) {
                            iCommunityBoardHandler.onBypassCommand(player, string7);
                        }
                        iCommunityBoardHandler = string8.split("\\s+");
                        String[] stringArray = string8.substring(iCommunityBoardHandler[0].length()).trim().split("\\s+");
                        String[] stringArray2 = iCommunityBoardHandler[0].split(":");
                        if (stringArray2.length != 2) {
                            return;
                        }
                        Scripts scripts = Scripts.getInstance();
                        String string10 = stringArray2[0];
                        String string11 = stringArray2[1];
                        if (((ICommunityBoardHandler)iCommunityBoardHandler).length == 1) {
                            objectArray = new Object[]{};
                        } else {
                            Object[] objectArray2 = new Object[1];
                            objectArray = objectArray2;
                            objectArray2[0] = stringArray;
                        }
                        scripts.callScripts(player, string10, string11, objectArray);
                        return;
                    }
                }
            }
        }
        clan = player.getClan();
        string2 = clan == null ? null : clan.getAlliance();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        string3 = string3.replace("%char_name%", player.getName());
        string3 = string3.replace("%char_title%", player.getTitle());
        string3 = string3.replace("%char_level%", String.valueOf(player.getLevel()));
        string3 = string3.replace("%char_race%", String.valueOf(player.getRace().toString()));
        string3 = string3.replace("%char_class%", String.valueOf(HtmlUtils.htmlClassName((int)player.getClassId().getId(), (Player)player)));
        string3 = string3.replace("%char_sex%", player.getSex() == 0 ? "Male" : "Female");
        string3 = string3.replace("%char_exp_percent%", String.valueOf(decimalFormat.format(Experience.getExpPercent((int)player.getLevel(), (long)player.getExp()) * 100.0)));
        string3 = string3.replace("%char_sp%", String.valueOf(player.getIntSp()));
        string3 = string3.replace("%char_pk%", String.valueOf(player.getPkKills()));
        string3 = string3.replace("%char_pvp%", String.valueOf(player.getPvpKills()));
        string3 = string3.replace("%char_karma%", String.valueOf(player.getKarma()));
        string3 = string3.replace("%char_raid_points%", String.valueOf(player.getRaidBossPoints()));
        string3 = string3.replace("%char_max_load%", String.valueOf(player.getMaxLoad()));
        string3 = string3.replace("%char_clan%", String.valueOf(String.valueOf(clan == null ? "No Clan" : clan.getName())));
        string3 = string3.replace("%char_clan_level%", String.valueOf(String.valueOf(clan == null ? "No Clan" : Integer.valueOf(clan.getLevel()))));
        string3 = string3.replace("%char_ally%", String.valueOf(String.valueOf(string2 == null ? "No Ally" : string2.getAllyName())));
        string3 = string3.replace("%char_hp%", String.valueOf(player.getMaxHp()));
        string3 = string3.replace("%char_cp%", String.valueOf(player.getMaxCp()));
        string3 = string3.replace("%char_mp%", String.valueOf(player.getMaxMp()));
        string3 = string3.replace("%char_noble%", player.isNoble() ? "True" : "False");
        string3 = string3.replace("%char_hero%", player.isHero() ? "True" : "False");
        string3 = string3.replace("%char_patk%", String.valueOf(player.getPAtk(null)));
        string3 = string3.replace("%char_patk_spd%", String.valueOf(player.getPAtkSpd()));
        string3 = string3.replace("%char_matk%", String.valueOf(player.getMAtk(null, null)));
        string3 = string3.replace("%char_matk_spd%", String.valueOf(player.getMAtkSpd()));
        string3 = string3.replace("%char_pdef%", String.valueOf(player.getPDef(null)));
        string3 = string3.replace("%char_mdef%", String.valueOf(player.getMDef(null, null)));
        string3 = string3.replace("%char_crit%", String.valueOf(player.getCriticalHit(null, null)));
        string3 = string3.replace("%char_accuracy%", String.valueOf(player.getAccuracy()));
        string3 = string3.replace("%char_evasion%", String.valueOf(player.getEvasionRate(null)));
        string3 = string3.replace("%char_str%", String.valueOf(player.getSTR()));
        string3 = string3.replace("%char_dex%", String.valueOf(player.getDEX()));
        string3 = string3.replace("%char_con%", String.valueOf(player.getCON()));
        string3 = string3.replace("%char_int%", String.valueOf(player.getINT()));
        string3 = string3.replace("%char_wit%", String.valueOf(player.getWIT()));
        string3 = string3.replace("%char_men%", String.valueOf(player.getMEN()));
        string3 = string3.replace("%server_exp%", String.valueOf(Config.RATE_XP));
        string3 = string3.replace("%server_sp%", String.valueOf(Config.RATE_SP));
        string3 = string3.replace("%server_overhit%", String.valueOf(Config.RATE_OVERHIT));
        string3 = string3.replace("%server_adena_rate%", String.valueOf(Config.RATE_DROP_ADENA));
        string3 = string3.replace("%server_drop_rate%", String.valueOf(Config.RATE_DROP_ITEMS));
        string3 = string3.replace("%server_rate_spoil%", String.valueOf(Config.RATE_DROP_SPOIL));
        string3 = string3.replace("%spoil_items_chance_rate%", String.valueOf(Config.SPOIL_ITEMS_CHANCE_RATE));
        string3 = string3.replace("%server_raid_exp%", String.valueOf(Config.RATE_RAIDBOSS_XP));
        string3 = string3.replace("%server_raid_sp%", String.valueOf(Config.RATE_RAIDBOSS_SP));
        string3 = string3.replace("%server_manor_rate%", String.valueOf(Config.RATE_MANOR));
        string3 = string3.replace("%server_fish_rate%", String.valueOf(Config.RATE_FISH_DROP_COUNT));
        string3 = string3.replace("%server_skill_cost%", String.valueOf(Config.SKILL_COST_RATE));
        string3 = string3.replace("%server_rate_herbs%", String.valueOf(Config.RATE_DROP_HERBS));
        string3 = string3.replace("%server_clan_rep%", String.valueOf(Config.RATE_CLAN_REP_SCORE));
        string3 = string3.replace("%server_quest_reward_rate%", String.valueOf(Config.RATE_QUESTS_REWARD));
        string3 = string3.replace("%server_quest_exp_sp_rate%", String.valueOf(Config.RATE_QUESTS_REWARD_EXP_SP));
        string3 = string3.replace("%server_quest_drop_rate%", String.valueOf(Config.RATE_QUESTS_DROP));
        string3 = string3.replace("%server_quest_adena_rate%", String.valueOf(Config.RATE_QUESTS_ADENA_REWARD));
        string3 = string3.replace("%server_seal_stone_rate%", String.valueOf(Config.RATE_DROP_SEAL_STONES));
        string3 = string3.replace("%server_buff_time_rate%", String.valueOf(Config.ALT_NPC_BUFFER_EFFECT_TIME == 0L ? "1.0" : Long.valueOf(Config.ALT_NPC_BUFFER_EFFECT_TIME)));
        string3 = string3.replace("%server_ch_buff_time_rate%", String.valueOf(Config.CLANHALL_BUFFTIME_MODIFIER));
        string3 = string3.replace("%server_ds_buff_time_rate%", String.valueOf(Config.SONGDANCETIME_MODIFIER));
        string3 = string3.replace("%server_enchant_max%", String.valueOf(Config.ENCHANT_MAX));
        string3 = string3.replace("%online%", String.valueOf(Math.round((double)GameObjectsStorage.getAllPlayersCount() * ACbConfigManager.ALT_CB_ONLINE_MULTIPLIER)));
        string3 = string3.replace("%online_time%", String.valueOf(TimeUnit.MILLISECONDS.toHours(player.getOnlineCurrentTime())));
        string3 = string3.replace("%offline_traders%", String.valueOf(GameObjectsStorage.getAllOfflineCount()));
        string3 = string3.replace("%premium_bonus_time%", player.getBonus().getBonusExpire() > System.currentTimeMillis() / 1000L ? TimeUtils.toSimpleFormat((long)(player.getBonus().getBonusExpire() * 1000L)) : "No Premium");
        ShowBoard.separateAndSend((String)string3, (Player)player);
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
    }
}
