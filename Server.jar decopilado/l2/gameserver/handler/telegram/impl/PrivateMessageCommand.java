/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;

public class PrivateMessageCommand
implements ITelegramCommandHandler {
    private final String[] ah = new String[]{"/pm"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /pm <name> <message>", "Enter private message in format: <name> <message>");
        } else {
            String[] stringArray = string2.split(" ", 2);
            if (stringArray.length < 2) {
                TelegramApi.sendMessage(string, "Usage: /pm <player> <message>");
                return;
            }
            String string3 = stringArray[0];
            String string4 = stringArray[1];
            Player player = World.getPlayer(string3);
            if (player == null) {
                TelegramApi.sendMessage(string, "Player not found.");
                return;
            }
            Say2 say2 = new Say2(0, ChatType.TELL, "[Admin]", string4);
            player.sendPacket((IStaticPacket)say2);
            TelegramApi.sendMessage(string, "Message sent to " + string3 + ".");
        }
    }

    @Override
    public String[] getCommands() {
        return this.ah;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
