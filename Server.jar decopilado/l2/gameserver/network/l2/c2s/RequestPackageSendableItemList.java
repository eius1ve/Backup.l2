/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.PackageSendableList;

public class RequestPackageSendableItemList
extends L2GameClientPacket {
    private int fW;

    @Override
    protected void readImpl() throws Exception {
        this.fW = this.readD();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.sendPacket(new PackageSendableList(1, this.fW, player), new PackageSendableList(2, this.fW, player));
    }
}
