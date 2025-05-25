/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;

class PlayableAI.2
extends RunnableImpl {
    final /* synthetic */ Playable val$actor;
    final /* synthetic */ Creature val$target;
    final /* synthetic */ int val$moveIndent;
    final /* synthetic */ int val$moveRange;

    PlayableAI.2(Playable playable, Creature creature, int n, int n2) {
        this.val$actor = playable;
        this.val$target = creature;
        this.val$moveIndent = n;
        this.val$moveRange = n2;
    }

    @Override
    public void runImpl() throws Exception {
        this.val$actor.moveToRelative(this.val$target, this.val$moveIndent, this.val$moveRange);
    }
}
