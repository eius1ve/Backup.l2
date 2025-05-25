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

private class _203_ElfTutorial.RadarTask
extends RunnableImpl {
    private final int Wx;
    private final int Wy;
    private final int Wz;
    private final Player t;

    _203_ElfTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.Wx = n;
        this.Wy = n2;
        this.Wz = n3;
        this.t = player;
    }

    public void runImpl() throws Exception {
        this.t.sendPacket((IStaticPacket)new RadarControl(0, 1, this.Wx, this.Wy, this.Wz));
    }
}
