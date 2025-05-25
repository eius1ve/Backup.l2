/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.GameTimeController;
import l2.gameserver.model.Fishing;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

protected class Fishing.LookingForFishTask
extends RunnableImpl {
    private long bd;

    protected Fishing.LookingForFishTask() {
        this.bd = System.currentTimeMillis() + (long)Fishing.this.a.getWaitTime() + 10000L;
    }

    @Override
    public void runImpl() throws Exception {
        if (System.currentTimeMillis() >= this.bd) {
            Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THE_BAIT_HAS_BEEN_LOST_BECAUSE_THE_FISH_GOT_AWAY);
            Fishing.this.ba();
            Fishing.this.endFishing(false);
            return;
        }
        if (!GameTimeController.getInstance().isNowNight() && Fishing.isNightLure(Fishing.this.gZ)) {
            Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THE_BAIT_HAS_BEEN_LOST_BECAUSE_THE_FISH_GOT_AWAY);
            Fishing.this.ba();
            Fishing.this.endFishing(false);
            return;
        }
        int n = Rnd.get(1000);
        if (Fishing.this.a.getFishGuts() > n) {
            Fishing.this.ba();
            Fishing.this.bc();
        }
    }
}
