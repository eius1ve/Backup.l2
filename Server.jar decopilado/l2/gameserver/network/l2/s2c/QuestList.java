/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class QuestList
extends L2GameServerPacket {
    private final List<QuestState> cX = new LinkedList<QuestState>();
    private final byte[] y = new byte[128];

    public QuestList(Player player) {
        for (QuestState questState : player.getAllQuestsStates()) {
            int n = questState.getQuest().getQuestIntId();
            if (n <= 0 || !questState.getQuest().isVisible()) continue;
            if (questState.isStarted()) {
                this.cX.add(questState);
                continue;
            }
            if (!questState.isCompleted() || n >= 1024) continue;
            int n2 = n % 10000 / 8;
            this.y[n2] = (byte)(this.y[n2] | 1 << n % 8);
        }
    }

    @Override
    protected void writeImpl() {
        this.writeC(134);
        this.writeH(this.cX.size());
        for (QuestState questState : this.cX) {
            this.writeD(questState.getQuest().getQuestIntId());
            this.writeD(questState.getCond());
        }
        this.writeB(this.y);
    }
}
