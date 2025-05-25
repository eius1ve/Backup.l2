/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.tables.GmListTable;

class TelnetWorld.3
extends TelnetCommand {
    TelnetWorld.3(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "gmlist";
    }

    @Override
    public String handle(String[] stringArray) {
        List<Player> list = GmListTable.getAllGMs();
        int n = list.size();
        if (n == 0) {
            return "GMs not found.\r\n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(list.get(i)).append("\r\n");
        }
        stringBuilder.append("Found: ").append(n).append(" GMs.").append("\r\n");
        return stringBuilder.toString();
    }
}
