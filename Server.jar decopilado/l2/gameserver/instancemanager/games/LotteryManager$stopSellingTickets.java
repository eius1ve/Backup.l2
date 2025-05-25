/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.network.l2.components.SystemMsg;

private class LotteryManager.stopSellingTickets
extends RunnableImpl {
    protected LotteryManager.stopSellingTickets() {
    }

    @Override
    public void runImpl() throws Exception {
        if (Config.SERVICES_ALLOW_LOTTERY) {
            _log.info("Lottery: Stopping ticket sell for lottery #" + LotteryManager.this.getId() + ".");
        }
        LotteryManager.this._isSellingTickets = false;
        Announcements.getInstance().announceToAll(SystemMsg.LOTTERY_TICKET_SALES_HAVE_BEEN_TEMPORARILY_SUSPENDED);
    }
}
