/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mysql.cj.jdbc.MysqlConnectionPoolDataSource
 */
package l2.authserver.database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.ConnectionPoolDataSource;
import l2.authserver.Config;
import l2.authserver.ThreadPoolManager;
import l2.commons.db.BaseDataConnectionFactory;

public class L2DatabaseFactory
extends BaseDataConnectionFactory {
    private static L2DatabaseFactory a;
    private static final String aA = "UTF-8";

    public static final L2DatabaseFactory getInstance() {
        if (a == null) {
            a = new L2DatabaseFactory(L2DatabaseFactory.a(), Config.DATABASE_MAX_CONN, Config.DATABASE_TIMEOUT);
        }
        return a;
    }

    private L2DatabaseFactory(ConnectionPoolDataSource connectionPoolDataSource, int n, int n2) {
        super(connectionPoolDataSource, n, n2);
        this.aa();
    }

    private static ConnectionPoolDataSource a() {
        MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = new MysqlConnectionPoolDataSource();
        try {
            mysqlConnectionPoolDataSource.setServerName(Config.DATABASE_HOST);
            mysqlConnectionPoolDataSource.setPort(Config.DATABASE_PORT);
            mysqlConnectionPoolDataSource.setDatabaseName(Config.DATABASE_NAME);
            mysqlConnectionPoolDataSource.setUser(Config.DATABASE_USER);
            mysqlConnectionPoolDataSource.setPasswordCharacterEncoding(aA);
            mysqlConnectionPoolDataSource.setPassword(Config.DATABASE_PASS);
            mysqlConnectionPoolDataSource.setAutoReconnect(true);
            mysqlConnectionPoolDataSource.setAutoReconnectForPools(true);
            mysqlConnectionPoolDataSource.setCharacterEncoding(aA);
            mysqlConnectionPoolDataSource.setClobCharacterEncoding(aA);
            mysqlConnectionPoolDataSource.setAllowPublicKeyRetrieval(true);
            mysqlConnectionPoolDataSource.setUseAffectedRows(true);
            mysqlConnectionPoolDataSource.setUseSSL(false);
            mysqlConnectionPoolDataSource.setVerifyServerCertificate(false);
            return L2DatabaseFactory.applyExProperties((ConnectionPoolDataSource)mysqlConnectionPoolDataSource, Config.DATABASE_EX_PROPERTIES);
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

    private void aa() {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable(){

            @Override
            public void run() {
                try {
                    L2DatabaseFactory.this.testDB();
                }
                catch (SQLException sQLException) {
                    sQLException.printStackTrace();
                }
            }
        }, 240000L, 240000L);
    }
}
