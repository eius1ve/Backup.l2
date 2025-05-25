/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package bosses;

import bosses.EpicBossState;
import bosses.ValakasManager;
import l2.commons.threading.RunnableImpl;

private static class ValakasManager.onAnnihilated
extends RunnableImpl {
    private ValakasManager.onAnnihilated() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() {
        Class<ValakasManager> clazz = ValakasManager.class;
        synchronized (ValakasManager.class) {
            if (!ValakasManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                j = null;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            ValakasManager.n();
            return;
        }
    }
}
