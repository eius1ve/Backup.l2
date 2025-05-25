/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.Config;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.taskmanager.AiTaskManager;

class TelnetPerfomance.8
extends TelnetCommand {
    TelnetPerfomance.8(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "aistats";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.AI_TASK_MANAGER_COUNT; ++i) {
            stringBuilder.append("AiTaskManager #").append(i + 1).append("\r\n");
            stringBuilder.append("=================================================\r\n");
            stringBuilder.append(AiTaskManager.getInstance().getStats(i));
            stringBuilder.append("=================================================\r\n");
        }
        return stringBuilder.toString();
    }
}
