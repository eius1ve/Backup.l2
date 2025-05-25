/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;

private class DoorInstance.AutoOpenClose
extends RunnableImpl {
    private boolean db;

    public DoorInstance.AutoOpenClose(boolean bl) {
        this.db = bl;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.db) {
            DoorInstance.this.openMe(null, true);
        } else {
            DoorInstance.this.closeMe(null, true);
        }
    }
}
