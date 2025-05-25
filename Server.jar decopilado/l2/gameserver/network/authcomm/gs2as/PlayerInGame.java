/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.network.authcomm.SendablePacket;

public class PlayerInGame
extends SendablePacket {
    private String account;
    private String aI;

    public PlayerInGame(String string, String string2) {
        this.account = string;
        this.aI = string2;
    }

    public PlayerInGame(String string) {
        this(string, "");
    }

    @Override
    protected void writeImpl() {
        this.writeC(3);
        this.writeS(this.account);
        this.writeS(this.aI);
    }
}
