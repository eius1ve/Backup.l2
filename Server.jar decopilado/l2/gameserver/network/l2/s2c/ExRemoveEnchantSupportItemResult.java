/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExRemoveEnchantSupportItemResult
extends L2GameServerPacket {
    public static final ExRemoveEnchantSupportItemResult STATIC = new ExRemoveEnchantSupportItemResult();

    @Override
    protected void writeImpl() {
        this.writeEx(339);
    }
}
