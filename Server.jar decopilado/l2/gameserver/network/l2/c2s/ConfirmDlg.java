/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import org.apache.commons.lang3.tuple.Pair;

public class ConfirmDlg
extends L2GameClientPacket {
    private int _answer;
    private int qa;

    @Override
    protected void readImpl() {
        this.readD();
        this._answer = this.readD();
        this.qa = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Pair<Integer, OnAnswerListener> pair = player.getAskListener(true);
        if (pair == null || (Integer)pair.getKey() != this.qa) {
            return;
        }
        OnAnswerListener onAnswerListener = (OnAnswerListener)pair.getValue();
        if (this._answer == 1) {
            onAnswerListener.sayYes();
        } else {
            onAnswerListener.sayNo();
        }
    }
}
