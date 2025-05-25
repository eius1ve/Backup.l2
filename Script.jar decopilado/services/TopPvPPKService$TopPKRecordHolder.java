/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import services.TopPvPPKService;

private static class TopPvPPKService.TopPKRecordHolder
extends TopPvPPKService.TopRecordHolder {
    private static final TopPvPPKService.TopPKRecordHolder a = new TopPvPPKService.TopPKRecordHolder();

    private TopPvPPKService.TopPKRecordHolder() {
        super(Config.PVP_PK_STAT_RECORD_LIMIT, Config.PVP_PK_STAT_CACHE_UPDATE_INTERVAL);
    }

    public static TopPvPPKService.TopPKRecordHolder getInstance() {
        return a;
    }

    @Override
    protected Collection<TopPvPPKService.TopRecord> fetchTopOnlineRecords() {
        LinkedList<TopPvPPKService.TopRecord> linkedList = new LinkedList<TopPvPPKService.TopRecord>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            linkedList.add(new TopPvPPKService.TopRecord(player.getObjectId(), player.getName(), player.getPkKills()));
        }
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Collection<TopPvPPKService.TopRecord> fetchTopDbRecords() {
        LinkedList<TopPvPPKService.TopRecord> linkedList = new LinkedList<TopPvPPKService.TopRecord>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT  `characters`.`obj_Id` AS `playerObjectId`,  `characters`.`char_name` AS `playerName`,  `characters`.`pkkills` AS `pkKills` FROM  `characters` ORDER BY  `characters`.`pkkills` DESC LIMIT ?");
            preparedStatement.setInt(1, this.getLimit());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                linkedList.add(new TopPvPPKService.TopRecord(resultSet.getInt("playerObjectId"), resultSet.getString("playerName"), resultSet.getInt("pkKills")));
            }
        }
        catch (SQLException sQLException) {
            try {
                eh.error("Can't fetch top PK records.", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        return linkedList;
    }
}
