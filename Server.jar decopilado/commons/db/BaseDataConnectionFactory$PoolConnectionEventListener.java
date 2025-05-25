/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.db;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;

private class BaseDataConnectionFactory.PoolConnectionEventListener
implements ConnectionEventListener {
    private BaseDataConnectionFactory.PoolConnectionEventListener() {
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
