/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExDissmissMpccRoom
extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC = new ExDissmissMpccRoom();

    @Override
    protected void writeImpl() {
        this.writeEx(158);
    }
}
