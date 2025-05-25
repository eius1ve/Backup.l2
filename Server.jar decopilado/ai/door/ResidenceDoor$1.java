/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnAnswerListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.DoorInstance
 */
package ai.door;

import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;

class ResidenceDoor.1
implements OnAnswerListener {
    final /* synthetic */ DoorInstance val$door;
    final /* synthetic */ Player val$player;

    ResidenceDoor.1() {
        this.val$door = doorInstance;
        this.val$player = player;
    }

    public void sayYes() {
        if (this.val$door.isOpen()) {
            this.val$door.closeMe(this.val$player, true);
        } else {
            this.val$door.openMe(this.val$player, true);
        }
    }

    public void sayNo() {
    }
}
