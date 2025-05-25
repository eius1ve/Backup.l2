/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageMemo
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger em = LoggerFactory.getLogger(ManageMemo.class);
    private static final int bGo = 12;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String a(Player player, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder("");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            if (n2 > 0) {
                int n3 = (n - 1) * 12;
                int n4 = n * 12;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT memo_id,title,post_date FROM `bbs_memo` WHERE `account_name` = ? ORDER BY post_date DESC LIMIT " + n3 + "," + n4);
                preparedStatement.setString(1, player.getAccountName());
                resultSet = preparedStatement.executeQuery();
                String string = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_memo_post.htm", player);
                while (resultSet.next()) {
                    String string2 = string;
                    string2 = string2.replace("%memo_id%", String.valueOf(resultSet.getInt("memo_id")));
                    string2 = string2.replace("%memo_title%", ManageMemo.j(resultSet.getString("title")));
                    string2 = string2.replace("%page%", String.valueOf(n));
                    string2 = string2.replace("%memo_date%", String.format("%1$te-%1$tm-%1$tY", new Date((long)resultSet.getInt("post_date") * 1000L)));
                    stringBuilder.append(string2);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private static int d(Player player) {
        int n;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            n = 0;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT count(*) as cnt FROM bbs_memo WHERE `account_name` = ?");
                preparedStatement.setString(1, player.getAccountName());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
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
        return n;
    }

    private static String j(String string) {
        return StringUtils.replaceEach((String)StringUtils.defaultString((String)string), (String[])new String[]{"<", ">", "&", "$", "\""}, (String[])new String[]{"&$740;", "&$740;", "&$740;", "&$740;", "&$740;"});
    }

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            em.info("CommunityBoard: Manage Memo service loaded.");
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
        return new String[]{"_bbsmemo", "_mmread_", "_mmlist_", "_mmcrea", "_mmwrite", "_mmmodi_", "_mmdele"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onBypassCommand(Player player, String string) {
        String string2;
        block23: {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            Connection connection;
            String string3;
            block21: {
                String string4;
                StringTokenizer stringTokenizer;
                block27: {
                    block25: {
                        ResultSet resultSet2;
                        PreparedStatement preparedStatement2;
                        Connection connection2;
                        block20: {
                            block24: {
                                block22: {
                                    stringTokenizer = new StringTokenizer(string, "_");
                                    string4 = stringTokenizer.nextToken();
                                    player.setSessionVar("add_fav", null);
                                    string2 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_memo_list.htm", player);
                                    if (!"bbsmemo".equals(string4) && !"_mmlist_1".equals(string)) break block22;
                                    int n2 = ManageMemo.d(player);
                                    string2 = string2.replace("%memo_list%", ManageMemo.a(player, 1, n2));
                                    string2 = string2.replace("%prev_page%", "");
                                    string2 = string2.replace("%page%", "1");
                                    Object object2 = "<td>1</td>\n\n";
                                    if (n2 > 12) {
                                        int n22 = n2 / 12;
                                        if (n2 % 12 != 0) {
                                            ++n22;
                                        }
                                        string2 = string2.replace("%next_page%", "bypass _mmlist_2");
                                        for (int i = 2; i <= n22; ++i) {
                                            object2 = (String)object2 + "<td><a action=\"bypass _mmlist_" + i + "\"> " + i + " </a></td>\n\n";
                                        }
                                    } else {
                                        string2 = string2.replace("%next_page%", "");
                                    }
                                    string2 = string2.replace("%pages%", (CharSequence)object2);
                                    break block23;
                                }
                                if (!"mmlist".equals(string4)) break block24;
                                int n = Integer.parseInt(stringTokenizer.nextToken());
                                int n3 = ManageMemo.d(player);
                                string2 = string2.replace("%memo_list%", ManageMemo.a(player, n, n3));
                                string2 = string2.replace("%prev_page%", "bypass _mmlist_" + (n - 1));
                                string2 = string2.replace("%page%", String.valueOf(n));
                                Object object = "";
                                int n4 = n3 / 12;
                                if (n3 % 12 != 0) {
                                    ++n4;
                                }
                                string2 = n3 > n * 12 ? string2.replace("%next_page%", "bypass _mmlist_" + (n + 1)) : string2.replace("%next_page%", "");
                                for (int i = 1; i <= n4; ++i) {
                                    object = i == n ? (String)object + "<td>" + i + "</td>\n\n" : (String)object + "<td height=15><a action=\"bypass _mmlist_" + i + "\"> " + i + " </a></td>\n\n";
                                }
                                string2 = string2.replace("%pages%", (CharSequence)object);
                                break block23;
                            }
                            if ("mmcrea".equals(string4)) {
                                if (ManageMemo.d(player) >= 100) {
                                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MEMO_BOX_IS_FULL__THERE_IS_A_100_MEMO_LIMIT));
                                    this.onBypassCommand(player, "_mmlist_1");
                                    return;
                                }
                                String string5 = stringTokenizer.nextToken();
                                string2 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_memo_edit.htm", player);
                                string2 = string2.replace("%page%", string5);
                                string2 = string2.replace("%memo_id%", "0");
                                string2 = player.isLangRus() ? string2.replace("%TREE%", "&nbsp;>&nbsp;\u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u0437\u0430\u043f\u0438\u0441\u043a\u0438") : string2.replace("%TREE%", "&nbsp;>&nbsp;Create a Note");
                                player.sendPacket((IStaticPacket)new ShowBoard(string2, "1001", player));
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
                                arrayList.add("");
                                arrayList.add("1970-01-01 00:00:00 ");
                                arrayList.add("1970-01-01 00:00:00 ");
                                arrayList.add("0");
                                arrayList.add("0");
                                arrayList.add("");
                                player.sendPacket((IStaticPacket)new ShowBoard(arrayList));
                                return;
                            }
                            if (!"mmread".equals(string4)) break block25;
                            int n3 = Integer.parseInt(stringTokenizer.nextToken());
                            String string6 = stringTokenizer.nextToken();
                            connection2 = null;
                            preparedStatement2 = null;
                            resultSet2 = null;
                            try {
                                connection2 = DatabaseFactory.getInstance().getConnection();
                                preparedStatement2 = connection2.prepareStatement("SELECT * FROM `bbs_memo` WHERE `account_name` = ? and memo_id = ?");
                                preparedStatement2.setString(1, player.getAccountName());
                                preparedStatement2.setInt(2, n3);
                                resultSet2 = preparedStatement2.executeQuery();
                                if (!resultSet2.next()) break block20;
                                String string7 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_memo_read.htm", player);
                                string7 = string7.replace("%title%", ManageMemo.j(resultSet2.getString("title")));
                                string7 = string7.replace("%char_name%", resultSet2.getString("char_name"));
                                string7 = string7.replace("%post_date%", String.format("%1$tY-%1$tm-%1$te %1$tH:%1tM:%1$tS", new Date((long)resultSet2.getInt("post_date") * 1000L)));
                                string7 = string7.replace("%memo%", ManageMemo.j(resultSet2.getString("memo")).replace("\n", "<br1>"));
                                string7 = string7.replace("%page%", string6);
                                string7 = string7.replace("%memo_id%", String.valueOf(n3));
                                ShowBoard.separateAndSend((String)string7, (Player)player);
                            }
                            catch (Exception exception) {
                                DbUtils.closeQuietly((Connection)connection2, (Statement)preparedStatement2, resultSet2);
                                catch (Throwable throwable) {
                                    DbUtils.closeQuietly((Connection)connection2, preparedStatement2, resultSet2);
                                    throw throwable;
                                }
                            }
                            DbUtils.closeQuietly((Connection)connection2, (Statement)preparedStatement2, (ResultSet)resultSet2);
                            return;
                        }
                        DbUtils.closeQuietly((Connection)connection2, (Statement)preparedStatement2, (ResultSet)resultSet2);
                        this.onBypassCommand(player, "_bbsmemo");
                        return;
                    }
                    if (!"mmdele".equals(string4)) break block27;
                    int n5 = Integer.parseInt(stringTokenizer.nextToken());
                    Connection connection3 = null;
                    PreparedStatement preparedStatement3 = null;
                    try {
                        connection3 = DatabaseFactory.getInstance().getConnection();
                        preparedStatement3 = connection3.prepareStatement("DELETE FROM `bbs_memo` WHERE `account_name` = ? and memo_id = ?");
                        preparedStatement3.setString(1, player.getAccountName());
                        preparedStatement3.setInt(2, n5);
                        preparedStatement3.execute();
                    }
                    catch (Exception exception) {
                        DbUtils.closeQuietly((Connection)connection3, preparedStatement3);
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)connection3, preparedStatement3);
                            throw throwable;
                        }
                    }
                    DbUtils.closeQuietly((Connection)connection3, (Statement)preparedStatement3);
                    this.onBypassCommand(player, "_mmlist_1");
                    return;
                }
                if (!"mmmodi".equals(string4)) break block23;
                int n6 = Integer.parseInt(stringTokenizer.nextToken());
                string3 = stringTokenizer.nextToken();
                connection = null;
                preparedStatement = null;
                resultSet = null;
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_memo` WHERE `account_name` = ? and memo_id = ?");
                    preparedStatement.setString(1, player.getAccountName());
                    preparedStatement.setInt(2, n6);
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) break block21;
                    string2 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_memo_edit.htm", player);
                    string2 = string2.replace("%page%", string3);
                    string2 = string2.replace("%memo_id%", String.valueOf(n6));
                    string2 = player.isLangRus() ? string2.replace("%TREE%", "&nbsp;>&nbsp;<a action=\"bypass _mmread_" + n6 + "_" + string3 + "\">\u0417\u0430\u043f\u0438\u0441\u043a\u0430: " + ManageMemo.j(resultSet.getString("title")) + "</a>&nbsp;>&nbsp;\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435") : string2.replace("%TREE%", "&nbsp;>&nbsp;<a action=\"bypass _mmread_" + n6 + "_" + string3 + "\">Note: " + ManageMemo.j(resultSet.getString("title")) + "</a>&nbsp;>&nbsp;Editing");
                    player.sendPacket((IStaticPacket)new ShowBoard(string2, "1001", player));
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add("0");
                    arrayList.add("0");
                    arrayList.add(String.valueOf(n6));
                    arrayList.add("0");
                    arrayList.add("0");
                    arrayList.add("0");
                    arrayList.add(player.getName());
                    arrayList.add("0");
                    arrayList.add(player.getAccountName());
                    arrayList.add("0");
                    arrayList.add(ManageMemo.j(resultSet.getString("title")));
                    arrayList.add(ManageMemo.j(resultSet.getString("title")));
                    arrayList.add(ManageMemo.j(resultSet.getString("memo")));
                    arrayList.add(String.format("%1$tY-%1$tm-%1$te %1$tH:%1tM:%1$tS", new Date((long)resultSet.getInt("post_date") * 1000L)));
                    arrayList.add(String.format("%1$tY-%1$tm-%1$te %1$tH:%1tM:%1$tS", new Date((long)resultSet.getInt("post_date") * 1000L)));
                    arrayList.add("0");
                    arrayList.add("0");
                    arrayList.add("");
                    player.sendPacket((IStaticPacket)new ShowBoard(arrayList));
                }
                catch (Exception exception) {
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                        throw throwable;
                    }
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
                return;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
            this.onBypassCommand(player, "_mmlist_" + string3);
            return;
        }
        ShowBoard.separateAndSend((String)string2, (Player)player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
        String string7 = stringTokenizer.nextToken();
        if ("mmwrite".equals(string7)) {
            if (ManageMemo.d(player) >= 100) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MEMO_BOX_IS_FULL__THERE_IS_A_100_MEMO_LIMIT));
                this.onBypassCommand(player, "_mmlist_1");
                return;
            }
            if (string4 != null && !string4.isEmpty() && string5 != null && !string5.isEmpty()) {
                String string8;
                String string9 = ManageMemo.j(string4);
                if (string9.length() > 128) {
                    string9 = string9.substring(0, 128);
                }
                if ((string8 = ManageMemo.j(string5)).length() > 1000) {
                    string8 = string8.substring(0, 1000);
                }
                int n = 0;
                if (string3 != null && !string3.isEmpty()) {
                    n = Integer.parseInt(string3);
                }
                if (string9.length() > 0 && string8.length() > 0) {
                    PreparedStatement preparedStatement;
                    Connection connection;
                    block12: {
                        connection = null;
                        preparedStatement = null;
                        try {
                            connection = DatabaseFactory.getInstance().getConnection();
                            if (n > 0) {
                                preparedStatement = connection.prepareStatement("UPDATE bbs_memo SET title = ?, memo = ? WHERE memo_id = ? AND account_name = ?");
                                preparedStatement.setString(1, string9);
                                preparedStatement.setString(2, string8);
                                preparedStatement.setInt(3, n);
                                preparedStatement.setString(4, player.getAccountName());
                                preparedStatement.execute();
                                break block12;
                            }
                            preparedStatement = connection.prepareStatement("INSERT INTO bbs_memo(account_name, char_name, ip, title, memo, post_date) VALUES(?, ?, ?, ?, ?, ?)");
                            preparedStatement.setString(1, player.getAccountName());
                            preparedStatement.setString(2, player.getName());
                            preparedStatement.setString(3, player.getNetConnection().getIpAddr());
                            preparedStatement.setString(4, string9);
                            preparedStatement.setString(5, string8);
                            preparedStatement.setInt(6, (int)(System.currentTimeMillis() / 1000L));
                            preparedStatement.execute();
                        }
                        catch (Exception exception) {
                            DbUtils.closeQuietly((Connection)connection, preparedStatement);
                            catch (Throwable throwable) {
                                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                                throw throwable;
                            }
                        }
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                }
            }
        }
        this.onBypassCommand(player, "_bbsmemo");
    }
}
