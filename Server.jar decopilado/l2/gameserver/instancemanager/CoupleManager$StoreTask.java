/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import l2.commons.threading.RunnableImpl;

private class CoupleManager.StoreTask
extends RunnableImpl {
    private CoupleManager.StoreTask() {
    }

    @Override
    public void runImpl() throws Exception {
        CoupleManager.this.store();
    }
}
