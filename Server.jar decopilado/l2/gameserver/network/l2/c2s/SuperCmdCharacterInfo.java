/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

class SuperCmdCharacterInfo
extends L2GameClientPacket {
    private String eH;

    SuperCmdCharacterInfo() {
    }

    @Override
    protected void readImpl() {
        this.eH = this.readS();
    }

    @Override
    protected void runImpl() {
    }
}
