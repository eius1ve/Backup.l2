/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.taskmanager.AiTaskManager;

public class AIStatsCommand
implements ITelegramCommandHandler {
    private final String[] G = new String[]{"/aistats"};

    @Override
    public void handle(String string, String string2) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.AI_TASK_MANAGER_COUNT; ++i) {
            stringBuilder.append("AiTaskManager #").append(i + 1).append("\n");
            stringBuilder.append("=================================================\n");
            stringBuilder.append(AiTaskManager.getInstance().getStats(i));
            stringBuilder.append("=================================================\n");
        }
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.G;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
