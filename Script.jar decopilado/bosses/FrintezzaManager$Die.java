/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 */
package bosses;

import bosses.EpicBossState;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;

private class FrintezzaManager.Die
extends RunnableImpl {
    private int bp = 0;

    public FrintezzaManager.Die(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    FrintezzaManager.this.a(true);
                    int n = Math.abs((FrintezzaManager.this.j.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.j.getHeading() / 182.044444444));
                    FrintezzaManager.this.a(FrintezzaManager.this.j, 300, n - 180, 5, 0, 7000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.j, 200, n, 85, 4000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Die(2)), 7500L);
                    break;
                }
                case 2: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 120, 5, 0, 7000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 90, 5, 5000, 15000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Die(3)), 6000L);
                    break;
                }
                case 3: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 900, 90, 25, 7000, 10000, 0);
                    FrintezzaManager.this.h.doDie((Creature)FrintezzaManager.this.h);
                    FrintezzaManager.this.h = null;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Die(4)), 7000L);
                    break;
                }
                case 4: {
                    FrintezzaManager.this.v();
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.leaveMovieMode();
                    }
                    SpawnManager.getInstance().spawn("[frintezza_teleporter]");
                    FrintezzaManager.this._state.setState(EpicBossState.State.DEAD);
                    FrintezzaManager.this._state.update();
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
