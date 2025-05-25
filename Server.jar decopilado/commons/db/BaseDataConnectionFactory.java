/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mysql.cj.jdbc.MysqlConnectionPoolDataSource
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class BaseDataConnectionFactory {
    private static final Logger ad = LoggerFactory.getLogger(BaseDataConnectionFactory.class);
    private final ConnectionPoolDataSource a;
    private final Semaphore a;
    private final ArrayDeque<PooledConnection> a;
    private final int en;
    private final long ap;
    private boolean av;
    private int eo;
    private PooledConnection a;
    private final PoolConnectionEventListener a;

    protected BaseDataConnectionFactory(ConnectionPoolDataSource connectionPoolDataSource, int n, int n2) {
        this.a = connectionPoolDataSource;
        this.en = n;
        this.ap = n2;
        if (n < 1) {
            throw new IllegalArgumentException("Invalid maxConnections value.");
        }
        this.a = new Semaphore(n, true);
        this.a = new ArrayDeque(this.en);
        this.a = new PoolConnectionEventListener();
        this.av = false;
        this.eo = 0;
        try {
            this.testDB();
            ad.info("DatabaseFactory: Database connection tested and working.");
        }
        catch (SQLException sQLException) {
            throw new RuntimeException("Can't init database connections pool", sQLException);
        }
    }

    public Connection getConnection() throws SQLException {
        return this.getConnectionImpl();
    }

    protected abstract void testDB() throws SQLException;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Connection getConnectionImpl() throws SQLException {
        BaseDataConnectionFactory baseDataConnectionFactory = this;
        synchronized (baseDataConnectionFactory) {
            if (this.av) {
                throw new IllegalStateException("Connection pool has been disposed.");
            }
        }
        Thread.currentThread();
        boolean bl = Thread.interrupted();
        try {
            try {
                if (!this.a.tryAcquire(this.ap, TimeUnit.MILLISECONDS)) {
                    throw new TimeoutException();
                }
            }
            catch (InterruptedException interruptedException) {
                throw new RuntimeException("Interrupted while waiting for a database connection.", interruptedException);
            }
            boolean bl2 = false;
            try {
                Connection connection = this.a();
                bl2 = true;
                Connection connection2 = connection;
                if (!bl2) {
                    this.a.release();
                }
                return connection2;
            }
            catch (Throwable throwable) {
                if (!bl2) {
                    this.a.release();
                }
                throw throwable;
            }
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized Connection a() throws SQLException {
        Connection connection;
        PooledConnection pooledConnection;
        if (this.av) {
            throw new IllegalStateException("Connection pool has been disposed.");
        }
        if (!((ArrayDeque)((Object)this.a)).isEmpty()) {
            pooledConnection = (PooledConnection)((ArrayDeque)((Object)this.a)).remove();
        } else {
            pooledConnection = this.a.getPooledConnection();
            pooledConnection.addConnectionEventListener(this.a);
        }
        try {
            this.a = pooledConnection;
            connection = pooledConnection.getConnection();
        }
        finally {
            this.a = null;
        }
        ++this.eo;
        this.ab();
        return connection;
    }

    private void ab() {
        if (this.eo < 0) {
            throw new RuntimeException();
        }
        if (this.eo + ((ArrayDeque)((Object)this.a)).size() > this.en) {
            throw new RuntimeException();
        }
        if (this.eo + this.a.availablePermits() > this.en) {
            throw new RuntimeException();
        }
    }

    private synchronized void a(PooledConnection pooledConnection) {
        if (this.av) {
            this.b(pooledConnection);
            return;
        }
        if (this.eo <= 0) {
            throw new AssertionError();
        }
        --this.eo;
        this.a.release();
        ((ArrayDeque)((Object)this.a)).add(pooledConnection);
        this.ab();
    }

    private synchronized void b(PooledConnection pooledConnection) {
        pooledConnection.removeConnectionEventListener(this.a);
        if (!((ArrayDeque)((Object)this.a)).remove(pooledConnection) && pooledConnection != this.a) {
            if (this.eo <= 0) {
                throw new AssertionError();
            }
            --this.eo;
            this.a.release();
        }
        this.c(pooledConnection);
        this.ab();
    }

    private void c(PooledConnection pooledConnection) {
        try {
            pooledConnection.close();
        }
        catch (SQLException sQLException) {
            ad.error("Error while closing database connection", (Throwable)sQLException);
        }
    }

    protected static ConnectionPoolDataSource applyExProperties(ConnectionPoolDataSource connectionPoolDataSource, Properties properties) {
        if (properties == null || properties.isEmpty()) {
            return connectionPoolDataSource;
        }
        MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = (MysqlConnectionPoolDataSource)connectionPoolDataSource;
        try {
            Method method = mysqlConnectionPoolDataSource.getClass().getSuperclass().getDeclaredMethod("setStringRuntimeProperty", String.class, String.class);
            if (method != null) {
                boolean bl = method.isAccessible();
                method.setAccessible(true);
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    String string = entry.getKey() != null ? entry.getKey().toString() : "";
                    String string2 = entry.getValue() != null ? entry.getValue().toString() : "";
                    method.invoke(mysqlConnectionPoolDataSource, string, string2);
                }
                method.setAccessible(bl);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return mysqlConnectionPoolDataSource;
    }

    protected static ConnectionPoolDataSource applyExProperties(ConnectionPoolDataSource connectionPoolDataSource, String string) {
        try {
            Properties properties = new Properties();
            for (String string2 : StringUtils.split((String)string, (char)'&')) {
                int n = string2.indexOf(61);
                if (n < 0) continue;
                properties.setProperty(URLDecoder.decode(string2.substring(0, n), "UTF-8"), URLDecoder.decode(string2.substring(n + 1), "UTF-8"));
            }
            return BaseDataConnectionFactory.applyExProperties(connectionPoolDataSource, properties);
        }
        catch (Exception exception) {
            return connectionPoolDataSource;
        }
    }

    public synchronized void shutdown() throws SQLException {
        if (this.av) {
            return;
        }
        this.av = true;
        SQLException sQLException = null;
        while (!((ArrayDeque)((Object)this.a)).isEmpty()) {
            PooledConnection pooledConnection = (PooledConnection)((ArrayDeque)((Object)this.a)).remove();
            try {
                pooledConnection.close();
            }
            catch (SQLException sQLException2) {
                if (sQLException != null) continue;
                sQLException = sQLException2;
            }
        }
        if (sQLException != null) {
            throw sQLException;
        }
    }

    private class PoolConnectionEventListener
    implements ConnectionEventListener {
        private PoolConnectionEventListener() {
        }

        @Override
        public void connectionClosed(ConnectionEvent connectionEvent) {
            PooledConnection pooledConnection = (PooledConnection)connectionEvent.getSource();
            BaseDataConnectionFactory.this.a(pooledConnection);
        }

        @Override
        public void connectionErrorOccurred(ConnectionEvent connectionEvent) {
            PooledConnection pooledConnection = (PooledConnection)connectionEvent.getSource();
            BaseDataConnectionFactory.this.b(pooledConnection);
        }
    }

    public static class TimeoutException
    extends RuntimeException {
        private static final long aq = 1L;

        public TimeoutException() {
            super("Timeout while waiting for a free database connection.");
        }

        public TimeoutException(String string) {
            super(string);
        }
    }
}
