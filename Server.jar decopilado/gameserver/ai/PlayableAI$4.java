/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;

class PlayableAI.4
extends RunnableImpl {
    final /* synthetic */ Playable val$actor;
    final /* synthetic */ GameObject val$target;
    final /* synthetic */ int val$moveIndent;
    final /* synthetic */ int val$moveRange;

    PlayableAI.4(Playable playable, GameObject gameObject, int n, int n2) {
        this.val$actor = playable;
        this.val$target = gameObject;
        this.val$moveIndent = n;
        this.val$moveRange = n2;
    }

    @Override
    public void runImpl() throws Exception {
        this.val$actor.moveToRelative(this.val$target, this.val$moveIndent, this.val$moveRange);
    }
}
