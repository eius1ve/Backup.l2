/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SunRise
extends L2GameServerPacket {
    public static final SunRise STATIC_PACKET = new SunRise();

    @Override
    protected final void writeImpl() {
        this.writeC(18);
    }
}
