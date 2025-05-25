/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package ai;

import l2.commons.threading.RunnableImpl;

private class Tears.SpawnMobsTask
extends RunnableImpl {
    private Tears.SpawnMobsTask() {
    }

    public void runImpl() {
        Tears.this.d();
        Tears.this.spawnTask = null;
    }
}
