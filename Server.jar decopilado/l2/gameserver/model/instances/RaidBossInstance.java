/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.CollectionUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oneDayReward.requirement.KillRaidRequirement;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;

public class RaidBossInstance
extends MonsterInstance {
    private static final long ct = 1L;
    private ScheduledFuture<?> aa;
    private static final int nn = 5000;

    public RaidBossInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    public boolean isRaid() {
        return true;
    }

    protected int getMinionUnspawnInterval() {
        return 5000;
    }

    @Override
    public void notifyMinionDied(MinionInstance minionInstance) {
        this.aa = ThreadPoolManager.getInstance().schedule(new MaintainKilledMinion(minionInstance), minionInstance.getParameter("respawn_time", 360000L));
        super.notifyMinionDied(minionInstance);
    }

    @Override
    protected void onDeath(Creature creature) {
        int n;
        if (this.aa != null) {
            this.aa.cancel(false);
            this.aa = null;
        }
        if ((n = this.getTemplate().rewardRp) > 0) {
            this.p(n);
        }
        if (this instanceof ReflectionBossInstance) {
            super.onDeath(creature);
            return;
        }
        if (creature.isPlayable()) {
            Quest quest;
            int n2;
            Object object;
            Player player = creature.getPlayer();
            if (player.isInParty()) {
                object = player.getParty().getPartyMembers().iterator();
                while (object.hasNext()) {
                    Player player2 = object.next();
                    if (!player2.isNoble()) continue;
                    HeroController.getInstance().addHeroDiary(player2.getObjectId(), 1, this.getNpcId());
                }
                player.getParty().broadCast(SystemMsg.CONGRATULATIONS_RAID_WAS_SUCCESSFUL);
            } else {
                if (player.isNoble()) {
                    HeroController.getInstance().addHeroDiary(player.getObjectId(), 1, this.getNpcId());
                }
                player.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS_RAID_WAS_SUCCESSFUL);
            }
            if (Config.ALT_RAID_DEATH_ANNOUNCE_IDS.contains(this.getNpcId())) {
                object = player.getClan();
                if (object == null) {
                    Announcements.getInstance().announceByCustomMessage("l2.gameserver.instancemanager.RaidBossSpawnManager.RaidBossKillAnnounce", new String[]{player.getName(), this.getName()});
                } else {
                    Announcements.getInstance().announceByCustomMessage("l2.gameserver.instancemanager.RaidBossSpawnManager.RaidBossKillWithClanAnnounce", new String[]{player.getName(), this.getName(), ((Clan)object).getName()});
                }
            }
            if ((object = player.getClan()) != null && Config.EVENT_CLAN_RAID_BOSS_POINTS.containsKey(this.getNpcId())) {
                ((Clan)object).setCustomPoints(((Clan)object).getCustomPoints() + Config.EVENT_CLAN_RAID_BOSS_POINTS.get(this.getNpcId()));
                if (Config.SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE) {
                    Announcements.getInstance().announceByCustomMessage("scripts.services.CustomClanPointsRaidBoss", new String[]{String.valueOf(((Clan)object).getName()), String.valueOf(this.getName()), String.valueOf(Config.EVENT_CLAN_RAID_BOSS_POINTS.get(this.getNpcId()))});
                }
            }
            if ((n2 = this.getTemplate().rewardCRP) > 0 && (object = player.getClan()) != null) {
                ((Clan)object).incReputation(n2, true, "raid_drop");
                ((Clan)object).broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.YOUR_CLAN_HAS_ADDED_S1_POINTS_TO_ITS_CLAN_REPUTATION_SCORE).addNumber(n2)});
            }
            if ((quest = QuestManager.getQuest(508)) != null) {
                String string = quest.getName();
                if (player.getClan() != null && player.getClan().getLeader().isOnline() && player.getClan().getLeader().getPlayer().getQuestState(string) != null) {
                    QuestState questState = player.getClan().getLeader().getPlayer().getQuestState(string);
                    questState.getQuest().onKill(this, questState);
                }
            }
        }
        if (this.getMinionList().hasAliveMinions()) {
            ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    if (RaidBossInstance.this.isDead()) {
                        RaidBossInstance.this.getMinionList().unspawnMinions();
                    }
                }
            }, this.getMinionUnspawnInterval());
        }
        super.onDeath(creature);
    }

    private void p(int n) {
        Map<PlayerGroup, Long> map = this.a();
        for (Map.Entry<PlayerGroup, Long> entry : map.entrySet()) {
            PlayerGroup playerGroup = entry.getKey();
            Set<Player> set = CollectionUtils.toSet(playerGroup);
            int n2 = Math.round((long)n * entry.getValue() / (long)(this.getMaxHp() * set.size()));
            for (Player player : set) {
                int n3;
                if (playerGroup != player && !player.isInRangeZ(this, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) || (n3 = (int)Math.round((double)n2 * Experience.penaltyModifier(this.calculateLevelDiffForDrop(player.getLevel()), 9.0))) == 0) continue;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_RAID_POINTS).addNumber(n3));
                if (player.getLevel() - this.getLevel() <= 6) {
                    OneDayRewardHolder.getInstance().fireRequirements(player, this, KillRaidRequirement.class);
                }
                RaidBossSpawnManager.getInstance().addPoints(player.getObjectId(), this.getNpcId(), n3);
                player.addRaidBossPoints(n3);
            }
        }
        RaidBossSpawnManager.getInstance().updatePointsDb();
        RaidBossSpawnManager.getInstance().calculateRanking();
    }

    private Map<PlayerGroup, Long> a() {
        HashMap<PlayerGroup, Long> hashMap = new HashMap<PlayerGroup, Long>();
        for (AggroList.HateInfo hateInfo : this.getAggroList().getPlayableMap().values()) {
            PlayerGroup playerGroup = hateInfo.attacker.getPlayer().getPlayerGroup();
            hashMap.put(playerGroup, hashMap.getOrDefault(playerGroup, 0L) + (long)hateInfo.damage);
        }
        return hashMap;
    }

    @Override
    protected void onDecay() {
        super.onDecay();
        RaidBossSpawnManager.getInstance().onBossDespawned(this);
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.addSkill(SkillTable.getInstance().getInfo(4045, 1));
        RaidBossSpawnManager.getInstance().onBossSpawned(this);
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public boolean isLethalImmune() {
        return true;
    }

    @Override
    public boolean hasRandomWalk() {
        return false;
    }

    @Override
    public boolean canChampion() {
        return false;
    }

    private class MaintainKilledMinion
    extends RunnableImpl {
        private final MinionInstance a;

        public MaintainKilledMinion(MinionInstance minionInstance) {
            this.a = minionInstance;
        }

        @Override
        public void runImpl() throws Exception {
            if (!RaidBossInstance.this.isDead()) {
                this.a.refreshID();
                RaidBossInstance.this.spawnMinion(this.a);
            }
        }
    }
}
