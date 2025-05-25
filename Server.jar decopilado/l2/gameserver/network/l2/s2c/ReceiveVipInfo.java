/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.VipManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ReceiveVipInfo
extends L2GameServerPacket {
    private final long dr;
    private final int AH;
    private final byte e;
    private final long ds;
    private final long dt;
    private final long du;

    public ReceiveVipInfo(Player player, VipManager vipManager) {
        this.dr = player.getVipPoints();
        this.AH = (int)ChronoUnit.SECONDS.between(Instant.now(), Instant.ofEpochMilli(player.getVipTierExpiration()));
        this.e = player.getVipLevel();
        this.ds = vipManager.getPointsToLevel((byte)(this.e + 1));
        this.dt = vipManager.getPointsDepreciatedOnLevel(this.e);
        this.du = vipManager.getPointsToLevel(this.e);
    }

    @Override
    protected void writeImpl() {
        if (!Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            return;
        }
        this.writeEx(393);
        this.writeC(this.e);
        this.writeQ(this.dr);
        this.writeD(this.AH);
        this.writeQ(this.ds);
        this.writeQ(this.dt);
        this.writeC(this.e);
        this.writeQ(this.du);
    }
}
