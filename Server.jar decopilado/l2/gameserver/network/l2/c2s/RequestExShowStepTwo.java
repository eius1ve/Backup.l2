/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestExShowStepTwo
extends L2GameClientPacket {
    private int rb;

    @Override
    protected void readImpl() {
        this.rb = this.readC();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
