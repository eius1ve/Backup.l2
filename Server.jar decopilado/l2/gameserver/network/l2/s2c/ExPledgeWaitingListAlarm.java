/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeWaitingListAlarm
extends L2GameServerPacket {
    public static final ExPledgeWaitingListAlarm STATIC_PACKET = new ExPledgeWaitingListAlarm();

    @Override
    protected final void writeImpl() {
        this.writeEx(327);
    }
}
