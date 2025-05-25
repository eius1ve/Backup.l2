/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPCCafePointInfo
extends L2GameServerPacket {
    private int vS;
    private int vT;
    private int vU;
    private int iv;
    private int vV;

    public ExPCCafePointInfo(Player player, int n, int n2, int n3, int n4) {
        this.iv = player.getPcBangPoints();
        this.vS = n;
        this.vT = n2;
        this.vU = n3;
        this.vV = n4;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(50);
        this.writeD(this.iv);
        this.writeD(this.vS);
        this.writeC(this.vT);
        this.writeD(this.vV);
        this.writeC(this.vU);
    }
}
