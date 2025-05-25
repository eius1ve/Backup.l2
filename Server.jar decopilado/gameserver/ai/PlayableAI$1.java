/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;

class PlayableAI.1
extends RunnableImpl {
    final /* synthetic */ Playable val$actor;
    final /* synthetic */ Creature val$attack_target;
    final /* synthetic */ int val$moveIndent;
    final /* synthetic */ int val$moveRange;

    PlayableAI.1(Playable playable, Creature creature, int n, int n2) {
        this.val$actor = playable;
        this.val$attack_target = creature;
        this.val$moveIndent = n;
        this.val$moveRange = n2;
    }

    @Override
    public void runImpl() throws Exception {
        this.val$actor.moveToRelative(this.val$attack_target, this.val$moveIndent, this.val$moveRange);
    }
}
