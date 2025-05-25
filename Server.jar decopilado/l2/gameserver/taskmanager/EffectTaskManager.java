/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.commons.threading.SteppingRunnableQueueManager;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;

public class EffectTaskManager
extends SteppingRunnableQueueManager {
    private static final long dM = 250L;
    private static final EffectTaskManager[] a = new EffectTaskManager[Config.EFFECT_TASK_MANAGER_COUNT];
    private static int Eb;

    public static final EffectTaskManager getInstance() {
        return a[Eb++ & a.length - 1];
    }

    private EffectTaskManager() {
        super(250L);
        ThreadPoolManager.getInstance().scheduleAtFixedRate(this, Rnd.get(250L), 250L);
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                EffectTaskManager.this.purge();
            }
        }, 30000L, 30000L);
    }

    public CharSequence getStats(int n) {
        return a[n].getStats();
    }

    static {
        for (int i = 0; i < a.length; ++i) {
            EffectTaskManager.a[i] = new EffectTaskManager();
        }
        Eb = 0;
    }
}
