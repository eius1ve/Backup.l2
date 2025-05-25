/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
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
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Achievement {
    public static final int BASE_ACHIEVEMENT_LEVEL = 1;
    private static final String d = "ex_achievement_%d_%s";
    private final AchievementInfo a;
    private final HardReference<Player> a;
    private final AchievementCounter a;
    private long a;

    public Achievement(AchievementInfo achievementInfo, Player player) {
        this.a = achievementInfo;
        this.a = player.getRef();
        this.a = achievementInfo.getMetricType().getCounter(player, achievementInfo);
        this.a = Long.parseLong(this.a("expire_time", String.valueOf(this.a.nextResetTimeMills())));
        this.checkExpire();
    }

    private Player getPlayer() {
        return (Player)this.a.get();
    }

    public AchievementInfo getAchInfo() {
        return this.a;
    }

    private int a() {
        return this.a("next_lvl", 1);
    }

    private void a(int n) {
        this.a("next_lvl", n);
    }

    public void checkExpire() {
        long l = System.currentTimeMillis();
        if (this.a >= 0L) {
            this.a("expire_time", String.valueOf(this.a));
        }
        if (this.a < 0L || this.a > l) {
            return;
        }
        this.a.setVal(0);
        this.a.store();
        this.a(1);
        for (AchievementInfo.AchievementInfoLevel achievementInfoLevel : this.a.getLevels()) {
            if (!this.isLevelRewarded(achievementInfoLevel)) continue;
            this.setLevelRewarded(achievementInfoLevel, false);
        }
        long l2 = this.a.nextResetTimeMills();
        this.a("expire_time", String.valueOf(l2));
    }

    private String a(String string, String string2) {
        Player player = this.getPlayer();
        if (player == null) {
            return string2;
        }
        String string3 = player.getVar(String.format(d, this.a.getId(), string));
        return string3 != null ? string3 : string2;
    }

    private int a(String string, int n) {
        return Integer.parseInt(this.a(string, String.valueOf(n)));
    }

    private void a(String string, String string2) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        player.setVar(String.format(d, this.a.getId(), string), string2, -1L);
    }

    private void a(String string, int n) {
        this.a(string, String.valueOf(n));
    }

    public AchievementInfo.AchievementInfoLevel getLevel() {
        return this.a.getLevel(this.a() - 1);
    }

    public boolean isRewardableLevel(AchievementInfo.AchievementInfoLevel achievementInfoLevel) {
        if (achievementInfoLevel == null || achievementInfoLevel.getAchievementInfo() != this.getAchInfo() || achievementInfoLevel.getLevel() >= this.a() || achievementInfoLevel.getLevel() < 1) {
            return false;
        }
        return !this.isLevelRewarded(achievementInfoLevel);
    }

    public boolean isLevelRewarded(int n) {
        return this.a(String.format("lvl_%d_rewarded", n), 0) != 0;
    }

    public boolean isLevelRewarded(AchievementInfo.AchievementInfoLevel achievementInfoLevel) {
        return this.isLevelRewarded(achievementInfoLevel.getLevel());
    }

    public void setLevelRewarded(int n, boolean bl) {
        this.a(String.format("lvl_%d_rewarded", n), bl ? 1 : 0);
    }

    public void setLevelRewarded(AchievementInfo.AchievementInfoLevel achievementInfoLevel, boolean bl) {
        this.setLevelRewarded(achievementInfoLevel.getLevel(), bl);
    }

    public AchievementInfo.AchievementInfoLevel getNextLevel() {
        return this.a.getLevel(this.a());
    }

    public boolean isCompleted() {
        int n = this.a();
        return n > this.a.getMaxLevel();
    }

    public AchievementCounter getCounter() {
        return this.a;
    }

    public void onMetricEvent(Object ... objectArray) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (!this.a.testConds(this.getPlayer(), objectArray)) {
            return;
        }
        if (this.a.getMetricNotifyDelay() > 0L) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new EventMetric(player, objectArray)), this.a.getMetricNotifyDelay() * 1000L);
        } else {
            ThreadPoolManager.getInstance().execute((Runnable)((Object)new EventMetric(player, objectArray)));
        }
    }

    private class EventMetric
    extends RunnableImpl {
        private final Object[] a;
        private final HardReference<Player> b;

        private EventMetric(Player player, Object[] objectArray) {
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
}
