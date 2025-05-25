/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.VipManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ReceiveVipInfo;

public class RequestVipInfo
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        VipManager vipManager = VipManager.getInstance();
        if (player == null || vipManager == null || !Config.ENABLE_PRIME_SHOP) {
            return;
        }
        player.sendPacket((IStaticPacket)new ReceiveVipInfo(player, vipManager));
    }
}
