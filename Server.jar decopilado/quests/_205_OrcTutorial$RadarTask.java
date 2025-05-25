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

private class _205_OrcTutorial.RadarTask
extends RunnableImpl {
    private final int WU;
    private final int WV;
    private final int WW;
    private final Player v;

    _205_OrcTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.WU = n;
        this.WV = n2;
        this.WW = n3;
        this.v = player;
    }

    public void runImpl() throws Exception {
        this.v.sendPacket((IStaticPacket)new RadarControl(0, 1, this.WU, this.WV, this.WW));
    }
}
