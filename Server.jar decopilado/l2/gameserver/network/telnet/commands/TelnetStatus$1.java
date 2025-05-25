/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.commons.lang.StatsUtils;
import l2.gameserver.Config;
import l2.gameserver.Shutdown;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.World;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.commands.TelnetStatus;
import l2.gameserver.tables.GmListTable;
import l2.gameserver.utils.Util;

class TelnetStatus.1
extends TelnetCommand {
    TelnetStatus.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "status";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = World.getStats();
        stringBuilder.append("Server Status: ").append("\r\n");
        stringBuilder.append("Players: ................. ").append(nArray[12]).append("/").append(Config.MAXIMUM_ONLINE_USERS).append("\r\n");
        stringBuilder.append("     Online: ............. ").append(nArray[12] - nArray[13]).append("\r\n");
        stringBuilder.append("     Offline: ............ ").append(nArray[13]).append("\r\n");
        stringBuilder.append("     GM: ................. ").append(GmListTable.getAllGMs().size()).append("\r\n");
        stringBuilder.append("Objects: ................. ").append(nArray[10]).append("\r\n");
        stringBuilder.append("Characters: .............. ").append(nArray[11]).append("\r\n");
        stringBuilder.append("Summons: ................. ").append(nArray[18]).append("\r\n");
        stringBuilder.append("Npcs: .................... ").append(nArray[15]).append("/").append(nArray[14]).append("\r\n");
        stringBuilder.append("Monsters: ................ ").append(nArray[16]).append("\r\n");
        stringBuilder.append("Minions: ................. ").append(nArray[17]).append("\r\n");
        stringBuilder.append("Doors: ................... ").append(nArray[19]).append("\r\n");
        stringBuilder.append("Items: ................... ").append(nArray[20]).append("\r\n");
        stringBuilder.append("Reflections: ............. ").append(ReflectionManager.getInstance().getAll().length).append("\r\n");
        stringBuilder.append("Regions: ................. ").append(nArray[0]).append("\r\n");
        stringBuilder.append("     Active: ............. ").append(nArray[1]).append("\r\n");
        stringBuilder.append("     Inactive: ........... ").append(nArray[2]).append("\r\n");
        stringBuilder.append("     Null: ............... ").append(nArray[3]).append("\r\n");
        stringBuilder.append("Game Time: ............... ").append(TelnetStatus.getGameTime()).append("\r\n");
        stringBuilder.append("Real Time: ............... ").append(TelnetStatus.getCurrentTime()).append("\r\n");
        stringBuilder.append("Start Time: .............. ").append(TelnetStatus.getStartTime()).append("\r\n");
        stringBuilder.append("Uptime: .................. ").append(TelnetStatus.getUptime()).append("\r\n");
        stringBuilder.append("Shutdown: ................ ").append(Util.formatTime(Shutdown.getInstance().getSeconds())).append("/").append(Shutdown.getInstance().getMode()).append("\r\n");
        stringBuilder.append("Threads: ................. ").append(Thread.activeCount()).append("\r\n");
        stringBuilder.append("RAM Used: ................ ").append(StatsUtils.getMemUsedMb()).append("\r\n");
        return stringBuilder.toString();
    }
}
