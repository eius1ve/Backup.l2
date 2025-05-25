/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestTeleport
extends L2GameClientPacket {
    private int unk;
    private int _type;
    private int qI;
    private int qJ;
    private int sd;

    @Override
    protected void readImpl() {
        this.unk = this.readD();
        this._type = this.readD();
        if (this._type == 2) {
            this.qI = this.readD();
            this.qJ = this.readD();
        } else if (this._type == 3) {
            this.qI = this.readD();
            this.qJ = this.readD();
            this.sd = this.readD();
        }
    }

    @Override
    protected void runImpl() {
    }
}
