/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;

public class Announcements.Announce
extends RunnableImpl {
    private Future<?> e;
    private final int _time;
    private final String aT;

    public Announcements.Announce(int n, String string) {
        this._time = n;
        this.aT = string;
    }

    @Override
    public void runImpl() throws Exception {
        Announcements.this.announceToAll(this.aT);
    }

    public void showAnnounce(Player player) {
        Say2 say2 = new Say2(0, ChatType.ANNOUNCEMENT, player.getName(), this.aT);
        player.sendPacket((IStaticPacket)say2);
    }

    public void start() {
        if (this._time > 0) {
            this.e = ThreadPoolManager.getInstance().scheduleAtFixedRate(this, (long)this._time * 1000L, (long)this._time * 1000L);
        }
    }

    public void stop() {
        if (this.e != null) {
            this.e.cancel(false);
            this.e = null;
        }
    }

    public int getTime() {
        return this._time;
    }

    public String getAnnounce() {
        return this.aT;
    }
}
