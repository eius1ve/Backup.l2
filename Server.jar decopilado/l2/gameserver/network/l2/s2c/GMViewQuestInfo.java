/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class GMViewQuestInfo
extends L2GameServerPacket {
    private final Player o;

    public GMViewQuestInfo(Player player) {
        this.o = player;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(153);
        this.writeS(this.o.getName());
        Quest[] questArray = this.o.getAllActiveQuests();
        if (questArray.length == 0) {
            this.writeH(0);
            this.writeH(0);
            return;
        }
        this.writeH(questArray.length);
        for (Quest quest : questArray) {
            this.writeD(quest.getQuestIntId());
            QuestState questState = this.o.getQuestState(quest.getName());
            this.writeD(questState == null ? 0 : questState.getInt("cond"));
        }
        this.writeH(0);
    }
}
