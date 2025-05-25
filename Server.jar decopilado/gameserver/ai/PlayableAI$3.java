/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.NextAction;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.utils.Location;

class PlayableAI.3
extends RunnableImpl {
    final /* synthetic */ Playable val$actor;
    final /* synthetic */ Location val$moveToLoc;
    final /* synthetic */ GameObject val$target;

    PlayableAI.3(Playable playable, Location location, GameObject gameObject) {
        this.val$actor = playable;
        this.val$moveToLoc = location;
        this.val$target = gameObject;
    }

    @Override
    public void runImpl() {
        this.val$actor.moveToLocation(this.val$moveToLoc, 0, true);
        PlayableAI.this.setNextAction(NextAction.PICKUP, this.val$target, null, false, false);
    }
}
