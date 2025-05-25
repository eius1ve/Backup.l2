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
 *  l2.gameserver.network.l2.s2c.ExMailArrived
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
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
import java.util.List;
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
import l2.gameserver.network.l2.s2c.ExMailArrived;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrivateMail
extends Functions
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger en = LoggerFactory.getLogger(PrivateMail.class);
    private static final int bGp = 10;

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            en.info("CommunityBoard: Private Mail service loaded.");
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
        return new String[]{"_maillist_", "_mailsearch_", "_mailread_", "_maildelete_"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onBypassCommand(Player player, String string) {
        block39: {
            String string2;
            StringTokenizer stringTokenizer;
            block40: {
                ResultSet resultSet;
                PreparedStatement preparedStatement;
                Connection connection;
                String string3;
                int n;
                int n2;
                int n3;
                block36: {
                    block37: {
                        ResultSet resultSet2;
                        PreparedStatement preparedStatement2;
                        Connection connection2;
                        int n4;
                        int n5;
                        String string4;
                        String string5;
                        int n6;
                        int n7;
                        int n8;
                        block35: {
                            stringTokenizer = new StringTokenizer(string, "_");
                            string2 = stringTokenizer.nextToken();
                            player.setSessionVar("add_fav", null);
                            if (!"maillist".equals(string2)) break block37;
                            n8 = Integer.parseInt(stringTokenizer.nextToken());
                            n7 = Integer.parseInt(stringTokenizer.nextToken());
                            n6 = Integer.parseInt(stringTokenizer.nextToken());
                            string5 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
                            string4 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_mail_list.htm", player);
                            n5 = 0;
                            n4 = 0;
                            connection2 = null;
                            preparedStatement2 = null;
                            resultSet2 = null;
                            try {
                                connection2 = DatabaseFactory.getInstance().getConnection();
                                preparedStatement2 = connection2.prepareStatement("SELECT count(*) as cnt FROM `bbs_mail` WHERE `box_type` = 0 and `to_object_id` = ?");
                                preparedStatement2.setInt(1, player.getObjectId());
                                resultSet2 = preparedStatement2.executeQuery();
                                if (resultSet2.next()) {
                                    n5 = resultSet2.getInt("cnt");
                                }
                                preparedStatement2.close();
                                preparedStatement2 = connection2.prepareStatement("SELECT count(*) as cnt FROM `bbs_mail` WHERE `box_type` = 1 and `from_object_id` = ?");
                                preparedStatement2.setInt(1, player.getObjectId());
                                resultSet2 = preparedStatement2.executeQuery();
                                if (!resultSet2.next()) break block35;
                                n4 = resultSet2.getInt("cnt");
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
                        List<MailData> list = null;
                        switch (n8) {
                            case 0: {
                                string4 = string4.replace("%inbox_link%", "[&$917;]");
                                string4 = string4.replace("%sentbox_link%", "<a action=\"bypass _maillist_1_1_0_\">[&$918;]</a>");
                                string4 = string4.replace("%archive_link%", "<a action=\"bypass _maillist_2_1_0_\">[&$919;]</a>");
                                string4 = string4.replace("%temp_archive_link%", "<a action=\"bypass _maillist_3_1_0_\">[&$920;]</a>");
                                string4 = string4.replace("%TREE%", "&$917;");
                                string4 = string4.replace("%writer_header%", "&$911;");
                                list = PrivateMail.a(player, n8, string5, n6 == 1);
                                break;
                            }
                            case 1: {
                                string4 = string4.replace("%inbox_link%", "<a action=\"bypass _maillist_0_1_0_\">[&$917;]</a>");
                                string4 = string4.replace("%sentbox_link%", "[&$918;]");
                                string4 = string4.replace("%archive_link%", "<a action=\"bypass _maillist_2_1_0_\">[&$919;]</a>");
                                string4 = string4.replace("%temp_archive_link%", "<a action=\"bypass _maillist_3_1_0_\">[&$920;]</a>");
                                string4 = string4.replace("%TREE%", "&$918;");
                                string4 = string4.replace("%writer_header%", "&$909;");
                                list = PrivateMail.a(player, n8, string5, n6 == 1);
                                break;
                            }
                            case 2: {
                                string4 = string4.replace("%inbox_link%", "<a action=\"bypass _maillist_0_1_0_\">[&$917;]</a>");
                                string4 = string4.replace("%sentbox_link%", "<a action=\"bypass _maillist_1_1_0_\">[&$918;]</a>");
                                string4 = string4.replace("%archive_link%", "[&$919;]");
                                string4 = string4.replace("%temp_archive_link%", "<a action=\"bypass _maillist_3_1_0_\">[&$920;]</a>");
                                string4 = string4.replace("%TREE%", "&$919;");
                                string4 = string4.replace("%writer_header%", "&$911;");
                                break;
                            }
                            case 3: {
                                string4 = string4.replace("%inbox_link%", "<a action=\"bypass _maillist_0_1_0_\">[&$917;]</a>");
                                string4 = string4.replace("%sentbox_link%", "<a action=\"bypass _maillist_1_1_0_\">[&$918;]</a>");
                                string4 = string4.replace("%archive_link%", "<a action=\"bypass _maillist_2_1_0_\">[&$919;]</a>");
                                string4 = string4.replace("%temp_archive_link%", "[&$920;]");
                                string4 = string4.replace("%TREE%", "&$920;");
                                string4 = string4.replace("%writer_header%", "&$909;");
                            }
                        }
                        if (list != null) {
                            int n9;
                            CharSequence charSequence;
                            int n10;
                            int n11 = (n7 - 1) * 10;
                            int n12 = Math.min(n7 * 10, list.size());
                            if (n7 == 1) {
                                string4 = string4.replace("%ACTION_GO_LEFT%", "");
                                string4 = string4.replace("%GO_LIST%", "");
                                string4 = string4.replace("%NPAGE%", "1");
                            } else {
                                string4 = string4.replace("%ACTION_GO_LEFT%", "bypass _maillist_" + n8 + "_" + (n7 - 1) + "_" + n6 + "_" + string5);
                                string4 = string4.replace("%NPAGE%", String.valueOf(n7));
                                StringBuilder stringBuilder = new StringBuilder("");
                                int n13 = n10 = n7 > 10 ? n7 - 10 : 1;
                                while (n10 < n7) {
                                    stringBuilder.append("<td><a action=\"bypass _maillist_").append(n8).append("_").append(n10).append("_").append(n6).append("_").append(string5).append("\"> ").append(n10).append(" </a> </td>\n\n");
                                    ++n10;
                                }
                                string4 = string4.replace("%GO_LIST%", stringBuilder.toString());
                            }
                            int n14 = Math.max(list.size() / 10, 1);
                            if (list.size() > n14 * 10) {
                                ++n14;
                            }
                            if (n14 > n7) {
                                string4 = string4.replace("%ACTION_GO_RIGHT%", "bypass _maillist_" + n8 + "_" + (n7 + 1) + "_" + n6 + "_" + string5);
                                n10 = Math.min(n7 + 10, n14);
                                charSequence = new StringBuilder("");
                                for (n9 = n7 + 1; n9 <= n10; ++n9) {
                                    ((StringBuilder)charSequence).append("<td><a action=\"bypass _maillist_").append(n8).append("_").append(n9).append("_").append(n6).append("_").append(string5).append("\"> ").append(n9).append(" </a> </td>\n\n");
                                }
                                string4 = string4.replace("%GO_LIST2%", ((StringBuilder)charSequence).toString());
                            } else {
                                string4 = string4.replace("%ACTION_GO_RIGHT%", "");
                                string4 = string4.replace("%GO_LIST2%", "");
                            }
                            StringBuilder stringBuilder = new StringBuilder("");
                            charSequence = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_mailtpl.htm", player);
                            for (n9 = n11; n9 < n12; ++n9) {
                                MailData mailData = list.get(n9);
                                CharSequence charSequence2 = charSequence;
                                charSequence2 = ((String)charSequence2).replace("%action%", "bypass _mailread_" + mailData.messageId + "_" + n8 + "_" + n7 + "_" + n6 + "_" + string5);
                                charSequence2 = ((String)charSequence2).replace("%writer%", mailData.author);
                                charSequence2 = ((String)charSequence2).replace("%title%", mailData.title);
                                charSequence2 = ((String)charSequence2).replace("%post_date%", mailData.postDate);
                                stringBuilder.append((String)charSequence2);
                            }
                            string4 = string4.replace("%MAIL_LIST%", stringBuilder.toString());
                        } else {
                            string4 = string4.replace("%ACTION_GO_LEFT%", "");
                            string4 = string4.replace("%GO_LIST%", "");
                            string4 = string4.replace("%NPAGE%", "1");
                            string4 = string4.replace("%GO_LIST2%", "");
                            string4 = string4.replace("%ACTION_GO_RIGHT%", "");
                            string4 = string4.replace("%MAIL_LIST%", "");
                        }
                        string4 = string4.replace("%mailbox_type%", String.valueOf(n8));
                        string4 = string4.replace("%incomming_mail_no%", String.valueOf(n5));
                        string4 = string4.replace("%sent_mail_no%", String.valueOf(n4));
                        string4 = string4.replace("%archived_mail_no%", "0");
                        string4 = string4.replace("%temp_mail_no%", "0");
                        ShowBoard.separateAndSend((String)string4, (Player)player);
                        break block39;
                    }
                    if (!"mailread".equals(string2)) break block40;
                    int n15 = Integer.parseInt(stringTokenizer.nextToken());
                    n3 = Integer.parseInt(stringTokenizer.nextToken());
                    n2 = Integer.parseInt(stringTokenizer.nextToken());
                    n = Integer.parseInt(stringTokenizer.nextToken());
                    string3 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
                    connection = null;
                    preparedStatement = null;
                    resultSet = null;
                    try {
                        connection = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_mail` WHERE `message_id` = ? and `box_type` = ? and `to_object_id` = ?");
                        preparedStatement.setInt(1, n15);
                        preparedStatement.setInt(2, n3);
                        preparedStatement.setInt(3, player.getObjectId());
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) break block36;
                        String string6 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_mail_read.htm", player);
                        switch (n3) {
                            case 0: {
                                string6 = string6.replace("%TREE%", "<a action=\"bypass _maillist_0_1_0_\">&$917;</a>");
                                break;
                            }
                            case 1: {
                                string6 = string6.replace("%TREE%", "<a action=\"bypass _maillist_1_1_0__\">&$918;</a>");
                                break;
                            }
                            case 2: {
                                string6 = string6.replace("%TREE%", "<a action=\"bypass _maillist_2_1_0__\">&$919;</a>");
                                break;
                            }
                            case 3: {
                                string6 = string6.replace("%TREE%", "<a action=\"bypass _maillist_3_1_0__\">&$920;</a>");
                            }
                        }
                        string6 = string6.replace("%writer%", resultSet.getString("from_name"));
                        string6 = string6.replace("%post_date%", String.format("%1$te-%1$tm-%1$tY", new Date((long)resultSet.getInt("post_date") * 1000L)));
                        string6 = string6.replace("%del_date%", String.format("%1$te-%1$tm-%1$tY", new Date((long)(resultSet.getInt("post_date") + 7776000) * 1000L)));
                        string6 = string6.replace("%char_name%", resultSet.getString("to_name"));
                        string6 = string6.replace("%title%", resultSet.getString("title"));
                        string6 = string6.replace("%CONTENT%", resultSet.getString("message").replace("\n", "<br1>"));
                        string6 = string6.replace("%GOTO_LIST_LINK%", "bypass _maillist_" + n3 + "_" + n2 + "_" + n + "_" + string3);
                        string6 = string6.replace("%message_id%", String.valueOf(n15));
                        string6 = string6.replace("%mailbox_type%", String.valueOf(n3));
                        player.setSessionVar("add_fav", string + "&" + resultSet.getString("title"));
                        preparedStatement.close();
                        preparedStatement = connection.prepareStatement("UPDATE `bbs_mail` SET `read` = `read` + 1 WHERE message_id = ?");
                        preparedStatement.setInt(1, n15);
                        preparedStatement.execute();
                        ShowBoard.separateAndSend((String)string6, (Player)player);
                    }
                    catch (Exception exception) {
                        try {
                            exception.printStackTrace();
                        }
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                            throw throwable;
                        }
                        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
                    return;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
                this.onBypassCommand(player, "_maillist_" + n3 + "_" + n2 + "_" + n + "_" + string3);
                break block39;
            }
            if (!"maildelete".equals(string2)) break block39;
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int n16 = Integer.parseInt(stringTokenizer.nextToken());
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("DELETE FROM `bbs_mail` WHERE `box_type` = ? and `message_id` = ? and `to_object_id` = ?");
                preparedStatement.setInt(1, n);
                preparedStatement.setInt(2, n16);
                preparedStatement.setInt(3, player.getObjectId());
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
            this.onBypassCommand(player, "_maillist_" + n + "_1_0_");
        }
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
        String string7 = stringTokenizer.nextToken();
        if ("mailsearch".equals(string7)) {
            this.onBypassCommand(player, "_maillist_" + stringTokenizer.nextToken() + "_1_" + ("Title".equals(string4) ? "1_" : "0_") + (string6 != null ? string6 : ""));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void OnPlayerEnter(Player player) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_mail` WHERE `box_type` = 0 and `read` = 0 and `to_object_id` = ?");
                preparedStatement.setInt(1, player.getObjectId());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                player.sendPacket((IStaticPacket)SystemMsg.YOUVE_GOT_MAIL);
                player.sendPacket((IStaticPacket)ExMailArrived.STATIC);
            }
            catch (Exception exception) {
                try {
                    exception.printStackTrace();
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static List<MailData> a(Player player, int n, String string, boolean bl) {
        ArrayList<MailData> arrayList = new ArrayList<MailData>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `bbs_mail` WHERE `to_object_id` = ? and `post_date` < ?");
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, (int)(System.currentTimeMillis() / 1000L) - 7776000);
            preparedStatement.execute();
            preparedStatement.close();
            String string2 = n == 0 ? "from_name" : "to_name";
            preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_mail` WHERE `box_type` = ? and `to_object_id` = ? ORDER BY post_date DESC");
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, player.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (string.isEmpty()) {
                    arrayList.add(new MailData(resultSet.getString(string2), resultSet.getString("title"), resultSet.getInt("post_date"), resultSet.getInt("message_id")));
                    continue;
                }
                if (bl && !string.isEmpty() && resultSet.getString("title").toLowerCase().contains(string.toLowerCase())) {
                    arrayList.add(new MailData(resultSet.getString(string2), resultSet.getString("title"), resultSet.getInt("post_date"), resultSet.getInt("message_id")));
                    continue;
                }
                if (bl || string.isEmpty() || !resultSet.getString(string2).toLowerCase().contains(string.toLowerCase())) continue;
                arrayList.add(new MailData(resultSet.getString(string2), resultSet.getString("title"), resultSet.getInt("post_date"), resultSet.getInt("message_id")));
            }
        }
        catch (Exception exception) {
            try {
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        return arrayList;
    }

    private static class MailData {
        public String author;
        public String title;
        public String postDate;
        public int messageId;

        public MailData(String string, String string2, int n, int n2) {
            this.author = string;
            this.title = string2;
            this.postDate = String.format(String.format("%1$te-%1$tm-%1$tY", new Date((long)n * 1000L)), new Object[0]);
            this.messageId = n2;
        }
    }
}
