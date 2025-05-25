/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.RadarControl
 */
package npc.model;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.RadarControl;

private class NewbieGuideInstance.RadarTask
extends RunnableImpl {
    private final int HG;
    private final int HH;
    private final int HI;
    private final Player q;

    NewbieGuideInstance.RadarTask(int n, int n2, int n3, Player player) {
        this.HG = n;
        this.HH = n2;
        this.HI = n3;
        this.q = player;
    }

    public void runImpl() throws Exception {
        this.q.sendPacket((IStaticPacket)new RadarControl(0, 1, this.HG, this.HH, this.HI));
    }
}
