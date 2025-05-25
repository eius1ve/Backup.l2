/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;

public class PvPEventRegistration
implements IVoicedCommandHandler {
    private String[] o = new String[]{"tvtjoin", "pvpjoin", "ctfjoin", "dmjoin"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.EVENT_PVP_REGISTRATION_COMMAND) {
            return false;
        }
        return this.d(string, player, string2);
    }

    private boolean d(String string, Player player, String string2) {
        String string3 = HtmCache.getInstance().getNotNull("command/pvpevent_registration.htm", player);
        Functions.show(string3, player, null, new Object[0]);
        return true;
    }

    @Override
    public String[] getVoicedCommandList() {
        return this.o;
    }
}
