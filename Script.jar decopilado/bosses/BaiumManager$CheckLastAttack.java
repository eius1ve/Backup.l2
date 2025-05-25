/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 */
package bosses;

import bosses.BaiumManager;
import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;

public static class BaiumManager.CheckLastAttack
extends RunnableImpl {
    public void runImpl() throws Exception {
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            if (S + Config.FWB_LIMITUNTILSLEEPBAIUM < System.currentTimeMillis()) {
                BaiumManager.p();
            } else {
                h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new BaiumManager.CheckLastAttack()), 60000L);
            }
        }
    }
}
