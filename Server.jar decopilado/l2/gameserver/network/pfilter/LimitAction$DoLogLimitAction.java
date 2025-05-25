/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.pfilter.LimitAction;
import l2.gameserver.utils.Log;

private static final class LimitAction.DoLogLimitAction
extends LimitAction {
    private final String fP;

    private LimitAction.DoLogLimitAction(String string) {
        this.fP = string;
    }

    @Override
    public void doIt(GameClient gameClient) {
        String string = this.fP;
        Player player = gameClient.getActiveChar();
        string = string.replace("%user_name%", player != null ? player.getName() : "");
        string = string.replace("%account_name%", gameClient.getLogin() != null ? gameClient.getLogin() : "EMPTY LOGIN");
        string = string.replace("%ip%", gameClient.getIpAddr() != null ? gameClient.getIpAddr() : "?.?.?.?");
        string = string.replace("%hwid%", gameClient.getHwid() != null ? gameClient.getHwid() : "EMPTY HWID");
        Log.network("RateLimit: " + string);
    }

    @Override
    public boolean dropPacket() {
        return false;
    }
}
