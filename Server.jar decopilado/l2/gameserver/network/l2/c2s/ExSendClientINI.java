/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class ExSendClientINI
extends L2GameClientPacket {
    private int qb;
    private byte[] o;

    @Override
    protected void readImpl() {
        this.qb = this.readC();
        this.o = new byte[this.readH()];
        this.readB(this.o);
    }

    @Override
    protected void runImpl() {
    }
}
