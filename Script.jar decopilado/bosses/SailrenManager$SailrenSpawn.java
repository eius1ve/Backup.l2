/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package bosses;

import bosses.EpicBossState;
import bosses.SailrenManager;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

private static class SailrenManager.SailrenSpawn
extends RunnableImpl {
    private int _npcId;
    private final Location j = new Location(27628, -6109, -1982, 44732);

    SailrenManager.SailrenSpawn(int n) {
        this._npcId = n;
    }

    public void runImpl() throws Exception {
        if (f != null) {
            f.cancel(false);
            f = null;
        }
        switch (this._npcId) {
            case 22198: {
                a.set(false);
                k = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22198);
                ((DefaultAI)k.getAI()).addTaskMove(this.j, false);
                if (f != null) {
                    f.cancel(false);
                    f = null;
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.Social(k, 2)), 6000L);
                if (p != null) {
                    p.cancel(false);
                    p = null;
                }
                p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                break;
            }
            case 22199: {
                l = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22199);
                ((DefaultAI)l.getAI()).addTaskMove(this.j, false);
                if (f != null) {
                    f.cancel(false);
                    f = null;
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.Social(l, 2)), 6000L);
                if (p != null) {
                    p.cancel(false);
                    p = null;
                }
                p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                break;
            }
            case 22217: {
                m = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22217);
                ((DefaultAI)m.getAI()).addTaskMove(this.j, false);
                if (f != null) {
                    f.cancel(false);
                    f = null;
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.Social(m, 2)), 6000L);
                if (p != null) {
                    p.cancel(false);
                    p = null;
                }
                p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                break;
            }
            case 29065: {
                n = Functions.spawn((Location)new Location(27810, -5655, -1983, 44732), (int)29065);
                _state.setRespawnDate(SailrenManager.a() + Config.FWS_ACTIVITYTIMEOFMOBS);
                _state.setState(EpicBossState.State.ALIVE);
                _state.update();
                n.setRunning();
                ((DefaultAI)n.getAI()).addTaskMove(this.j, false);
                if (f != null) {
                    f.cancel(false);
                    f = null;
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.Social(n, 2)), 6000L);
                if (p != null) {
                    p.cancel(false);
                    p = null;
                }
                p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenManager.ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
            }
        }
    }
}
