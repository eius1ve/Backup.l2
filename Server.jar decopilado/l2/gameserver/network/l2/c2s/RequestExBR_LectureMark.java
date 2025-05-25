/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestExBR_LectureMark
extends L2GameClientPacket {
    public static final int INITIAL_MARK = 1;
    public static final int EVANGELIST_MARK = 2;
    public static final int OFF_MARK = 3;
    private int qK;

    @Override
    protected void readImpl() throws Exception {
        this.qK = this.readC();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
