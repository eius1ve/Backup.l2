/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ClientSetTime;

class GameTimeController.CheckSunState.3
extends RunnableImpl {
    GameTimeController.CheckSunState.3() {
    }

    @Override
    public void runImpl() throws Exception {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            try {
                player.checkDayNightMessages();
                player.sendPacket((IStaticPacket)ClientSetTime.STATIC);
            }
            catch (Exception exception) {}
        }
    }
}
