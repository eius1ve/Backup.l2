/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

import l2.commons.threading.RunnableImpl;

private class LotteryManager.startLottery
extends RunnableImpl {
    protected LotteryManager.startLottery() {
    }

    @Override
    public void runImpl() throws Exception {
        if (LotteryManager.this.r()) {
            LotteryManager.this.aH();
            LotteryManager.this.aI();
            LotteryManager.this.aJ();
        }
    }
}
