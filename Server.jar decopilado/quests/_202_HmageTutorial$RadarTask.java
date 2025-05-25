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

private class _202_HmageTutorial.RadarTask
extends RunnableImpl {
    private final int Wm;
    private final int Wn;
    private final int Wo;
    private final Player s;

    _202_HmageTutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.Wm = n;
        this.Wn = n2;
        this.Wo = n3;
        this.s = player;
    }

    public void runImpl() throws Exception {
        this.s.sendPacket((IStaticPacket)new RadarControl(0, 1, this.Wm, this.Wn, this.Wo));
    }
}
