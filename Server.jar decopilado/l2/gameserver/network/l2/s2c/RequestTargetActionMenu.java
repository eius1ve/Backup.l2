/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.Action;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestTargetActionMenu
extends L2GameClientPacket {
    private int objectId;

    @Override
    protected void readImpl() throws Exception {
        this.objectId = this.readD();
        this.readH();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Action.onAction(player, this.objectId, false);
    }
}
