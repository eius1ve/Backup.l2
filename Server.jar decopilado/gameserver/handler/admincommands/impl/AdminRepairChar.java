/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;

public class AdminRepairChar
implements IAdminCommandHandler {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        block9: {
            int n;
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            Connection connection;
            block8: {
                Commands commands = (Commands)enum_;
                if (player.getPlayerAccess() == null || !player.getPlayerAccess().CanEditChar) {
                    return false;
                }
                if (stringArray.length != 2) {
                    return false;
                }
                connection = null;
                preparedStatement = null;
                resultSet = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("UPDATE `characters` SET `x`=-84318, `y`=244579, `z`=-3730 WHERE `char_name`=?");
                preparedStatement.setString(1, stringArray[1]);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                preparedStatement = connection.prepareStatement("SELECT `obj_id` FROM `characters` where `char_name`=?");
                preparedStatement.setString(1, stringArray[1]);
                resultSet = preparedStatement.executeQuery();
                n = 0;
                if (resultSet.next()) {
                    n = resultSet.getInt(1);
                }
                DbUtils.close(preparedStatement, resultSet);
                if (n != 0) break block8;
                boolean bl = false;
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                return bl;
            }
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM `character_shortcuts` WHERE `object_id`=?");
                preparedStatement.setInt(1, n);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                preparedStatement = connection.prepareStatement("DELETE FROM `character_variables` WHERE `obj_id`=? AND `type`='user-var' AND `name`='reflection' LIMIT 1");
                preparedStatement.setInt(1, n);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
            }
            catch (Exception exception) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                break block9;
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_restore = new Commands();
        public static final /* enum */ Commands admin_repair = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_restore, admin_repair};
        }

        static {
            a = Commands.a();
        }
    }
}
