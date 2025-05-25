/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 */
package dressmeEngine.B;

import java.util.logging.Level;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.UserInfo;

public static class B._A
implements Runnable {
    private final Player A;

    public B._A(Player Player2) {
        this.A = Player2;
    }

    @Override
    public void run() {
        try {
            this.A.unsetVar("tryAgathion");
            this.A.sendPacket((IStaticPacket)new UserInfo(this.A));
        }
        catch (Exception e) {
            _log.log(Level.SEVERE, "", e);
        }
    }
}
