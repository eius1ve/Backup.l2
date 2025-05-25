/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public static class GameObjectTasks.WaterTask
extends RunnableImpl {
    private final HardReference<Player> O;

    public GameObjectTasks.WaterTask(Player player) {
        this.O = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.O.get();
        if (player == null) {
            return;
        }
        if (player.isDead() || !player.isInWater()) {
            player.stopWaterTask();
            return;
        }
        double d = player.getMaxHp() < 100 ? 1.0 : (double)(player.getMaxHp() / 100);
        player.reduceCurrentHp(d, player, null, false, false, true, false, false, false, false);
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_TAKEN_S1_DAMAGE_BECAUSE_YOU_WERE_UNABLE_TO_BREATHE).addNumber((long)d));
    }
}
