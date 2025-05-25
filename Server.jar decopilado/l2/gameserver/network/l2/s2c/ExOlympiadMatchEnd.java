/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExOlympiadMatchEnd
extends L2GameServerPacket {
    public static final ExOlympiadMatchEnd STATIC = new ExOlympiadMatchEnd();

    @Override
    protected void writeImpl() {
        this.writeEx(45);
    }
}
