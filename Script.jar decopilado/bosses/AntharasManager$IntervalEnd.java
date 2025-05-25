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

private static class AntharasManager.IntervalEnd
extends RunnableImpl {
    private AntharasManager.IntervalEnd() {
    }

    public void runImpl() {
        AntharasManager.setUnspawn();
        _state.setState(EpicBossState.State.NOTSPAWN);
        _state.update();
    }
}
