/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestExBR_EventRankerList
extends L2GameClientPacket {
    private int unk;
    private int qI;
    private int qJ;

    @Override
    protected void readImpl() {
        this.unk = this.readD();
        this.qI = this.readD();
        this.qJ = this.readD();
    }

    @Override
    protected void runImpl() {
    }
}
