/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

public static class FeedableBeastInstance.AggrPlayer
extends RunnableImpl {
    private NpcInstance w;
    private Player l;

    public FeedableBeastInstance.AggrPlayer(NpcInstance npcInstance, Player player) {
        this.w = npcInstance;
        this.l = player;
    }

    @Override
    public void runImpl() throws Exception {
        this.w.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, this.l, 1000);
    }
}
