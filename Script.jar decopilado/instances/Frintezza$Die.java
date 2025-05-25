/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 */
package instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;

private class Frintezza.Die
extends RunnableImpl {
    private int bp = 0;

    public Frintezza.Die(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    Frintezza.this.a(true);
                    int n = Math.abs((Frintezza.this.j.getHeading() < 32768 ? 180 : 540) - (int)((double)Frintezza.this.j.getHeading() / 182.044444444));
                    Frintezza.this.a(Frintezza.this.j, 300, n - 180, 5, 0, 7000, 0);
                    Frintezza.this.a(Frintezza.this.j, 200, n, 85, 4000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Die(2)), 7500L);
                    break;
                }
                case 2: {
                    Frintezza.this.a(Frintezza.this.h, 100, 120, 5, 0, 7000, 0);
                    Frintezza.this.a(Frintezza.this.h, 100, 90, 5, 5000, 15000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Die(3)), 6000L);
                    break;
                }
                case 3: {
                    Frintezza.this.a(Frintezza.this.h, 900, 90, 25, 7000, 10000, 0);
                    Frintezza.this.h.doDie((Creature)Frintezza.this.h);
                    Frintezza.this.h = null;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Die(4)), 7000L);
                    break;
                }
                case 4: {
                    for (Player player : Frintezza.this.getPlayers()) {
                        player.leaveMovieMode();
                    }
                    Frintezza.this.cleanUp();
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
