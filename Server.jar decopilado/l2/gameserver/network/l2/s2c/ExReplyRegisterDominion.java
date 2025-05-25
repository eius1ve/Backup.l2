/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExReplyRegisterDominion
extends L2GameServerPacket {
    private int qQ;
    private int xa;
    private int _playerCount;
    private boolean eL;
    private boolean eM;
    private boolean eN;

    @Override
    protected void writeImpl() {
        this.writeEx(145);
        this.writeD(this.qQ);
        this.writeD(this.eN);
        this.writeD(this.eM);
        this.writeD(this.eL);
        this.writeD(this.xa);
        this.writeD(this._playerCount);
    }
}
