/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

class SuperCmdSummonCmd
extends L2GameClientPacket {
    private String eI;

    SuperCmdSummonCmd() {
    }

    @Override
    protected void readImpl() {
        this.eI = this.readS();
    }

    @Override
    protected void runImpl() {
    }
}
