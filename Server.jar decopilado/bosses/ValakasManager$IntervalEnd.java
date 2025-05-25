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

private static class ValakasManager.IntervalEnd
extends RunnableImpl {
    private ValakasManager.IntervalEnd() {
    }

    public void runImpl() throws Exception {
        ValakasManager.setUnspawn();
        _state.setState(EpicBossState.State.NOTSPAWN);
        _state.update();
    }
}
