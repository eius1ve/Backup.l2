/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 */
package achievements;

import achievements.AchievementCounter;
import achievements.AchievementInfo;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

private class Achievement.EventMetric
extends RunnableImpl {
    private final Object[] a;
    private final HardReference<Player> b;

    private Achievement.EventMetric(Player player, Object[] objectArray) {
        this.a = objectArray;
        this.b = player.getRef();
    }

    public void runImpl() throws Exception {
        Player player = (Player)this.b.get();
        if (player == null) {
            return;
        }
        AchievementInfo.AchievementInfoLevel achievementInfoLevel = Achievement.this.getNextLevel();
        if (achievementInfoLevel != null && achievementInfoLevel.testConds(player, this.a)) {
            AchievementCounter achievementCounter = Achievement.this.getCounter();
            int n = achievementCounter.incrementAndGetValue();
            AchievementInfo.AchievementInfoLevel achievementInfoLevel2 = null;
            if (n >= achievementInfoLevel.getValue()) {
                achievementInfoLevel2 = achievementInfoLevel;
                Achievement.this.a(achievementInfoLevel2.getLevel() + 1);
                if (achievementInfoLevel2.isResetMetric()) {
                    achievementCounter.setVal(0);
                }
                String string = new CustomMessage("achievements.achievementS1Unlocked", player, new Object[]{Achievement.this.a.getName(player)}).toString();
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false, 1, -1, true));
                player.sendMessage(new CustomMessage("achievements.achievedS1LevelS2", player, new Object[]{Achievement.this.a.getName(player), achievementInfoLevel.getLevel()}));
            }
            achievementCounter.store();
        }
    }
}
