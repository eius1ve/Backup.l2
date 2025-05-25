/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.QuestState;

public class QuestState.PlayerOnKillListenerImpl
implements OnKillListener {
    @Override
    public void onKill(Creature creature, Creature creature2) {
        if (!creature2.isPlayer()) {
            return;
        }
        Player player = (Player)creature;
        List<Object> list = null;
        switch (QuestState.this.a.getParty()) {
            case 0: {
                list = Collections.singletonList(player);
                break;
            }
            case 2: {
                if (player.getParty() == null) {
                    list = Collections.singletonList(player);
                    break;
                }
                list = new ArrayList(player.getParty().getMemberCount());
                for (Player player2 : player.getParty().getPartyMembers()) {
                    if (!player2.isInActingRange(player)) continue;
                    list.add(player2);
                }
                break;
            }
            default: {
                list = Collections.emptyList();
            }
        }
        for (Player player2 : list) {
            QuestState questState = player2.getQuestState(QuestState.this.a.getClass());
            if (questState == null || questState.isCompleted()) continue;
            QuestState.this.a.notifyKill((Player)creature2, questState);
        }
    }

    @Override
    public boolean ignorePetOrSummon() {
        return true;
    }
}
