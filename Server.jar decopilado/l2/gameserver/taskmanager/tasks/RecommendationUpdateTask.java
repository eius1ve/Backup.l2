/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager.tasks;

import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExVoteSystemInfo;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.taskmanager.Task;
import l2.gameserver.taskmanager.TaskManager;
import l2.gameserver.taskmanager.TaskTypes;

public class RecommendationUpdateTask
extends Task {
    private static final String gh = "sp_recommendations";

    @Override
    public void init() {
        TaskManager.addUniqueTask(gh, TaskTypes.TYPE_GLOBAL_TASK, "1", String.format("%02d:%02d:00", Config.REC_FLUSH_HOUR, Config.REC_FLUSH_MINUTE), "");
    }

    @Override
    public String getName() {
        return gh;
    }

    @Override
    public void onTimeElapsed(TaskManager.ExecutedTask executedTask) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.updateRecommends();
            player.sendPacket((IStaticPacket)new ExVoteSystemInfo(player));
            player.broadcastUserInfo(true, UserInfoType.SOCIAL);
        }
    }
}
