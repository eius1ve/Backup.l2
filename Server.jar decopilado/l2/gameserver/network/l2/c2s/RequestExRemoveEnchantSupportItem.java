/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExRemoveEnchantSupportItemResult;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestExRemoveEnchantSupportItem
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
        this.sendPacket((L2GameServerPacket)ExRemoveEnchantSupportItemResult.STATIC);
    }
}
