/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.utils.ItemFunctions;

class TelnetItems.1
extends TelnetCommand {
    TelnetItems.1(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "give_item <name> <id> <count>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length != 3 || stringArray[0].isEmpty() || stringArray[1].isEmpty() || stringArray[2].isEmpty()) {
            return null;
        }
        try {
            int n = Integer.parseInt(stringArray[1]);
            long l = Integer.parseInt(stringArray[2]);
            String string = stringArray[0];
            boolean bl = false;
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                if (!player.getName().equals(string)) continue;
                ItemFunctions.addItem((Playable)player, n, l, true);
                bl = true;
            }
            return bl ? "Issued item.\r\n" : "Player not found.\r\n";
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
