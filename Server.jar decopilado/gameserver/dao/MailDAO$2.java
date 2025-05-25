/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.dao;

import l2.commons.dao.JdbcEntityStats;

class MailDAO.2
implements JdbcEntityStats {
    MailDAO.2() {
    }

    @Override
    public long getLoadCount() {
        return MailDAO.this.m.get();
    }

    @Override
    public long getInsertCount() {
        return MailDAO.this.n.get();
    }

    @Override
    public long getUpdateCount() {
        return MailDAO.this.o.get();
    }

    @Override
    public long getDeleteCount() {
        return MailDAO.this.p.get();
    }
}
