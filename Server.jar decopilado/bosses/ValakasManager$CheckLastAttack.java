/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 */
package bosses;

import bosses.EpicBossState;
import bosses.ValakasManager;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;

private static class ValakasManager.CheckLastAttack
extends RunnableImpl {
    private ValakasManager.CheckLastAttack() {
    }

    public void runImpl() throws Exception {
        if (_state.getState() == EpicBossState.State.ALIVE) {
            if (S + Config.FWV_LIMITUNTILSLEEPVALAKAS < System.currentTimeMillis()) {
                ValakasManager.n();
            } else {
                h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.CheckLastAttack()), 60000L);
            }
        }
    }
}
