/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PledgeReceiveUpdatePower;

public class ManagePledgePower
extends L2GameServerPacket {
    private int bC;
    private int fY;
    private int zA;

    public ManagePledgePower(Player player, int n, int n2) {
        this.fY = player.getClanId();
        this.bC = n;
        RankPrivs rankPrivs = player.getClan().getRankPrivs(n2);
        this.zA = rankPrivs == null ? 0 : rankPrivs.getPrivs();
        player.sendPacket((IStaticPacket)new PledgeReceiveUpdatePower(this.zA));
    }

    @Override
    protected final void writeImpl() {
        this.writeC(42);
        this.writeD(this.fY);
        this.writeD(this.bC);
        this.writeD(this.zA);
    }
}
