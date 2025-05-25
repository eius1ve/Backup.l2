/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.gameserver.model.AggroList$AggroInfo
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.oly.Competition
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 */
package achievements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;

public abstract class AchievementCondition {
    public static AchievementCondition makeCond(String string, String string2) {
        try {
            for (Class<?> clazz : AchievementCondition.class.getClasses()) {
                Constructor<?> constructor;
                Class<?> clazz2;
                AchievementConditionName achievementConditionName;
                if (!AchievementCondition.class.isAssignableFrom(clazz) || (achievementConditionName = (clazz2 = clazz).getAnnotation(AchievementConditionName.class)) == null || !string.equalsIgnoreCase(achievementConditionName.value()) || (constructor = clazz2.getConstructor(String.class)) == null) continue;
                return (AchievementCondition)constructor.newInstance(string2);
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("Can't make condition " + string + "(" + string2 + ")", exception);
        }
        return null;
    }

    public abstract boolean test(Player var1, Object ... var2);

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface AchievementConditionName {
        public String value();
    }

    @AchievementConditionName(value="self_min_online_time")
    public static class AchSelfMinOnlineTime
    extends AchievementCondition {
        private final long b;

        public AchSelfMinOnlineTime(String string) {
            this.b = Long.parseLong(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            long l = player.getOnlineBeginTime();
            long l2 = System.currentTimeMillis();
            if (l > 0L && l <= l2) {
                return (l2 - l) / 1000L >= this.b;
            }
            return false;
        }
    }

    @AchievementConditionName(value="self_quest_state_is")
    public static class AchSelfQuestStateIs
    extends AchievementCondition {
        private final int d;

        public AchSelfQuestStateIs(String string) {
            this.d = Quest.getStateId((String)string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null) continue;
                QuestState questState = null;
                if (object instanceof QuestState) {
                    questState = (QuestState)object;
                }
                if (object instanceof Quest) {
                    questState = player.getQuestState((Quest)object);
                }
                if (questState == null) continue;
                return questState.getState() == this.d;
            }
            return false;
        }
    }

    @AchievementConditionName(value="self_quest_id_in")
    public static class AchSelfQuestId
    extends AchievementCondition {
        private final Set<Integer> b = new HashSet<Integer>();

        public AchSelfQuestId(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
            while (stringTokenizer.hasMoreTokens()) {
                this.b.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null) continue;
                Quest quest = null;
                if (object instanceof Quest) {
                    quest = (Quest)object;
                }
                if (object instanceof QuestState) {
                    quest = ((QuestState)object).getQuest();
                }
                if (quest == null) continue;
                return this.b.contains(quest.getQuestIntId());
            }
            return false;
        }
    }

    @AchievementConditionName(value="target_npc_min_damage_to_me")
    public static class AchTargetAggroMinDamageToMe
    extends AchievementCondition {
        private final int f;

        public AchTargetAggroMinDamageToMe(String string) {
            this.f = Integer.parseInt(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(object instanceof NpcInstance)) continue;
                NpcInstance npcInstance = (NpcInstance)object;
                AggroList.AggroInfo aggroInfo = npcInstance.getAggroList().get((Creature)player);
                if (aggroInfo == null) {
                    return false;
                }
                return aggroInfo.damage >= this.f;
            }
            return false;
        }
    }

    @AchievementConditionName(value="target_npc_min_hate_to_me")
    public static class AchTargetAggroMinHateToMe
    extends AchievementCondition {
        private final int g;

        public AchTargetAggroMinHateToMe(String string) {
            this.g = Integer.parseInt(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(object instanceof NpcInstance)) continue;
                NpcInstance npcInstance = (NpcInstance)object;
                AggroList.AggroInfo aggroInfo = npcInstance.getAggroList().get((Creature)player);
                if (aggroInfo == null) {
                    return false;
                }
                return aggroInfo.hate >= this.g;
            }
            return false;
        }
    }

    @AchievementConditionName(value="self_target_max_lvl_diff")
    public static class AchSelfTargetMaxLvlDiff
    extends AchievementCondition {
        private final int e;

        public AchSelfTargetMaxLvlDiff(String string) {
            this.e = Integer.parseInt(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(object instanceof Creature)) continue;
                Creature creature = (Creature)object;
                return Math.abs(creature.getLevel() - player.getLevel()) <= this.e;
            }
            return false;
        }
    }

    @AchievementConditionName(value="self_is_hero")
    public static class AchSelfIsHero
    extends AchievementCondition {
        private final boolean c;

        public AchSelfIsHero(String string) {
            this.c = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return player.isHero() == this.c;
        }
    }

    @AchievementConditionName(value="self_is_noble")
    public static class AchSelfIsNoble
    extends AchievementCondition {
        private final boolean d;

        public AchSelfIsNoble(String string) {
            this.d = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return player.isNoble() == this.d;
        }
    }

    @AchievementConditionName(value="self_level_in_range")
    public static class AchSelfLevelInRange
    extends AchievementCondition {
        private final int b;
        private final int c;

        public AchSelfLevelInRange(String string) {
            int n = string.indexOf(45);
            this.b = Integer.parseInt(string.substring(0, n).trim());
            this.c = Integer.parseInt(string.substring(n + 1).trim());
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return player.getLevel() >= this.b && player.getLevel() < this.c;
        }
    }

    @AchievementConditionName(value="self_is_clan_leader")
    public static class AchSelfIsClanLeader
    extends AchievementCondition {
        private final boolean b;

        public AchSelfIsClanLeader(String string) {
            this.b = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return player.isClanLeader() == this.b;
        }
    }

    @AchievementConditionName(value="self_is_subclass_active")
    public static class AchSelfIsSubclassActive
    extends AchievementCondition {
        private final boolean e;

        public AchSelfIsSubclassActive(String string) {
            this.e = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return player.isSubClassActive() == this.e;
        }
    }

    @AchievementConditionName(value="is_target_player_class_id_in")
    public static class AchTargetPlayerIsActiveClass
    extends AchievementCondition {
        private final Set<Integer> c = new HashSet<Integer>();

        public AchTargetPlayerIsActiveClass(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
            while (stringTokenizer.hasMoreTokens()) {
                this.c.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(object instanceof Player)) continue;
                Player player2 = (Player)object;
                return this.c.contains(player2.getClassId());
            }
            return false;
        }
    }

    @AchievementConditionName(value="self_is_class_id_in")
    public static class AchSelfIsActiveClass
    extends AchievementCondition {
        private final Set<Integer> a = new HashSet<Integer>();

        public AchSelfIsActiveClass(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
            while (stringTokenizer.hasMoreTokens()) {
                this.a.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return this.a.contains(player.getClassId());
        }
    }

    @AchievementConditionName(value="is_oly_winner")
    public static class AchIsOlyWinner
    extends AchievementCondition {
        private final boolean a;

        public AchIsOlyWinner(String string) {
            this.a = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            boolean bl = false;
            for (Object object : objectArray) {
                if (!(object instanceof Competition)) continue;
                bl = true;
            }
            if (bl) {
                for (Object object : objectArray) {
                    if (!(object instanceof Boolean)) continue;
                    return this.a == (Boolean)object;
                }
            }
            return false;
        }
    }

    @AchievementConditionName(value="now_match_cron")
    public static class AchievementConditionIsNowMatchCron
    extends AchievementCondition {
        private final SchedulingPattern a;

        public AchievementConditionIsNowMatchCron(String string) {
            this.a = new SchedulingPattern(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            return this.a.match(System.currentTimeMillis());
        }
    }

    @AchievementConditionName(value="npc_id_in_list")
    public static class AchievementConditionNpcIdInList
    extends AchievementCondition {
        private Set<Integer> d = new HashSet<Integer>();

        public AchievementConditionNpcIdInList(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
            while (stringTokenizer.hasMoreTokens()) {
                this.d.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                NpcInstance npcInstance;
                if (object == null || !(object instanceof NpcInstance) || !this.d.contains((npcInstance = (NpcInstance)object).getNpcId())) continue;
                return true;
            }
            return false;
        }
    }

    @AchievementConditionName(value="is_karma_player")
    public static class AchievementConditionIsKarmaPlayer
    extends AchievementCondition {
        private final boolean g;

        public AchievementConditionIsKarmaPlayer(String string) {
            this.g = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(this.g ? object instanceof Player && ((Player)object).getKarma() > 0 : object instanceof Player && ((Player)object).getKarma() == 0)) continue;
                return true;
            }
            return false;
        }
    }

    @AchievementConditionName(value="is_raid_boss")
    public static class AchievementConditionHaveRaid
    extends AchievementCondition {
        private final boolean f;

        public AchievementConditionHaveRaid(String string) {
            this.f = Boolean.parseBoolean(string);
        }

        @Override
        public boolean test(Player player, Object ... objectArray) {
            for (Object object : objectArray) {
                if (object == null || !(this.f ? object instanceof RaidBossInstance : !(object instanceof RaidBossInstance))) continue;
                return true;
            }
            return false;
        }
    }
}
