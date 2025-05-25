/*
 * Decompiled with CFR 0.152.
 */
package bosses;

import bosses.EpicBossState;

private class FrintezzaManager.IntervalEnd
implements Runnable {
    private FrintezzaManager.IntervalEnd() {
    }

    @Override
    public void run() {
        FrintezzaManager.this._state.setState(EpicBossState.State.NOTSPAWN);
        FrintezzaManager.this._state.update();
    }
}
