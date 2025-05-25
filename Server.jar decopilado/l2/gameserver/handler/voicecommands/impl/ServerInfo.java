/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;

public class ServerInfo
extends Functions
implements IVoicedCommandHandler {
    private final String[] aI = new String[]{"date", "time"};
    private static final DateFormat a = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    @Override
    public String[] getVoicedCommandList() {
        return this.aI;
    }

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.ALT_ALLOW_SERVER_INFO_COMMAND) {
            return false;
        }
        if (string.equals("date") || string.equals("time")) {
            player.sendMessage(a.format(new Date(System.currentTimeMillis())));
            return true;
        }
        return false;
    }
}
