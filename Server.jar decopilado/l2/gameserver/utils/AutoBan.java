/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AutoBan {
    private static final Logger dE = LoggerFactory.getLogger(AutoBan.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isBanned(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        boolean bl;
        block4: {
            bl = false;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT MAX(`endban`) AS `endban` FROM `bans` WHERE `obj_Id`=? AND `endban` IS NOT NULL");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                Long l = resultSet.getLong("endban") * 1000L;
                bl = l > System.currentTimeMillis();
            }
            catch (Exception exception) {
                try {
                    dE.warn("Could not restore ban data: " + exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void Banned(Player player, int n, String string, String string2) {
        Object object;
        int n2 = 0;
        if (n == -1) {
            n2 = Integer.MAX_VALUE;
        } else if (n > 0) {
            object = Calendar.getInstance();
            ((Calendar)object).add(5, n);
            n2 = (int)(((Calendar)object).getTimeInMillis() / 1000L);
        } else {
            dE.warn("Negative ban period: " + n);
            return;
        }
        object = new SimpleDateFormat("yy.MM.dd H:mm:ss").format(new Date());
        String string3 = new SimpleDateFormat("yy.MM.dd H:mm:ss").format(new Date((long)n2 * 1000L));
        if ((long)n2 * 1000L <= Calendar.getInstance().getTimeInMillis()) {
            dE.warn("Negative ban period | From " + (String)object + " to " + string3);
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `bans` (`account_name`, `obj_id`, `baned`, `unban`, `reason`, `GM`, `endban`) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1, player.getAccountName());
            preparedStatement.setInt(2, player.getObjectId());
            preparedStatement.setString(3, (String)object);
            preparedStatement.setString(4, string3);
            preparedStatement.setString(5, string);
            preparedStatement.setString(6, string2);
            preparedStatement.setLong(7, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                dE.warn("could not store bans data:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public static boolean Banned(String var0, int var1_1, int var2_2, String var3_3, String var4_4) {
        block11: {
            block10: {
                block9: {
                    var6_5 = CharacterDAO.getInstance().getObjectIdByName(var0);
                    v0 = var5_6 = var6_5 > 0;
                    if (!var5_6) {
                        return false;
                    }
                    var7_7 = null;
                    var8_8 = null;
                    var7_7 = DatabaseFactory.getInstance().getConnection();
                    var8_8 = var7_7.prepareStatement("UPDATE `characters` SET `accesslevel`=? WHERE `obj_Id`=?");
                    var8_8.setInt(1, var1_1);
                    var8_8.setInt(2, var6_5);
                    var8_8.executeUpdate();
                    DbUtils.close(var8_8);
                    if (var1_1 >= 0) ** GOTO lbl50
                    var9_9 = 0;
                    if (var2_2 == -1) {
                        var9_9 = 0x7FFFFFFF;
                        break block9;
                    }
                    if (var2_2 > 0) {
                        var10_11 = Calendar.getInstance();
                        var10_11.add(5, var2_2);
                        var9_9 = (int)(var10_11.getTimeInMillis() / 1000L);
                        break block9;
                    }
                    AutoBan.dE.warn("Negative ban period: " + var2_2);
                    var10_12 = false;
                    DbUtils.closeQuietly(var7_7, var8_8);
                    return var10_12;
                }
                var10_11 = new SimpleDateFormat("yy.MM.dd H:mm:ss").format(new Date());
                var11_13 = new SimpleDateFormat("yy.MM.dd H:mm:ss").format(new Date((long)var9_9 * 1000L));
                if ((long)var9_9 * 1000L > Calendar.getInstance().getTimeInMillis()) break block10;
                AutoBan.dE.warn("Negative ban period | From " + (String)var10_11 + " to " + var11_13);
                var12_14 = false;
                DbUtils.closeQuietly(var7_7, var8_8);
                return var12_14;
            }
            try {
                var8_8 = var7_7.prepareStatement("INSERT INTO `bans` (`obj_id`, `baned`, `unban`, `reason`, `GM`, `endban`) VALUES(?,?,?,?,?,?)");
                var8_8.setInt(1, var6_5);
                var8_8.setString(2, (String)var10_11);
                var8_8.setString(3, var11_13);
                var8_8.setString(4, var3_3);
                var8_8.setString(5, var4_4);
                var8_8.setLong(6, var9_9);
                var8_8.execute();
                break block11;
lbl50:
                // 1 sources

                var8_8 = var7_7.prepareStatement("DELETE FROM `bans` WHERE `obj_id`=?");
                var8_8.setInt(1, var6_5);
                var8_8.execute();
            }
            catch (Exception var9_10) {
                try {
                    var9_10.printStackTrace();
                    AutoBan.dE.warn("could not store bans data:" + var9_10);
                    var5_6 = false;
                }
                catch (Throwable var13_15) {
                    DbUtils.closeQuietly(var7_7, var8_8);
                    throw var13_15;
                }
                DbUtils.closeQuietly(var7_7, var8_8);
            }
        }
        DbUtils.closeQuietly(var7_7, var8_8);
        return var5_6;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void Karma(Player player, int n, String object, String string) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String string2 = new SimpleDateFormat("yy.MM.dd H:mm:ss").format(new Date());
            object = "Add karma(" + n + ") " + (String)object;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `bans` (`account_name`, `obj_id`, `baned`, `reason`, `GM`) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, player.getAccountName());
            preparedStatement.setInt(2, player.getObjectId());
            preparedStatement.setString(3, string2);
            preparedStatement.setString(4, (String)object);
            preparedStatement.setString(5, string);
            preparedStatement.execute();
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        catch (Exception exception) {
            dE.warn("could not store bans data:" + exception);
        }
        finally {
            DbUtils.closeQuietly(connection, preparedStatement);
        }
    }

    public static void Banned(Player player, int n, String string) {
        AutoBan.Banned(player, n, string, "AutoBan");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean ChatBan(String string, int n, String string2, String string3) {
        boolean bl = true;
        long l = n * 60000;
        int n2 = CharacterDAO.getInstance().getObjectIdByName(string);
        if (n2 == 0) {
            return false;
        }
        Player player = World.getPlayer(string);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (player != null) {
            player.sendMessage(new CustomMessage("l2p.Util.AutoBan.ChatBan", player, new Object[0]).addString(string3).addNumber(n));
            player.updateNoChannel(l);
            return bl;
        }
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `nochannel` = ? WHERE `obj_Id`=?");
            preparedStatement.setLong(1, l > 0L ? l / 1000L : l);
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bl = false;
                dE.warn("Could not activate nochannel:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean ChatUnBan(String string, String string2) {
        boolean bl = true;
        Player player = World.getPlayer(string);
        int n = CharacterDAO.getInstance().getObjectIdByName(string);
        if (n == 0) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (player != null) {
            player.sendMessage(new CustomMessage("l2p.Util.AutoBan.ChatUnBan", player, new Object[0]).addString(string2));
            player.updateNoChannel(0L);
            return bl;
        }
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `nochannel` = ? WHERE `obj_Id`=?");
            preparedStatement.setLong(1, 0L);
            preparedStatement.setInt(2, n);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bl = false;
                dE.warn("Could not activate nochannel:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean noSpam(String string, int n) {
        boolean bl = true;
        int n2 = CharacterDAO.getInstance().getObjectIdByName(string);
        if (n2 == 0) {
            return false;
        }
        Player player = World.getPlayer(string);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (player != null) {
            player.setAntiSpam(TimeUnit.MINUTES.toMillis(n), true);
            player.sendMessage("You have add no spam chat the player " + player + " for an " + n + " period.");
            return bl;
        }
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            String string2 = "REPLACE INTO `character_variables` VALUE (?, 'user-var', 'antispam', ?, -1);";
            preparedStatement = connection.prepareStatement(string2);
            preparedStatement.setInt(1, n2);
            preparedStatement.setLong(2, TimeUnit.MINUTES.toMillis(n));
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bl = false;
                dE.warn("Could not activate nospam:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return bl;
    }
}
