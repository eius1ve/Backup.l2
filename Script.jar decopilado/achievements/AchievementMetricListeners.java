/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.listener.CharListener
 *  l2.gameserver.listener.PlayerListener
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.OnKillListener
 *  l2.gameserver.listener.actor.player.OnGainExpSpListener
 *  l2.gameserver.listener.actor.player.OnOlyCompetitionListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.actor.player.OnPvpPkKillListener
 *  l2.gameserver.listener.actor.player.OnQuestStateChangeListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.entity.oly.Competition
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.utils.Location
 */
package achievements;

import achievements.Achievement;
import achievements.AchievementInfo;
import achievements.AchievementMetricType;
import achievements.Achievements;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import l2.commons.listener.Listener;
import l2.gameserver.listener.CharListener;
import l2.gameserver.listener.PlayerListener;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.listener.actor.player.OnGainExpSpListener;
import l2.gameserver.listener.actor.player.OnOlyCompetitionListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2.gameserver.listener.actor.player.OnQuestStateChangeListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.utils.Location;

public class AchievementMetricListeners {
    private static final AchievementMetricListeners a = new AchievementMetricListeners();
    private List<? extends Listener<?>> e = Arrays.asList(new AchievementOnPlayerEnter(), new AchievementOnKill(), new AchievementOnDeath(), new AchievementOnPvPPkKill(), new AchievementOnGainExpSp(), new AchievementOnOlyCompetitionCompleted(), new AchievementOnQuestStateChange());

    private AchievementMetricListeners() {
    }

    public static AchievementMetricListeners getInstance() {
        return a;
    }

    public void metricEvent(Player player, AchievementMetricType achievementMetricType, Object ... objectArray) {
        if (!Achievements.getInstance().isEnabled()) {
            return;
        }
        List<AchievementInfo> list = Achievements.getInstance().getAchievementInfosByMetric(achievementMetricType);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (AchievementInfo achievementInfo : list) {
            Achievement achievement = new Achievement(achievementInfo, player);
            if (achievement.isCompleted()) continue;
            achievement.onMetricEvent(objectArray);
        }
    }

    public void init() {
        for (Listener<?> listener : this.e) {
            if (listener instanceof PlayerListener) {
                PlayerListenerList.addGlobal((Listener)((PlayerListener)listener));
                continue;
            }
            if (listener instanceof CharListener) {
                CharListenerList.addGlobal((Listener)((CharListener)listener));
                continue;
            }
            throw new IllegalStateException("Unknown listener " + listener.getClass());
        }
    }

    public void done() {
        for (Listener<?> listener : this.e) {
            if (listener instanceof PlayerListener) {
                PlayerListenerList.removeGlobal((Listener)((PlayerListener)listener));
                continue;
            }
            if (!(listener instanceof CharListener)) continue;
            CharListenerList.removeGlobal((Listener)((CharListener)listener));
        }
    }

    public static class AchievementOnPlayerEnter
    implements OnPlayerEnterListener {
        public void onPlayerEnter(Player player) {
            AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.LOGIN, player);
        }
    }

    public static class AchievementOnKill
    implements OnKillListener {
        public void onKill(Creature creature, Creature creature2) {
            if (creature == null || !creature.isPlayer() || creature2 == null) {
                return;
            }
            if (creature2.isNpc()) {
                AchievementMetricListeners.getInstance().metricEvent(creature.getPlayer(), AchievementMetricType.NPC_KILL, creature2);
                if (creature2 instanceof RaidBossInstance) {
                    RaidBossInstance raidBossInstance = (RaidBossInstance)creature2;
                    Location location = raidBossInstance.getLoc();
                    ArrayList arrayList = new ArrayList(raidBossInstance.getAggroList().getCharMap().keySet());
                    LinkedHashSet<Player> linkedHashSet = new LinkedHashSet<Player>();
                    for (Creature creature3 : arrayList) {
                        if (creature3 == null || location.distance3D(creature3.getLoc()) > 1500.0 || !(creature3 instanceof Player)) continue;
                        linkedHashSet.add((Player)creature3);
                    }
                    for (Player player : linkedHashSet) {
                        AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.RAID_PARTICIPATION, creature2);
                    }
                }
            }
        }

        public boolean ignorePetOrSummon() {
            return true;
        }
    }

    public static class AchievementOnDeath
    implements OnDeathListener {
        public void onDeath(Creature creature, Creature creature2) {
            if (creature.isPlayer()) {
                AchievementMetricListeners.getInstance().metricEvent(creature.getPlayer(), AchievementMetricType.DEATH, creature2);
            }
        }
    }

    public static class AchievementOnPvPPkKill
    implements OnPvpPkKillListener {
        public void onPvpPkKill(Player player, Player player2, boolean bl) {
            if (!bl) {
                AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.PVP_KILL, player.getPvpKills(), player2);
            }
        }
    }

    public static class AchievementOnGainExpSp
    implements OnGainExpSpListener {
        public void onGainExpSp(Player player, long l, long l2) {
            AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.LEVEL, player.getLevel());
        }
    }

    public static class AchievementOnOlyCompetitionCompleted
    implements OnOlyCompetitionListener {
        public void onOlyCompetitionCompleted(Player player, Competition competition, boolean bl) {
            AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.OLYMPIAD, competition, bl);
        }
    }

    public static class AchievementOnQuestStateChange
    implements OnQuestStateChangeListener {
        public void onQuestStateChange(Player player, QuestState questState) {
            AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.QUEST_STATE, questState);
        }
    }
}
