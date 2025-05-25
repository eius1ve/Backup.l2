/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.tables.ClanTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClanDataDAO {
    private static final Logger aF = LoggerFactory.getLogger(ClanDataDAO.class);
    private static final ClanDataDAO a = new ClanDataDAO();
    public static final String SELECT_CASTLE_OWNER = "SELECT `clan_id` FROM `clan_data` WHERE `hasCastle`=? LIMIT 1";
    public static final String SELECT_CLANHALL_OWNER = "SELECT `clan_id` FROM `clan_data` WHERE `hasHideout`=? LIMIT 1";

    public static ClanDataDAO getInstance() {
        return a;
    }

    public Clan getOwner(Castle castle) {
        return this.a(castle, SELECT_CASTLE_OWNER);
    }

    public Clan getOwner(ClanHall clanHall) {
        return this.a(clanHall, SELECT_CLANHALL_OWNER);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Clan a(Residence residence, String string) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            Clan clan;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(string);
                preparedStatement.setInt(1, residence.getId());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                clan = ClanTable.getInstance().getClan(resultSet.getInt("clan_id"));
            }
            catch (Exception exception) {
                try {
                    aF.error("ClanDataDAO.getOwner(Residence, String)", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return clan;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return null;
    }
}
