/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package bosses;

import bosses.BaiumManager;
import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;

public static class BaiumManager.onAnnihilated
extends RunnableImpl {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() {
        Class<BaiumManager> clazz = BaiumManager.class;
        synchronized (BaiumManager.class) {
            if (!BaiumManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                j = null;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            BaiumManager.p();
            return;
        }
    }
}
