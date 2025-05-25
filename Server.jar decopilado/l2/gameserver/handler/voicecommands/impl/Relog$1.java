/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.CharSelected;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

class Relog.1
extends RunnableImpl {
    final /* synthetic */ GameClient val$client;
    final /* synthetic */ int val$objId;

    Relog.1(GameClient gameClient, int n) {
        this.val$client = gameClient;
        this.val$objId = n;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.val$client == null || !this.val$client.isConnected() || !this.val$client.isAuthed()) {
            return;
        }
        if (Config.USE_SECOND_PASSWORD_AUTH && !this.val$client.isSecondPasswordAuthed()) {
            return;
        }
        int n = this.val$client.getSlotForObjectId(this.val$objId);
        if (n < 0) {
            return;
        }
        Player player = this.val$client.loadCharFromDisk(n);
        this.val$client.setState(GameClient.GameClientState.IN_GAME);
        this.val$client.sendPacket((L2GameServerPacket)new CharSelected(player, this.val$client.getSessionKey().playOkID1));
    }
}
