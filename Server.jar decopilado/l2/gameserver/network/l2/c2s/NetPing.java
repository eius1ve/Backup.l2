/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class NetPing
extends L2GameClientPacket {
    public static final int MIN_CLIP_RANGE = 1433;
    public static final int MAX_CLIP_RANGE = 6144;
    private int qi;
    private int qj;
    private int pF;

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient.getRevision() == 0) {
            gameClient.closeNow(false);
        } else {
            gameClient.onPing(this.qi, this.pF, Math.max(1433, Math.min(this.qj, 6144)));
        }
    }

    @Override
    protected void readImpl() {
        this.qi = this.readD();
        this.pF = this.readD();
        this.qj = this.readD();
    }
}
