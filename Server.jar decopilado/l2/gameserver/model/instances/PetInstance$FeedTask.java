/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;

class PetInstance.FeedTask
extends RunnableImpl {
    PetInstance.FeedTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Player player = PetInstance.this.getPlayer();
        while ((double)PetInstance.this.getCurrentFed() <= 0.55 * (double)PetInstance.this.getMaxFed() && PetInstance.this.tryFeed()) {
        }
        if ((double)PetInstance.this.getCurrentFed() <= 0.1 * (double)PetInstance.this.getMaxFed()) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2PetInstance.UnSummonHungryPet", player, new Object[0]));
            PetInstance.this.unSummon();
            return;
        }
        PetInstance.this.setCurrentFed(PetInstance.this.getCurrentFed() - 5);
        PetInstance.this.sendStatusUpdate();
        PetInstance.this.startFeed(PetInstance.this.isInCombat());
    }
}
