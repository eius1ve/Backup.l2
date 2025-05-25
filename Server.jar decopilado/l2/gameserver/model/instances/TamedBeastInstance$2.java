/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;

class TamedBeastInstance.2
extends RunnableImpl {
    TamedBeastInstance.2() {
    }

    @Override
    public void runImpl() throws Exception {
        TamedBeastInstance.this.doDespawn();
    }
}
