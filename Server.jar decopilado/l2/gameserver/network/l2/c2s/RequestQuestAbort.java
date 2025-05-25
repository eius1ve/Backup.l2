/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestQuestAbort
extends L2GameClientPacket {
    private int rS;

    @Override
    protected void readImpl() {
        this.rS = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        Quest quest = QuestManager.getQuest(this.rS);
        if (player == null || quest == null) {
            return;
        }
        if (!quest.canAbortByPacket()) {
            return;
        }
        QuestState questState = player.getQuestState(quest.getClass());
        if (questState != null && !questState.isCompleted()) {
            questState.abortQuest();
        }
    }
}
