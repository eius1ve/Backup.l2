/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.RadarControl
 */
package quests;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.RadarControl;

private class _204_DelfTutorial.RadarTask
extends RunnableImpl {
    private final int WI;
    private final int WJ;
    private final int WK;
    private final Player u;

    _204_DelfTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.WI = n;
        this.WJ = n2;
        this.WK = n3;
        this.u = player;
    }

    public void runImpl() throws Exception {
        this.u.sendPacket((IStaticPacket)new RadarControl(0, 1, this.WI, this.WJ, this.WK));
    }
}
