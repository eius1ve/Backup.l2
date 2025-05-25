/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestSendMsnChatLog
extends L2GameClientPacket {
    private int qJ;
    private String ey;
    private String ez;

    @Override
    protected void runImpl() {
    }

    @Override
    protected void readImpl() {
        this.ey = this.readS();
        this.ez = this.readS();
        this.qJ = this.readD();
    }
}
