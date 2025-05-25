/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 */
package bosses;

import bosses.AntharasManager;
import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;

private static class AntharasManager.MaxTimeCheck
extends RunnableImpl {
    private AntharasManager.MaxTimeCheck() {
    }

    public void runImpl() throws Exception {
        if (_state.getState() != EpicBossState.State.ALIVE) {
            return;
        }
        if (T + Config.FWA_LIMITMAXUNTILSLEEPANTHARAS <= System.currentTimeMillis()) {
            AntharasManager.n();
        }
    }
}
