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

private class _201_HfighterTutorial.RadarTask
extends RunnableImpl {
    private final int Wa;
    private final int Wb;
    private final int Wc;
    private final Player r;

    _201_HfighterTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.Wa = n;
        this.Wb = n2;
        this.Wc = n3;
        this.r = player;
    }

    public void runImpl() throws Exception {
        this.r.sendPacket((IStaticPacket)new RadarControl(0, 1, this.Wa, this.Wb, this.Wc));
    }
}
