/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public final class ExEnchantOneOK
extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC = new ExEnchantOneOK();

    @Override
    protected void writeImpl() {
        this.writeEx(360);
    }
}
