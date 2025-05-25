/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package l2.gameserver.handler.telegram.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import l2.commons.lang.StatsUtils;
import l2.gameserver.Config;
import l2.gameserver.GameTimeController;
import l2.gameserver.Shutdown;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.World;
import l2.gameserver.tables.GmListTable;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class StatusCommand
implements ITelegramCommandHandler {
    private final String[] as = new String[]{"/status", "/s"};

    private static String getGameTime() {
        int n = GameTimeController.getInstance().getGameTime();
        int n2 = n / 60;
        int n3 = n % 60;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, n2);
        calendar.set(12, n3);
        return simpleDateFormat.format(calendar.getTime());
    }

    private static String getUptime() {
        return DurationFormatUtils.formatDurationHMS((long)ManagementFactory.getRuntimeMXBean().getUptime());
    }

    private static String getStartTime() {
        return new Date(ManagementFactory.getRuntimeMXBean().getStartTime()).toString();
    }

    private static String getCurrentTime() {
        return new Date().toString();
    }

    @Override
    public void handle(String string, String string2) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = World.getStats();
        stringBuilder.append("Server Status: ").append("\n");
        stringBuilder.append("Players: ................. ").append(nArray[12]).append("/").append(Config.MAXIMUM_ONLINE_USERS).append("\n");
        stringBuilder.append("     Online: ............. ").append(nArray[12] - nArray[13]).append("\n");
        stringBuilder.append("     Offline: ............ ").append(nArray[13]).append("\n");
        stringBuilder.append("     GM: ................. ").append(GmListTable.getAllGMs().size()).append("\n");
        stringBuilder.append("Objects: ................. ").append(nArray[10]).append("\n");
        stringBuilder.append("Characters: .............. ").append(nArray[11]).append("\n");
        stringBuilder.append("Summons: ................. ").append(nArray[18]).append("\n");
        stringBuilder.append("Npcs: .................... ").append(nArray[15]).append("/").append(nArray[14]).append("\n");
        stringBuilder.append("Monsters: ................ ").append(nArray[16]).append("\n");
        stringBuilder.append("Minions: ................. ").append(nArray[17]).append("\n");
        stringBuilder.append("Doors: ................... ").append(nArray[19]).append("\n");
        stringBuilder.append("Items: ................... ").append(nArray[20]).append("\n");
        stringBuilder.append("Reflections: ............. ").append(ReflectionManager.getInstance().getAll().length).append("\n");
        stringBuilder.append("Regions: ................. ").append(nArray[0]).append("\n");
        stringBuilder.append("     Active: ............. ").append(nArray[1]).append("\n");
        stringBuilder.append("     Inactive: ........... ").append(nArray[2]).append("\n");
        stringBuilder.append("     Null: ............... ").append(nArray[3]).append("\n");
        stringBuilder.append("Game Time: ............... ").append(StatusCommand.getGameTime()).append("\n");
        stringBuilder.append("Real Time: ............... ").append(StatusCommand.getCurrentTime()).append("\n");
        stringBuilder.append("Start Time: .............. ").append(StatusCommand.getStartTime()).append("\n");
        stringBuilder.append("Uptime: .................. ").append(StatusCommand.getUptime()).append("\n");
        stringBuilder.append("Shutdown: ................ ").append(Util.formatTime(Shutdown.getInstance().getSeconds())).append("/").append(Shutdown.getInstance().getMode()).append("\n");
        stringBuilder.append("Threads: ................. ").append(Thread.activeCount()).append("\n");
        stringBuilder.append("RAM Used: ................ ").append(StatsUtils.getMemUsedMb()).append("\n");
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.as;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
