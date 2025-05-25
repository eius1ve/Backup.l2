/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;

public class Jump
extends Functions
implements IVoicedCommandHandler {
    private final String[] aC = new String[]{"jump"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.ALT_ALLOW_JUMP_COMMAND) {
            return false;
        }
        if (player != null) {
            player.broadcastPlayerJump(player);
        }
        return false;
    }

    @Override
    public String[] getVoicedCommandList() {
        return this.aC;
    }
}
