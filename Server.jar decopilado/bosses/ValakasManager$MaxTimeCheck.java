/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 */
package bosses;

import bosses.EpicBossState;
import bosses.ValakasManager;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;

private static class ValakasManager.MaxTimeCheck
extends RunnableImpl {
    private ValakasManager.MaxTimeCheck() {
    }

    public void runImpl() throws Exception {
        if (_state.getState() != EpicBossState.State.ALIVE) {
            return;
        }
        if (T + Config.FWA_LIMITMAXUNTILSLEEPVALAKAS <= System.currentTimeMillis()) {
            ValakasManager.n();
        }
    }
}
