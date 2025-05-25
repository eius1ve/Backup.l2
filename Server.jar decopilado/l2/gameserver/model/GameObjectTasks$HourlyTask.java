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

public static class GameObjectTasks.HourlyTask
extends RunnableImpl {
    private final HardReference<Player> G;

    public GameObjectTasks.HourlyTask(Player player) {
        this.G = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.G.get();
        if (player == null) {
            return;
        }
        int n = player.getHoursInGame();
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_BEEN_PLAYING_FOR_AN_EXTENDED_PERIOD_OF_TIME_PLEASE_CONSIDER_TAKING_A_BREAK_S1).addNumber(n));
    }
}
