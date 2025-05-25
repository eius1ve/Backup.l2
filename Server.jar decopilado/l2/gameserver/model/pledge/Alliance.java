/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.pledge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.cache.CrestCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.ClanTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alliance {
    private static final Logger cq = LoggerFactory.getLogger(Alliance.class);
    private String dJ;
    private int oP;
    private Clan b = null;
    private Map<Integer, Clan> bk = new ConcurrentHashMap<Integer, Clan>();
    private int oQ;
    private long cF;

    public Alliance(int n) {
        this.oP = n;
        this.restore();
    }

    public Alliance(int n, String string, Clan clan) {
        this.oP = n;
        this.dJ = string;
        this.setLeader(clan);
    }

    public int getLeaderId() {
        return this.b != null ? this.b.getClanId() : 0;
    }

    public Clan getLeader() {
        return this.b;
    }

    public void setLeader(Clan clan) {
        this.b = clan;
        this.bk.put(clan.getClanId(), clan);
    }

    public String getAllyLeaderName() {
        return this.b != null ? this.b.getLeaderName() : "";
    }

    public void addAllyMember(Clan clan, boolean bl) {
        this.bk.put(clan.getClanId(), clan);
        if (bl) {
            this.b(clan);
        }
    }

    public Clan getAllyMember(int n) {
        return this.bk.get(n);
    }

    public void removeAllyMember(int n) {
        if (this.b != null && this.b.getClanId() == n) {
            return;
        }
        Clan clan = this.bk.remove(n);
        if (clan == null) {
            cq.warn("Clan " + n + " not found in alliance while trying to remove");
            return;
        }
        this.c(clan);
    }

    public Clan[] getMembers() {
        return this.bk.values().toArray(new Clan[this.bk.size()]);
    }

    public int getMembersCount() {
        return this.bk.size();
    }

    public int getAllyId() {
        return this.oP;
    }

    public String getAllyName() {
        return this.dJ;
    }

    public void setAllyCrestId(int n) {
        this.oQ = n;
    }

    public int getAllyCrestId() {
        return this.oQ;
    }

    public void setAllyId(int n) {
        this.oP = n;
    }

    public void setAllyName(String string) {
        this.dJ = string;
    }

    public boolean isMember(int n) {
        return this.bk.containsKey(n);
    }

    public void setExpelledMemberTime(long l) {
        this.cF = l;
    }

    public long getExpelledMemberTime() {
        return this.cF;
    }

    public void setExpelledMember() {
        this.cF = System.currentTimeMillis();
        this.updateAllyInDB();
    }

    public boolean canInvite() {
        return System.currentTimeMillis() - this.cF >= Config.ALLY_LEAVE_TIME_PENALTY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateAllyInDB() {
        if (this.getLeaderId() == 0) {
            cq.warn("updateAllyInDB with empty LeaderId");
            Thread.dumpStack();
            return;
        }
        if (this.getAllyId() == 0) {
            cq.warn("updateAllyInDB with empty AllyId");
            Thread.dumpStack();
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `ally_data` SET `leader_id`=?,`expelled_member`=? WHERE `ally_id`=?");
            preparedStatement.setInt(1, this.getLeaderId());
            preparedStatement.setLong(2, this.getExpelledMemberTime() / 1000L);
            preparedStatement.setInt(3, this.getAllyId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cq.warn("error while updating ally '" + this.getAllyId() + "' data in db: " + exception);
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
     */
    public void store() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `ally_data` (`ally_id`,`ally_name`,`leader_id`) values (?,?,?)");
            preparedStatement.setInt(1, this.getAllyId());
            preparedStatement.setString(2, this.getAllyName());
            preparedStatement.setInt(3, this.getLeaderId());
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `ally_id`=? WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getAllyId());
            preparedStatement.setInt(2, this.getLeaderId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cq.warn("error while saving new ally to db " + exception);
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
     */
    private void b(Clan clan) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `ally_id`=? WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getAllyId());
            preparedStatement.setInt(2, clan.getClanId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cq.warn("error while saving new alliance member to db " + exception);
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
     */
    private void c(Clan clan) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `ally_id`=0 WHERE `clan_id`=?");
            preparedStatement.setInt(1, clan.getClanId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cq.warn("error while removing ally member in db " + exception);
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
     */
    private void restore() {
        if (this.getAllyId() == 0) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `ally_name`,`leader_id` FROM `ally_data` where `ally_id`=?");
            preparedStatement.setInt(1, this.getAllyId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                this.setAllyName(resultSet.getString("ally_name"));
                int n = resultSet.getInt("leader_id");
                DbUtils.close(preparedStatement, resultSet);
                preparedStatement = connection.prepareStatement("SELECT `clan_id` FROM `clan_data` WHERE `ally_id`=?");
                preparedStatement.setInt(1, this.getAllyId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Clan clan = ClanTable.getInstance().getClan(resultSet.getInt("clan_id"));
                    if (clan == null) continue;
                    if (clan.getClanId() == n) {
                        this.setLeader(clan);
                        continue;
                    }
                    this.addAllyMember(clan, false);
                }
            }
            this.setAllyCrestId(CrestCache.getInstance().getAllyCrestId(this.getAllyId()));
        }
        catch (Exception exception) {
            try {
                cq.warn("error while restoring ally");
                cq.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public void broadcastToOnlineMembers(L2GameServerPacket l2GameServerPacket) {
        for (Clan clan : this.bk.values()) {
            if (clan == null) continue;
            clan.broadcastToOnlineMembers(l2GameServerPacket);
        }
    }

    public void broadcastToOtherOnlineMembers(L2GameServerPacket l2GameServerPacket, Player player) {
        for (Clan clan : this.bk.values()) {
            if (clan == null) continue;
            clan.broadcastToOtherOnlineMembers(l2GameServerPacket, player);
        }
    }

    public String toString() {
        return this.getAllyName();
    }

    public boolean hasAllyCrest() {
        return this.oQ > 0;
    }

    public void broadcastAllyStatus() {
        for (Clan clan : this.getMembers()) {
            clan.broadcastClanStatus(false, true, false);
        }
    }
}
