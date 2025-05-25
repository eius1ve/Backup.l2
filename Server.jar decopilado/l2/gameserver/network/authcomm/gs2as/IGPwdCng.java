/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.model.Player;
import l2.gameserver.network.authcomm.SendablePacket;

public class IGPwdCng
extends SendablePacket {
    private int dz;
    private String aF;
    private String aG;
    private String aH;

    public IGPwdCng(Player player, String string, String string2) {
        this.dz = player.getObjectId();
        this.aF = player.getAccountName();
        this.aG = string;
        this.aH = string2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(160);
        this.writeD(this.dz);
        this.writeS(this.aF);
        this.writeS(this.aG);
        this.writeS(this.aH);
    }
}
