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

private static class SailrenManager.IntervalEnd
extends RunnableImpl {
    private SailrenManager.IntervalEnd() {
    }

    public void runImpl() throws Exception {
        SailrenManager.setUnspawn();
        b.set(false);
        _state.setState(EpicBossState.State.NOTSPAWN);
        _state.update();
    }
}
