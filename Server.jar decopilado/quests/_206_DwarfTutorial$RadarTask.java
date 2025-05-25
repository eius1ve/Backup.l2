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

private class _206_DwarfTutorial.RadarTask
extends RunnableImpl {
    private final int Xf;
    private final int Xg;
    private final int Xh;
    private final Player w;

    _206_DwarfTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.Xf = n;
        this.Xg = n2;
        this.Xh = n3;
        this.w = player;
    }

    public void runImpl() throws Exception {
        this.w.sendPacket((IStaticPacket)new RadarControl(0, 1, this.Xf, this.Xg, this.Xh));
    }
}
