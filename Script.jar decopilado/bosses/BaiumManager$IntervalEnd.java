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

public static class BaiumManager.IntervalEnd
extends RunnableImpl {
    public void runImpl() throws Exception {
        BaiumManager.setUnspawn();
        _state.setState(EpicBossState.State.NOTSPAWN);
        _state.update();
        a.doSpawn(true);
    }
}
