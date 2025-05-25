/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.network.l2.s2c.Earthquake
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public static class BaiumManager.EarthquakeTask
extends RunnableImpl {
    private final BossInstance b;

    public BaiumManager.EarthquakeTask(BossInstance bossInstance) {
        this.b = bossInstance;
    }

    public void runImpl() throws Exception {
        Earthquake earthquake = new Earthquake(this.b.getLoc(), 40, 5);
        this.b.broadcastPacket(new L2GameServerPacket[]{earthquake});
    }
}
