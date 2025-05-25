/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class SevenSigns.SevenSignsAnnounce
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        if (Config.SEND_SSQ_WELCOME_MESSAGE) {
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                SevenSigns.this.sendCurrentPeriodMsg(player);
            }
            ThreadPoolManager.getInstance().schedule(new SevenSigns.SevenSignsAnnounce(), (long)Config.SS_ANNOUNCE_PERIOD * 1000L * 60L);
        }
    }
}
