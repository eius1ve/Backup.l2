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
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageFavorites
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger el = LoggerFactory.getLogger(ManageFavorites.class);

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            el.info("CommunityBoard: Manage Favorites service loaded.");
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
        return new String[]{"_bbsgetfav", "_bbsaddfav_List", "_bbsdelfav_"};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onBypassCommand(Player player, String string) {
        block17: {
            String string2;
            StringTokenizer stringTokenizer;
            block18: {
                String[] stringArray;
                block15: {
                    String string3;
                    stringTokenizer = new StringTokenizer(string, "_");
                    string2 = stringTokenizer.nextToken();
                    if (!"bbsgetfav".equals(string2)) break block15;
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    StringBuilder stringBuilder = new StringBuilder("");
                    try {
                        connection = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = connection.prepareStatement("SELECT * FROM `bbs_favorites` WHERE `object_id` = ? ORDER BY `add_date` DESC");
                        preparedStatement.setInt(1, player.getObjectId());
                        resultSet = preparedStatement.executeQuery();
                        string3 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_favoritetpl.htm", player);
                        while (resultSet.next()) {
                            String string4 = string3.replace("%fav_title%", resultSet.getString("fav_title"));
                            string4 = string4.replace("%fav_bypass%", resultSet.getString("fav_bypass"));
                            string4 = string4.replace("%add_date%", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date((long)resultSet.getInt("add_date") * 1000L)));
                            string4 = string4.replace("%fav_id%", String.valueOf(resultSet.getInt("fav_id")));
                            stringBuilder.append(string4);
                        }
                    }
                    catch (Exception exception) {
                        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                            throw throwable;
                        }
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
                    string3 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_getfavorite.htm", player);
                    string3 = string3.replace("%FAV_LIST%", stringBuilder.toString());
                    ShowBoard.separateAndSend((String)string3, (Player)player);
                    break block17;
                }
                if (!"bbsaddfav".equals(string2)) break block18;
                String string5 = player.getSessionVar("add_fav");
                player.setSessionVar("add_fav", null);
                if (string5 != null && (stringArray = string5.split("&")).length > 1) {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    try {
                        connection = DatabaseFactory.getInstance().getConnection();
                        preparedStatement = connection.prepareStatement("REPLACE INTO `bbs_favorites`(`object_id`, `fav_bypass`, `fav_title`, `add_date`) VALUES(?, ?, ?, ?)");
                        preparedStatement.setInt(1, player.getObjectId());
                        preparedStatement.setString(2, stringArray[0]);
                        preparedStatement.setString(3, stringArray[1]);
                        preparedStatement.setInt(4, (int)(System.currentTimeMillis() / 1000L));
                        preparedStatement.execute();
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
                    }
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                }
                this.onBypassCommand(player, "_bbsgetfav");
                break block17;
            }
            if (!"bbsdelfav".equals(string2)) break block17;
            int n = Integer.parseInt(stringTokenizer.nextToken());
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("DELETE FROM `bbs_favorites` WHERE `fav_id` = ? and `object_id` = ?");
                preparedStatement.setInt(1, n);
                preparedStatement.setInt(2, player.getObjectId());
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
            this.onBypassCommand(player, "_bbsgetfav");
        }
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
    }
}
