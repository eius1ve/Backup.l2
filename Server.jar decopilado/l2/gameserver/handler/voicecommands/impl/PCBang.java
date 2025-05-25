/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;

public class PCBang
extends Functions
implements IVoicedCommandHandler {
    private final String[] aE = new String[]{"pcbang", "pc"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (Config.ALT_PCBANG_POINTS_COMMAND) {
            player.sendMessage(new CustomMessage("scripts.commands.user.pcbangpoints", player, new Object[0]).addNumber(player.getPcBangPoints()));
            return false;
        }
        return true;
    }

    @Override
    public String[] getVoicedCommandList() {
        return this.aE;
    }
}
