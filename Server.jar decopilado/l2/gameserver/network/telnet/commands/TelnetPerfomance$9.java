/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.Config;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.taskmanager.EffectTaskManager;

class TelnetPerfomance.9
extends TelnetCommand {
    TelnetPerfomance.9(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "effectstats";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.EFFECT_TASK_MANAGER_COUNT; ++i) {
            stringBuilder.append("EffectTaskManager #").append(i + 1).append("\r\n");
            stringBuilder.append("=================================================\r\n");
            stringBuilder.append(EffectTaskManager.getInstance().getStats(i));
            stringBuilder.append("=================================================\r\n");
        }
        return stringBuilder.toString();
    }
}
