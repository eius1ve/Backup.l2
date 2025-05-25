/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.database.DatabaseFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package bosses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpicBossState {
    private static final Logger d = LoggerFactory.getLogger(EpicBossState.class);
    private int _bossId;
    private long U;
    private State a;

    public int getBossId() {
        return this._bossId;
    }

    public void setBossId(int n) {
        this._bossId = n;
    }

    public State getState() {
        return this.a;
    }

    public void setState(State state) {
        this.a = state;
    }

    public long getRespawnDate() {
        return this.U;
    }

    public void setRespawnDate(long l) {
        this.U = l + System.currentTimeMillis();
    }

    public EpicBossState(int n) {
        this(n, true);
    }

    public EpicBossState(int n, boolean bl) {
        this._bossId = n;
        if (bl) {
            this.load();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                int n;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM epic_boss_spawn WHERE bossId = ? LIMIT 1");
                preparedStatement.setInt(1, this._bossId);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                this.U = resultSet.getLong("respawnDate") * 1000L;
                this.a = this.U - System.currentTimeMillis() <= 0L ? State.NOTSPAWN : ((n = resultSet.getInt("state")) == State.NOTSPAWN.ordinal() ? State.NOTSPAWN : (n == State.INTERVAL.ordinal() ? State.INTERVAL : (n == State.ALIVE.ordinal() ? State.ALIVE : (n == State.DEAD.ordinal() ? State.DEAD : State.NOTSPAWN))));
            }
            catch (Exception exception) {
                try {
                    d.error("", (Throwable)exception);
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
    public void save() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO epic_boss_spawn (bossId,respawnDate,state) VALUES(?,?,?)");
            preparedStatement.setInt(1, this._bossId);
            preparedStatement.setInt(2, (int)(this.U / 1000L));
            preparedStatement.setInt(3, this.a.ordinal());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                d.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE epic_boss_spawn SET respawnDate=" + this.U / 1000L + ", state=" + this.a.ordinal() + " WHERE bossId=" + this._bossId);
            Date date = new Date(this.U);
            d.info("update EpicBossState: ID:" + this._bossId + ", RespawnDate:" + date + ", State:" + this.a.toString());
        }
        catch (Exception exception) {
            try {
                d.error("Exception on update EpicBossState: ID " + this._bossId + ", RespawnDate:" + this.U / 1000L + ", State:" + this.a.toString(), (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, statement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)statement);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)statement);
    }

    public void setNextRespawnDate(long l) {
        this.U = l;
    }

    public long getInterval() {
        long l = this.U - System.currentTimeMillis();
        return l > 0L ? l : 0L;
    }

    public static final class State
    extends Enum<State> {
        public static final /* enum */ State NOTSPAWN = new State();
        public static final /* enum */ State ALIVE = new State();
        public static final /* enum */ State DEAD = new State();
        public static final /* enum */ State INTERVAL = new State();
        private static final /* synthetic */ State[] a;

        public static State[] values() {
            return (State[])a.clone();
        }

        public static State valueOf(String string) {
            return Enum.valueOf(State.class, string);
        }

        private static /* synthetic */ State[] a() {
            return new State[]{NOTSPAWN, ALIVE, DEAD, INTERVAL};
        }

        static {
            a = State.a();
        }
    }
}
