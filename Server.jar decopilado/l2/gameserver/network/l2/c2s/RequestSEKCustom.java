/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestSEKCustom
extends L2GameClientPacket {
    private int rW;
    private int rX;

    @Override
    protected void readImpl() {
        this.rW = this.readD();
        this.rX = this.readD();
    }

    @Override
    protected void runImpl() {
    }
}
