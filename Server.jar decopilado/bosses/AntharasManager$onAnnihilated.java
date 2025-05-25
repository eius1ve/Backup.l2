/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package bosses;

import bosses.AntharasManager;
import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;

private static class AntharasManager.onAnnihilated
extends RunnableImpl {
    private AntharasManager.onAnnihilated() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() {
        Class<AntharasManager> clazz = AntharasManager.class;
        synchronized (AntharasManager.class) {
            if (!AntharasManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                j = null;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            AntharasManager.n();
            return;
        }
    }
}
