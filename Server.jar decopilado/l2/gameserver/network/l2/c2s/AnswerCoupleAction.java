/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class AnswerCoupleAction
extends L2GameClientPacket {
    private int pM;
    private int pK;
    private int _answer;

    @Override
    protected void readImpl() {
        this.pK = this.readD();
        this._answer = this.readD();
        this.pM = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
