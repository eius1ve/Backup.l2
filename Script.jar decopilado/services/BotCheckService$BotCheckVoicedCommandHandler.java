/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.model.Player
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import org.apache.commons.lang3.ArrayUtils;
import services.BotCheckService;

private static class BotCheckService.BotCheckVoicedCommandHandler
implements IVoicedCommandHandler {
    private final String[] aW = new String[]{"check_captcha"};

    private BotCheckService.BotCheckVoicedCommandHandler() {
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICE_CAPTCHA_BOT_CHECK) {
            return false;
        }
        if (this.aW[0].equalsIgnoreCase(string)) {
            BotCheckService.b(player, BotCheckService.c(player));
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICE_CAPTCHA_BOT_CHECK) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.aW;
    }
}
