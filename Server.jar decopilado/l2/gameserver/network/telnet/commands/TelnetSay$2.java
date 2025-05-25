/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetSay.2
extends TelnetCommand {
    TelnetSay.2(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "message <player> <text>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length < 2) {
            return null;
        }
        Player player = World.getPlayer(stringArray[0]);
        if (player == null) {
            return "Player not found.\r\n";
        }
        Say2 say2 = new Say2(0, ChatType.TELL, "[Admin]", stringArray[1]);
        player.sendPacket((IStaticPacket)say2);
        return "Message sent.\r\n";
    }
}
