/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import java.util.concurrent.ConcurrentSkipListSet;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;

private class PvPEvent.RegisrationTask
extends RunnableImpl {
    private final int cs;
    private final PvPEvent.RegisrationState b;

    public PvPEvent.RegisrationTask(PvPEvent.RegisrationState regisrationState, int n) {
        this.cs = n;
        this.b = regisrationState;
    }

    public void runImpl() throws Exception {
        if (PvPEvent.getInstance().a != this.b && this.b == PvPEvent.RegisrationState.ANNOUNCE) {
            if (PvPEvent.getInstance().b != null) {
                PvPEvent.getInstance().b.clear();
                PvPEvent.getInstance().b = null;
            }
            PvPEvent.getInstance().b = new ConcurrentSkipListSet<Integer>();
        }
        PvPEvent.getInstance().a = this.b;
        switch (this.b) {
            case ANNOUNCE: {
                if (this.cs > 0) {
                    Announcements.getInstance().announceByCustomMessage("events.PvPEvent.EventS1StartAtS2Minutes", new String[]{PvPEvent.this.getRule().name(), String.valueOf(this.cs)});
                    PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.RegisrationTask(PvPEvent.RegisrationState.ANNOUNCE, Math.max(0, this.cs - PvPEvent.getInstance().cp))), (long)(PvPEvent.getInstance().cp * 60 * 1000));
                    break;
                }
                PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.RegisrationTask(PvPEvent.RegisrationState.REQUEST, 0)), 1000L);
                break;
            }
            case REQUEST: {
                PvPEvent.getInstance().Q();
                PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.RegisrationTask(PvPEvent.getInstance().g() ? PvPEvent.RegisrationState.CAPCHA : PvPEvent.RegisrationState.MORPH, 0)), 40000L);
                break;
            }
            case CAPCHA: {
                PvPEvent.getInstance().R();
                PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.RegisrationTask(PvPEvent.RegisrationState.MORPH, 0)), 40000L);
                break;
            }
            case MORPH: {
                PvPEvent.getInstance().S();
            }
        }
    }
}
