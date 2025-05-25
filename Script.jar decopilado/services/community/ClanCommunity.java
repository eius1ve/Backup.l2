/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.pledge.UnitMember
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExMailArrived
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.Util
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMailArrived;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClanCommunity
extends Functions
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger ej = LoggerFactory.getLogger(ClanCommunity.class);
    private static final int bGn = 10;
    private final Listener a = new Listener();

    public void onLoad() {
        CharListenerList.addGlobal((l2.commons.listener.Listener)this.a);
        if (Config.COMMUNITYBOARD_ENABLED) {
            ej.info("CommunityBoard: Clan Community service loaded.");
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
        return new String[]{"_bbsclan", "_clbbsclan_", "_clbbslist_", "_clsearch", "_clbbsadmi", "_mailwritepledgeform", "_announcepledgewriteform", "_announcepledgeswitchshowflag", "_announcepledgewrite", "_clwriteintro", "_clwritemail"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onBypassCommand(Player player, String string) {
        block38: {
            String string2;
            StringTokenizer stringTokenizer;
            block45: {
                int n3;
                String string3;
                ResultSet resultSet;
                PreparedStatement preparedStatement;
                Connection connection;
                String string4;
                HashMap hashMap;
                block36: {
                    block44: {
                        block42: {
                            String string5;
                            ResultSet resultSet2;
                            PreparedStatement preparedStatement2;
                            Connection connection2;
                            String string6;
                            block35: {
                                block40: {
                                    String string7;
                                    ResultSet resultSet3;
                                    PreparedStatement preparedStatement3;
                                    Connection connection3;
                                    Clan clan2;
                                    int n2;
                                    block34: {
                                        block39: {
                                            int n32;
                                            CharSequence charSequence;
                                            int n4;
                                            Object object;
                                            block37: {
                                                stringTokenizer = new StringTokenizer(string, "_");
                                                string2 = stringTokenizer.nextToken();
                                                player.setSessionVar("add_fav", null);
                                                if (!"bbsclan".equals(string2)) break block37;
                                                Clan clan22 = player.getClan();
                                                if (clan22 != null && clan22.getLevel() > 1) {
                                                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                                                    return;
                                                }
                                                this.onBypassCommand(player, "_clbbslist_1_0_");
                                                break block38;
                                            }
                                            if (!"clbbslist".equals(string2)) break block39;
                                            int n5 = Integer.parseInt(stringTokenizer.nextToken());
                                            int n6 = Integer.parseInt(stringTokenizer.nextToken());
                                            String string8 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
                                            HashMap hashMap2 = Util.parseTemplate((String)HtmCache.getInstance().getNotNull("scripts/services/community/bbs_clanlist.htm", player));
                                            String string9 = (String)hashMap2.get(0);
                                            Clan clan3 = player.getClan();
                                            if (clan3 != null) {
                                                object = (String)hashMap2.get(1);
                                                object = ((String)object).replace("%PLEDGE_ID%", String.valueOf(clan3.getClanId()));
                                                object = ((String)object).replace("%MY_PLEDGE_NAME%", clan3.getLevel() > 1 ? clan3.getName() : "");
                                                string9 = string9.replace("<?my_clan_link?>", (CharSequence)object);
                                            } else {
                                                string9 = string9.replace("<?my_clan_link?>", "");
                                            }
                                            object = ClanCommunity.a(string8, n6 == 1);
                                            int n7 = (n5 - 1) * 10;
                                            int n8 = Math.min(n5 * 10, object.size());
                                            if (n5 == 1) {
                                                string9 = string9.replace("%ACTION_GO_LEFT%", "");
                                                string9 = string9.replace("%GO_LIST%", "");
                                                string9 = string9.replace("%NPAGE%", "1");
                                            } else {
                                                string9 = string9.replace("%ACTION_GO_LEFT%", "bypass _clbbslist_" + (n5 - 1) + "_" + n6 + "_" + string8);
                                                string9 = string9.replace("%NPAGE%", String.valueOf(n5));
                                                StringBuilder stringBuilder = new StringBuilder("");
                                                int n9 = n4 = n5 > 10 ? n5 - 10 : 1;
                                                while (n4 < n5) {
                                                    stringBuilder.append("<td><a action=\"bypass _clbbslist_").append(n4).append("_").append(n6).append("_").append(string8).append("\"> ").append(n4).append(" </a> </td>\n\n");
                                                    ++n4;
                                                }
                                                string9 = string9.replace("%GO_LIST%", stringBuilder.toString());
                                            }
                                            int n10 = Math.max(object.size() / 10, 1);
                                            if (object.size() > n10 * 10) {
                                                ++n10;
                                            }
                                            if (n10 > n5) {
                                                string9 = string9.replace("%ACTION_GO_RIGHT%", "bypass _clbbslist_" + (n5 + 1) + "_" + n6 + "_" + string8);
                                                n4 = Math.min(n5 + 10, n10);
                                                charSequence = new StringBuilder("");
                                                for (n32 = n5 + 1; n32 <= n4; ++n32) {
                                                    ((StringBuilder)charSequence).append("<td><a action=\"bypass _clbbslist_").append(n32).append("_").append(n6).append("_").append(string8).append("\"> ").append(n32).append(" </a> </td>\n\n");
                                                }
                                                string9 = string9.replace("%GO_LIST2%", ((StringBuilder)charSequence).toString());
                                            } else {
                                                string9 = string9.replace("%ACTION_GO_RIGHT%", "");
                                                string9 = string9.replace("%GO_LIST2%", "");
                                            }
                                            StringBuilder stringBuilder = new StringBuilder("");
                                            charSequence = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_clantpl.htm", player);
                                            for (n32 = n7; n32 < n8; ++n32) {
                                                Clan clan4 = (Clan)object.get(n32);
                                                CharSequence charSequence2 = charSequence;
                                                charSequence2 = ((String)charSequence2).replace("%action_clanhome%", "bypass _clbbsclan_" + clan4.getClanId());
                                                charSequence2 = ((String)charSequence2).replace("%clan_name%", clan4.getName());
                                                charSequence2 = ((String)charSequence2).replace("%clan_owner%", clan4.getLeaderName());
                                                charSequence2 = ((String)charSequence2).replace("%skill_level%", String.valueOf(clan4.getLevel()));
                                                charSequence2 = ((String)charSequence2).replace("%member_count%", String.valueOf(clan4.getAllSize()));
                                                stringBuilder.append((String)charSequence2);
                                            }
                                            string9 = string9.replace("%CLAN_LIST%", stringBuilder.toString());
                                            ShowBoard.separateAndSend((String)string9, (Player)player);
                                            break block38;
                                        }
                                        if (!"clbbsclan".equals(string2)) break block40;
                                        n2 = Integer.parseInt(stringTokenizer.nextToken());
                                        if (n2 == 0) {
                                            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.NOT_JOINED_IN_ANY_CLAN));
                                            this.onBypassCommand(player, "_clbbslist_1_0");
                                            return;
                                        }
                                        clan2 = ClanTable.getInstance().getClan(n2);
                                        if (clan2 == null) {
                                            this.onBypassCommand(player, "_clbbslist_1_0");
                                            return;
                                        }
                                        if (clan2.getLevel() < 2) {
                                            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_ARE_NO_COMMUNITIES_IN_MY_CLAN_CLAN_COMMUNITIES_ARE_ALLOWED_FOR_CLANS_WITH_SKILL_LEVELS_OF_2_AND_HIGHER));
                                            this.onBypassCommand(player, "_clbbslist_1_0");
                                            return;
                                        }
                                        connection3 = null;
                                        preparedStatement3 = null;
                                        resultSet3 = null;
                                        string7 = "";
                                        try {
                                            connection3 = DatabaseFactory.getInstance().getConnection();
                                            preparedStatement3 = connection3.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type = 2");
                                            preparedStatement3.setInt(1, n2);
                                            resultSet3 = preparedStatement3.executeQuery();
                                            if (!resultSet3.next()) break block34;
                                            string7 = resultSet3.getString("notice");
                                        }
                                        catch (Exception exception) {
                                            DbUtils.closeQuietly((Connection)connection3, (Statement)preparedStatement3, resultSet3);
                                            catch (Throwable throwable) {
                                                DbUtils.closeQuietly((Connection)connection3, preparedStatement3, resultSet3);
                                                throw throwable;
                                            }
                                        }
                                    }
                                    DbUtils.closeQuietly((Connection)connection3, (Statement)preparedStatement3, (ResultSet)resultSet3);
                                    HashMap hashMap3 = Util.parseTemplate((String)HtmCache.getInstance().getNotNull("scripts/services/community/bbs_clan.htm", player));
                                    String string10 = (String)hashMap3.get(0);
                                    string10 = string10.replace("%PLEDGE_ID%", String.valueOf(n2));
                                    string10 = string10.replace("%ACTION_ANN%", "");
                                    string10 = string10.replace("%ACTION_FREE%", "");
                                    string10 = player.getClanId() == n2 && player.isClanLeader() ? string10.replace("<?menu?>", (CharSequence)hashMap3.get(1)) : string10.replace("<?menu?>", "");
                                    string10 = string10.replace("%CLAN_INTRO%", string7.replace("\n", "<br1>"));
                                    string10 = string10.replace("%CLAN_NAME%", clan2.getName());
                                    string10 = string10.replace("%SKILL_LEVEL%", String.valueOf(clan2.getLevel()));
                                    string10 = string10.replace("%CLAN_MEMBERS%", String.valueOf(clan2.getAllSize()));
                                    string10 = string10.replace("%OWNER_NAME%", clan2.getLeaderName());
                                    string10 = string10.replace("%ALLIANCE_NAME%", clan2.getAlliance() != null ? clan2.getAlliance().getAllyName() : "");
                                    string10 = string10.replace("%ANN_LIST%", "");
                                    string10 = string10.replace("%THREAD_LIST%", "");
                                    ShowBoard.separateAndSend((String)string10, (Player)player);
                                    break block38;
                                }
                                if (!"clbbsadmi".equals(string2)) break block42;
                                Clan clan3 = player.getClan();
                                if (clan3 == null || clan3.getLevel() < 2 || !player.isClanLeader()) {
                                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                                    return;
                                }
                                string6 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_clanadmin.htm", player);
                                string6 = string6.replace("%PLEDGE_ID%", String.valueOf(clan3.getClanId()));
                                string6 = string6.replace("%ACTION_ANN%", "");
                                string6 = string6.replace("%ACTION_FREE%", "");
                                string6 = string6.replace("%CLAN_NAME%", clan3.getName());
                                string6 = string6.replace("%per_list%", "");
                                connection2 = null;
                                preparedStatement2 = null;
                                resultSet2 = null;
                                string5 = "";
                                try {
                                    connection2 = DatabaseFactory.getInstance().getConnection();
                                    preparedStatement2 = connection2.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type = 2");
                                    preparedStatement2.setInt(1, clan3.getClanId());
                                    resultSet2 = preparedStatement2.executeQuery();
                                    if (!resultSet2.next()) break block35;
                                    string5 = resultSet2.getString("notice");
                                }
                                catch (Exception exception) {
                                    DbUtils.closeQuietly((Connection)connection2, (Statement)preparedStatement2, resultSet2);
                                    catch (Throwable throwable) {
                                        DbUtils.closeQuietly((Connection)connection2, preparedStatement2, resultSet2);
                                        throw throwable;
                                    }
                                }
                            }
                            DbUtils.closeQuietly((Connection)connection2, (Statement)preparedStatement2, (ResultSet)resultSet2);
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("");
                            arrayList.add("0");
                            arrayList.add("");
                            arrayList.add("0");
                            arrayList.add("");
                            arrayList.add("");
                            arrayList.add(string5);
                            arrayList.add("");
                            arrayList.add("");
                            arrayList.add("0");
                            arrayList.add("0");
                            arrayList.add("");
                            player.sendPacket((IStaticPacket)new ShowBoard(string6, "1001", player));
                            player.sendPacket((IStaticPacket)new ShowBoard(arrayList));
                            break block38;
                        }
                        if (!"mailwritepledgeform".equals(string2)) break block44;
                        Clan clan4 = player.getClan();
                        if (clan4 == null || clan4.getLevel() < 2 || !player.isClanLeader()) {
                            this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                            return;
                        }
                        String string11 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_pledge_mail_write.htm", player);
                        string11 = string11.replace("%PLEDGE_ID%", String.valueOf(clan4.getClanId()));
                        string11 = string11.replace("%pledge_id%", String.valueOf(clan4.getClanId()));
                        string11 = string11.replace("%pledge_name%", clan4.getName());
                        ShowBoard.separateAndSend((String)string11, (Player)player);
                        break block38;
                    }
                    if (!"announcepledgewriteform".equals(string2)) break block45;
                    Clan clan5 = player.getClan();
                    if (clan5 == null || clan5.getLevel() < 2 || !player.isClanLeader()) {
                        this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                        return;
                    }
                    hashMap = Util.parseTemplate((String)HtmCache.getInstance().getNotNull("scripts/services/community/bbs_clanannounce.htm", player));
                    string4 = (String)hashMap.get(0);
                    string4 = string4.replace("%PLEDGE_ID%", String.valueOf(clan5.getClanId()));
                    string4 = string4.replace("%ACTION_ANN%", "");
                    string4 = string4.replace("%ACTION_FREE%", "");
                    connection = null;
                    preparedStatement = null;
                    resultSet = null;
                    string3 = "";
                    n3 = 0;
                    try {
                        connection = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type != 2");
                        preparedStatement.setInt(1, clan5.getClanId());
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) break block36;
                        string3 = resultSet.getString("notice");
                        n3 = resultSet.getInt("type");
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
                string4 = n3 == 0 ? string4.replace("<?usage?>", (CharSequence)hashMap.get(1)) : string4.replace("<?usage?>", (CharSequence)hashMap.get(2));
                string4 = string4.replace("%flag%", String.valueOf(n3));
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("");
                arrayList.add("0");
                arrayList.add("");
                arrayList.add("0");
                arrayList.add("");
                arrayList.add("");
                arrayList.add(string3);
                arrayList.add("");
                arrayList.add("");
                arrayList.add("0");
                arrayList.add("0");
                arrayList.add("");
                player.sendPacket((IStaticPacket)new ShowBoard(string4, "1001", player));
                player.sendPacket((IStaticPacket)new ShowBoard(arrayList));
                break block38;
            }
            if (!"announcepledgeswitchshowflag".equals(string2)) break block38;
            Clan clan = player.getClan();
            if (clan == null || clan.getLevel() < 2 || !player.isClanLeader()) {
                this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("UPDATE `bbs_clannotice` SET type = ? WHERE `clan_id` = ? and type = ?");
                preparedStatement.setInt(1, n);
                preparedStatement.setInt(2, clan.getClanId());
                preparedStatement.setInt(3, n == 1 ? 0 : 1);
                preparedStatement.execute();
            }
            catch (Exception exception) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement);
                    throw throwable;
                }
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
            clan.setNotice(n == 0 ? "" : null);
            this.onBypassCommand(player, "_announcepledgewriteform");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
        block28: {
            String string7;
            StringTokenizer stringTokenizer;
            block31: {
                block29: {
                    block27: {
                        stringTokenizer = new StringTokenizer(string, "_");
                        string7 = stringTokenizer.nextToken();
                        if (!"clsearch".equals(string7)) break block27;
                        if (string4 == null) {
                            string4 = "";
                        }
                        this.onBypassCommand(player, "_clbbslist_1_" + ("Ruler".equals(string5) ? "1" : "0") + "_" + string4);
                        break block28;
                    }
                    if (!"clwriteintro".equals(string7)) break block29;
                    Clan clan2 = player.getClan();
                    if (clan2 == null || clan2.getLevel() < 2 || !player.isClanLeader() || string4 == null || string4.isEmpty()) {
                        this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                        return;
                    }
                    string4 = string4.replace("<", "");
                    string4 = string4.replace(">", "");
                    string4 = string4.replace("&", "");
                    string4 = string4.replace("$", "");
                    string4 = string4.replace("\"", "&quot;");
                    string4 = HtmlUtils.sanitizeHtml((String)string4, (int)3000);
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    try {
                        connection = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = connection.prepareStatement("REPLACE INTO `bbs_clannotice`(clan_id, type, notice) VALUES(?, ?, ?)");
                        preparedStatement.setInt(1, clan2.getClanId());
                        preparedStatement.setInt(2, 2);
                        preparedStatement.setString(3, string4);
                        preparedStatement.execute();
                    }
                    catch (Exception exception) {
                        DbUtils.closeQuietly((Connection)connection, preparedStatement);
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)connection, preparedStatement);
                            throw throwable;
                        }
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                    break block28;
                }
                if (!"clwritemail".equals(string7)) break block31;
                Clan clan3 = player.getClan();
                if (clan3 == null || clan3.getLevel() < 2 || !player.isClanLeader()) {
                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                    return;
                }
                if (string4 == null || string5 == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_MESSAGE_WAS_NOT_SENT);
                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                    return;
                }
                string4 = string4.replace("<", "");
                string4 = string4.replace(">", "");
                string4 = string4.replace("&", "");
                string4 = string4.replace("$", "");
                string4 = string4.replace("\"", "&quot;");
                string6 = string6.replace("<", "");
                string6 = string6.replace(">", "");
                string6 = string6.replace("&", "");
                string6 = string6.replace("$", "");
                string6 = string6.replace("\"", "&quot;");
                if (string4.isEmpty() || string5.isEmpty()) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_MESSAGE_WAS_NOT_SENT);
                    this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                    return;
                }
                if (string4.length() > 128) {
                    string4 = string4.substring(0, 128);
                }
                if (string5.length() > 3000) {
                    string6 = string6.substring(0, 3000);
                }
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("INSERT INTO `bbs_mail`(to_name, to_object_id, from_name, from_object_id, title, message, post_date, box_type) VALUES(?, ?, ?, ?, ?, ?, ?, 0)");
                    for (UnitMember unitMember : clan3) {
                        preparedStatement.setString(1, clan3.getName());
                        preparedStatement.setInt(2, unitMember.getObjectId());
                        preparedStatement.setString(3, player.getName());
                        preparedStatement.setInt(4, player.getObjectId());
                        preparedStatement.setString(5, string4);
                        preparedStatement.setString(6, string6);
                        preparedStatement.setInt(7, (int)(System.currentTimeMillis() / 1000L));
                        preparedStatement.execute();
                    }
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("INSERT INTO `bbs_mail`(to_name, to_object_id, from_name, from_object_id, title, message, post_date, box_type) VALUES(?, ?, ?, ?, ?, ?, ?, 1)");
                    preparedStatement.setString(1, clan3.getName());
                    preparedStatement.setInt(2, player.getObjectId());
                    preparedStatement.setString(3, player.getName());
                    preparedStatement.setInt(4, player.getObjectId());
                    preparedStatement.setString(5, string4);
                    preparedStatement.setString(6, string6);
                    preparedStatement.setInt(7, (int)(System.currentTimeMillis() / 1000L));
                    preparedStatement.execute();
                }
                catch (Exception exception) {
                    try {
                        player.sendPacket((IStaticPacket)SystemMsg.THE_MESSAGE_WAS_NOT_SENT);
                        this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                    }
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly((Connection)connection, preparedStatement);
                        throw throwable;
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                    return;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                player.sendPacket((IStaticPacket)SystemMsg.YOUVE_SENT_MAIL);
                for (UnitMember unitMember : clan3.getOnlineMembers(0)) {
                    unitMember.sendPacket((IStaticPacket)SystemMsg.YOUVE_GOT_MAIL);
                    unitMember.sendPacket((IStaticPacket)ExMailArrived.STATIC);
                }
                this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                break block28;
            }
            if (!"announcepledgewrite".equals(string7)) break block28;
            Clan clan = player.getClan();
            if (clan == null || clan.getLevel() < 2 || !player.isClanLeader()) {
                this.onBypassCommand(player, "_clbbsclan_" + player.getClanId());
                return;
            }
            if (string4 == null || string4.isEmpty()) {
                this.onBypassCommand(player, "_announcepledgewriteform");
                return;
            }
            string4 = string4.replace("<", "");
            string4 = string4.replace(">", "");
            string4 = string4.replace("&", "");
            string4 = string4.replace("$", "");
            if ((string4 = string4.replace("\"", "&quot;")).isEmpty()) {
                this.onBypassCommand(player, "_announcepledgewriteform");
                return;
            }
            if (string4.length() > 3000) {
                string4 = string4.substring(0, 3000);
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("REPLACE INTO `bbs_clannotice`(clan_id, type, notice) VALUES(?, ?, ?)");
                preparedStatement.setInt(1, clan.getClanId());
                preparedStatement.setInt(2, n);
                preparedStatement.setString(3, string4);
                preparedStatement.execute();
            }
            catch (Exception exception) {
                try {
                    exception.printStackTrace();
                    this.onBypassCommand(player, "_announcepledgewriteform");
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                return;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
            if (n == 1) {
                clan.setNotice(string4.replace("\n", "<br1>"));
            } else {
                clan.setNotice("");
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_CLAN_NOTICE_HAS_BEEN_SAVED);
            this.onBypassCommand(player, "_announcepledgewriteform");
        }
    }

    private static List<Clan> a(String string, boolean bl) {
        ArrayList arrayList = new ArrayList();
        Clan[] clanArray = ClanTable.getInstance().getClans();
        Arrays.sort(clanArray, new ClansComparator());
        for (Clan clan : clanArray) {
            if (clan.getLevel() <= 1) continue;
            arrayList.add((Clan)clan);
        }
        if (!string.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<Clan> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                Clan clan = iterator.next();
                if (bl && clan.getLeaderName().toLowerCase().contains(string.toLowerCase())) {
                    arrayList2.add(clan);
                    continue;
                }
                if (bl || !clan.getName().toLowerCase().contains(string.toLowerCase())) continue;
                arrayList2.add(clan);
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    private class Listener
    implements OnPlayerEnterListener {
        private Listener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Loose catch block
         */
        public void onPlayerEnter(Player player) {
            Object object;
            Clan clan;
            block7: {
                int n;
                String string;
                ResultSet resultSet;
                PreparedStatement preparedStatement;
                block6: {
                    clan = player.getClan();
                    if (clan == null || clan.getLevel() < 2) {
                        return;
                    }
                    if (clan.getNotice() != null) break block7;
                    object = null;
                    preparedStatement = null;
                    resultSet = null;
                    string = "";
                    n = 0;
                    try {
                        object = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = object.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type != 2");
                        preparedStatement.setInt(1, clan.getClanId());
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) break block6;
                        string = resultSet.getString("notice");
                        n = resultSet.getInt("type");
                    }
                    catch (Exception exception) {
                        DbUtils.closeQuietly((Connection)object, (Statement)preparedStatement, resultSet);
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)object, preparedStatement, resultSet);
                            throw throwable;
                        }
                    }
                }
                DbUtils.closeQuietly((Connection)object, (Statement)preparedStatement, (ResultSet)resultSet);
                clan.setNotice(n == 1 ? string.replace("\n", "<br1>\n") : "");
            }
            if (!clan.getNotice().isEmpty()) {
                object = HtmCache.getInstance().getNotNull("scripts/services/community/clan_popup.htm", player);
                object = ((String)object).replace("%pledge_name%", clan.getName());
                object = ((String)object).replace("%content%", clan.getNotice());
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(0).setHtml((String)object));
            }
        }
    }

    private static class ClansComparator<T>
    implements Comparator<T> {
        private ClansComparator() {
        }

        @Override
        public int compare(Object object, Object object2) {
            if (object instanceof Clan && object2 instanceof Clan) {
                Clan clan = (Clan)object;
                Clan clan2 = (Clan)object2;
                return clan.getName().compareTo(clan2.getName());
            }
            return 0;
        }
    }
}
