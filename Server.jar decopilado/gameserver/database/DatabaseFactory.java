/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mysql.cj.jdbc.MysqlConnectionPoolDataSource
 */
package l2.gameserver.database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.ConnectionPoolDataSource;
import l2.commons.db.BaseDataConnectionFactory;
import l2.gameserver.Config;

public class DatabaseFactory
extends BaseDataConnectionFactory {
    private static DatabaseFactory a;
    private static final String bM = "UTF-8";

    public static final DatabaseFactory getInstance() {
        if (a == null) {
            a = new DatabaseFactory(DatabaseFactory.a(), Config.DATABASE_MAX_CONN, Config.DATABASE_TIMEOUT);
        }
        return a;
    }

    private DatabaseFactory(ConnectionPoolDataSource connectionPoolDataSource, int n, int n2) {
        super(connectionPoolDataSource, n, n2);
    }

    private static ConnectionPoolDataSource a() {
        MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = new MysqlConnectionPoolDataSource();
        try {
            mysqlConnectionPoolDataSource.setServerName(Config.DATABASE_HOST);
            mysqlConnectionPoolDataSource.setPort(Config.DATABASE_PORT);
            mysqlConnectionPoolDataSource.setDatabaseName(Config.DATABASE_NAME);
            mysqlConnectionPoolDataSource.setUser(Config.DATABASE_USER);
            mysqlConnectionPoolDataSource.setPasswordCharacterEncoding(bM);
            mysqlConnectionPoolDataSource.setPassword(Config.DATABASE_PASS);
            mysqlConnectionPoolDataSource.setAutoReconnect(true);
            mysqlConnectionPoolDataSource.setAutoReconnectForPools(true);
            mysqlConnectionPoolDataSource.setClobCharacterEncoding(bM);
            mysqlConnectionPoolDataSource.setCharacterEncoding(bM);
            mysqlConnectionPoolDataSource.setUseAffectedRows(true);
            mysqlConnectionPoolDataSource.setUseSSL(false);
            mysqlConnectionPoolDataSource.setAllowPublicKeyRetrieval(true);
            mysqlConnectionPoolDataSource.setVerifyServerCertificate(false);
            return DatabaseFactory.applyExProperties((ConnectionPoolDataSource)mysqlConnectionPoolDataSource, Config.DATABASE_EX_PROPERTIES);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void testDB() throws SQLException {
        Connection connection = this.getConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT VERSION()");
        statement.clearBatch();
        statement.close();
        connection.close();
    }
}
