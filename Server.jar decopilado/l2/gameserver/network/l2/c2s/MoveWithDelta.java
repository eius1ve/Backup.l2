/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class MoveWithDelta
extends L2GameClientPacket {
    private int qf;
    private int qg;
    private int qh;

    @Override
    protected void readImpl() {
        this.qf = this.readD();
        this.qg = this.readD();
        this.qh = this.readD();
    }

    @Override
    protected void runImpl() {
    }
}
