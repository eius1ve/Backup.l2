/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 */
package bosses;

import bosses.AntharasManager;
import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;

private static class AntharasManager.CheckLastAttack
extends RunnableImpl {
    private AntharasManager.CheckLastAttack() {
    }

    public void runImpl() {
        if (_state.getState() == EpicBossState.State.ALIVE) {
            if (S + Config.FWA_LIMITUNTILSLEEPANTHARAS < System.currentTimeMillis()) {
                AntharasManager.n();
            } else {
                h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.CheckLastAttack()), 60000L);
            }
        }
    }
}
