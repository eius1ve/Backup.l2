/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;

public class QuestState.OnDeathListenerImpl
implements OnDeathListener {
    @Override
    public void onDeath(Creature creature, Creature creature2) {
        Player player = creature.getPlayer();
        if (player == null) {
            return;
        }
        player.removeListener(this);
        QuestState.this.a.notifyDeath(creature2, creature, QuestState.this);
    }
}
