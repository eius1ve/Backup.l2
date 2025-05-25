/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer
 */
package l2.gameserver;

import com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import l2.gameserver.Config;

class GameServer.1
extends DBMSStructureSynchronizer {
    GameServer.1(Connection connection, String string, InputStream inputStream) {
        super(connection, string, inputStream);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected String tryGetDBVersion(List<String> list) throws Exception {
        try (Statement statement = this.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT `value` AS `dbver` FROM `server_variables` WHERE `name` = '" + list.stream().collect(Collectors.joining()) + "_dbmsstruct_dbver'");){
            if (!resultSet.next()) return super.tryGetDBVersion(list);
            String string = resultSet.getString("dbver");
            return string;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return super.tryGetDBVersion(list);
    }

    protected void trySetDBVersion(List<String> list, String string) throws Exception {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement("REPLACE INTO `server_variables` (`name`, `value`) VALUES ('" + list.stream().collect(Collectors.joining()) + "_dbmsstruct_dbver',?)");){
            preparedStatement.setString(1, string);
            preparedStatement.executeUpdate();
            return;
        }
        catch (Exception exception) {
            super.trySetDBVersion(list, string);
            return;
        }
    }

    public void synchronize(List<String> list) {
        if (!Config.DATABASE_EX_STRUCTURE_MANAGER) {
            return;
        }
        super.synchronize(list);
    }
}
