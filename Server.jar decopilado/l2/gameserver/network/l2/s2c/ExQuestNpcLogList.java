/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.quest.QuestNpcLogInfo;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExQuestNpcLogList
extends L2GameServerPacket {
    private int _questId;
    private List<int[]> cq = Collections.emptyList();

    public ExQuestNpcLogList(QuestState questState) {
        this._questId = questState.getQuest().getQuestIntId();
        int n = questState.getCond();
        List<QuestNpcLogInfo> list = questState.getQuest().getNpcLogList(n);
        if (list == null) {
            return;
        }
        this.cq = new ArrayList<int[]>(list.size());
        for (QuestNpcLogInfo questNpcLogInfo : list) {
            int[] nArray = new int[]{questNpcLogInfo.getNpcIds()[0] + 1000000, questState.getInt(questNpcLogInfo.getVarName())};
            this.cq.add(nArray);
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(198);
        this.writeD(this._questId);
        this.writeC(this.cq.size());
        for (int i = 0; i < this.cq.size(); ++i) {
            int[] nArray = this.cq.get(i);
            this.writeD(nArray[0]);
            this.writeC(0);
            this.writeD(nArray[1]);
        }
    }
}
