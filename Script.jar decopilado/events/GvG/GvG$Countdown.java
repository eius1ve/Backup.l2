/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 */
package events.GvG;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;

public static class GvG.Countdown
extends RunnableImpl {
    int _timer;

    public GvG.Countdown(int n) {
        this._timer = n;
    }

    public void runImpl() throws Exception {
        Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.timeexpend", new String[]{Integer.toString(this._timer)});
    }
}
