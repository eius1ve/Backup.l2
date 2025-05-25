/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.database;

import java.sql.SQLException;

class L2DatabaseFactory.1
implements Runnable {
    L2DatabaseFactory.1() {
    }

    @Override
    public void run() {
        try {
            L2DatabaseFactory.this.testDB();
        }
        catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
    }
}
