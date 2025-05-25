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

private class _255_Tutorial.RadarTask
extends RunnableImpl {
    private final int atq;
    private final int atr;
    private final int ats;
    private final Player x;

    _255_Tutorial.RadarTask(int n, int n2, int n3, Player player) {
        this.atq = n;
        this.atr = n2;
        this.ats = n3;
        this.x = player;
    }

    public void runImpl() throws Exception {
        this.x.sendPacket((IStaticPacket)new RadarControl(0, 1, this.atq, this.atr, this.ats));
    }
}
