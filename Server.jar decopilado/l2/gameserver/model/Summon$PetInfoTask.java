/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

private class Summon.PetInfoTask
extends RunnableImpl {
    private Summon.PetInfoTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Summon.this.bs();
        Summon.this.A = null;
    }
}
