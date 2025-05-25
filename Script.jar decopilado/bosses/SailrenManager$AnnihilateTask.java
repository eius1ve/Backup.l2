/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package bosses;

import bosses.EpicBossState;
import bosses.SailrenManager;
import l2.commons.threading.RunnableImpl;

private static class SailrenManager.AnnihilateTask
extends RunnableImpl {
    private SailrenManager.AnnihilateTask() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() {
        Class<SailrenManager> clazz = SailrenManager.class;
        synchronized (SailrenManager.class) {
            if (!SailrenManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                j = null;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            SailrenManager.n();
            return;
        }
    }
}
