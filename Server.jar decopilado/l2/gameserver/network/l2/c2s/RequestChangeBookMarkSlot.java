/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestChangeBookMarkSlot
extends L2GameClientPacket {
    private int qs;
    private int qt;

    @Override
    protected void readImpl() {
        this.qs = this.readD();
        this.qt = this.readD();
    }

    @Override
    protected void runImpl() {
    }
}
