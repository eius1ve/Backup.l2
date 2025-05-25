/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 */
package bosses;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

private class FrintezzaManager.TombTimeoutTask
extends RunnableImpl {
    private int _timeLeft = Config.FRINTEZZA_TOMB_TIMEOUT;

    public void runImpl() throws Exception {
        if (this._timeLeft > 0) {
            List list = FrintezzaManager.this.e.getInsidePlayers();
            for (Player player : list) {
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("LastImperialTomb.Remaining", player, new Object[]{this._timeLeft}).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.BOTTOM_RIGHT, false));
            }
            if (this._timeLeft > 5) {
                this._timeLeft -= 5;
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 300000L);
            } else {
                --this._timeLeft;
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 60000L);
            }
        } else {
            FrintezzaManager.this.cleanUp();
        }
    }
}
