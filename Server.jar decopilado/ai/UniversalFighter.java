/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.Config$RateBonusInfo
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.dao.AccountBonusDAO
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.base.CategoryData
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.StatsSet
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.ArrayUtils
 */
package ai;

import java.util.ArrayList;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.Fighter;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import quests._234_FatesWhisper;
import quests._235_MimirsElixir;
import services.GlobalServices;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UniversalFighter
extends Fighter {
    private static final long w = 40000L;
    private final int[] k;
    private final int aE;
    private final int[] l;
    private final boolean v;
    private final boolean w;
    private final boolean x;
    private final boolean y;
    private final boolean z;
    private final int aF;
    private final long x;
    private long y;
    private final boolean A;
    private final int[] m;
    private final int[] n;
    private final int[] o;
    private final int aG;
    private final int aH;
    private final int[] p;

    public UniversalFighter(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        this.v = statsSet.getBool((Object)"flagging_around_players_on_dead", false);
        this.y = statsSet.getBool((Object)"flagging_around_players_on_attack", false);
        this.z = statsSet.getBool((Object)"flagging_solo_player_on_attack", false);
        this.w = statsSet.getBool((Object)"give_nobles_on_main_class", false);
        this.x = statsSet.getBool((Object)"give_nobles_on_sub_class", false);
        this.aF = statsSet.getInteger((Object)"give_to_party_premium_account_id", 0);
        this.x = statsSet.getLong((Object)"give_to_party_custom_hero_time_hours", 0L);
        this.k = statsSet.getIntegerArray((Object)"give_to_party_items_reward", ArrayUtils.EMPTY_INT_ARRAY);
        this.aE = statsSet.getInteger((Object)"give_to_party_items_reward_level_penalty", 9);
        this.A = statsSet.getBool((Object)"give_to_party_subclass_quests", false);
        this.l = statsSet.getIntegerArray((Object)"buff_party_reward", ArrayUtils.EMPTY_INT_ARRAY);
        this.m = statsSet.getIntegerArray((Object)"give_pvp_point", ArrayUtils.EMPTY_INT_ARRAY);
        this.n = statsSet.getIntegerArray((Object)"give_pk_point", ArrayUtils.EMPTY_INT_ARRAY);
        this.o = statsSet.getIntegerArray((Object)"distribution_items_reward", ArrayUtils.EMPTY_INT_ARRAY);
        this.aG = statsSet.getInteger((Object)"distribution_items_reward_level_penalty", 9);
        this.aH = statsSet.getInteger((Object)"distribution_items_reward_min_damage", 1);
        this.p = statsSet.getIntegerArray((Object)"distribution_items_reward_chance", ArrayUtils.EMPTY_INT_ARRAY);
    }

    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        if (!creature.isPlayable()) {
            return;
        }
        Player player = creature.getPlayer();
        if (this.v) {
            this.f();
        }
        if (this.w || this.x) {
            this.a(player);
        }
        if (this.aF > 0) {
            this.b(player);
        }
        if (this.x > 0L) {
            this.c(player);
        }
        if (!ArrayUtils.isEmpty((int[])this.k)) {
            this.f(player);
        }
        if (this.A) {
            this.g(player);
        }
        if (!ArrayUtils.isEmpty((int[])this.l)) {
            this.h(player);
        }
        if (!ArrayUtils.isEmpty((int[])this.m)) {
            this.d(player);
        }
        if (!ArrayUtils.isEmpty((int[])this.n)) {
            this.e(player);
        }
        if (!ArrayUtils.isEmpty((int[])this.o)) {
            this.g();
        }
    }

    protected void onEvtAttacked(Creature creature, int n) {
        super.onEvtAttacked(creature, n);
        if (creature.isPlayer()) {
            if (this.y) {
                this.e();
            }
            if (this.z) {
                this.c(creature);
            }
        }
    }

    private void e() {
        if (System.currentTimeMillis() - 40000L >= this.y) {
            NpcInstance npcInstance = this.getActor();
            List list = World.getAroundPlayers((GameObject)npcInstance, (int)2000, (int)300);
            list.forEach(player -> player.startPvPFlag((Creature)npcInstance));
            this.y = System.currentTimeMillis();
        }
    }

    private void c(Creature creature) {
        if (System.currentTimeMillis() - 40000L >= this.y) {
            NpcInstance npcInstance = this.getActor();
            if (creature != null) {
                creature.startPvPFlag((Creature)npcInstance);
            }
            this.y = System.currentTimeMillis();
        }
    }

    private void f() {
        NpcInstance npcInstance = this.getActor();
        List list = World.getAroundPlayers((GameObject)npcInstance, (int)2000, (int)300);
        list.forEach(player -> player.startPvPFlag((Creature)npcInstance));
    }

    private void a(Player player) {
        if (player.isDead() || player.getParty() == null) {
            Log.add((String)("UniversalFighter Player " + player.getName() + " is dead or not in a party, skipping handleGiveNobles."), (String)"AI");
            return;
        }
        Party party = player.getParty();
        for (Player player2 : party) {
            if (player2 == null) {
                Log.add((String)"UniversalFighter Party player is null, skipping.", (String)"AI");
                continue;
            }
            if (player2.isDead()) {
                Log.add((String)("UniversalFighter Party player " + player2.getName() + " is dead, skipping."), (String)"AI");
                continue;
            }
            if (player2.isNoble()) {
                Log.add((String)("UniversalFighter Party player " + player2.getName() + " is already Nobles, skipping."), (String)"AI");
                continue;
            }
            if (this.getActor().getDistance3D((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) {
                Log.add((String)("UniversalFighter Party player " + player2.getName() + " is out of range (" + this.getActor().getDistance3D((GameObject)player2) + " > " + Config.ALT_PARTY_DISTRIBUTION_RANGE + "), skipping."), (String)"AI");
                continue;
            }
            if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player2)) {
                Log.add((String)("UniversalFighter Party player " + player2.getName() + " is not in the fourth class group, skipping. Player current classId " + player2.getBaseClassId()), (String)"AI");
                continue;
            }
            if (player2.getLevel() < 76) {
                Log.add((String)("UniversalFighter Party player " + player2.getName() + " has insufficient level (" + player2.getLevel() + " < 76), skipping."), (String)"AI");
                continue;
            }
            if (this.w && !player2.isNoble() && !player2.isSubClassActive()) {
                NoblesController.getInstance().addNoble(player2.getPlayer());
                player2.setNoble(true);
                player2.updatePledgeClass();
                player2.updateNobleSkills();
                player2.sendSkillList();
                player2.broadcastUserInfo(false, new UserInfoType[0]);
                Log.add((String)("UniversalFighter Nobles on Main added " + player2.getName()), (String)"AI");
            }
            if (!this.x || player2.isNoble() || !player2.isSubClassActive()) continue;
            NoblesController.getInstance().addNoble(player2.getPlayer());
            player2.setNoble(true);
            player2.updatePledgeClass();
            player2.updateNobleSkills();
            player2.sendSkillList();
            player2.broadcastUserInfo(false, new UserInfoType[0]);
            Log.add((String)("UniversalFighter Nobles on Sub added " + player2.getName()), (String)"AI");
        }
    }

    private void b(Player player) {
        if (player.isDead() || player.getParty() == null) {
            return;
        }
        Party party = player.getParty();
        for (Player player2 : party) {
            if (player2 == null || player2.isDead() || player2.hasBonus() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
            Config.RateBonusInfo rateBonusInfo = null;
            for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
                if (rateBonusInfo2.id != this.aF) continue;
                rateBonusInfo = rateBonusInfo2;
                break;
            }
            if (rateBonusInfo == null) continue;
            AccountBonusDAO.getInstance().store(player2.getAccountName(), rateBonusInfo.makeBonus());
            player2.stopBonusTask();
            player2.startBonusTask();
            if (player2.getParty() != null) {
                player2.getParty().recalculatePartyData();
            }
            player2.broadcastUserInfo(true, new UserInfoType[0]);
            Log.add((String)("UniversalMystic PA Bonus added " + player2.getName() + "|" + player2.getObjectId() + "|rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "|"), (String)"AI");
        }
    }

    private void c(Player player) {
        if (player.isDead() || player.getParty() == null) {
            return;
        }
        Party party = player.getParty();
        for (Player player2 : party) {
            if (player2 == null || player2.isDead() || player2.isHero() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
            GlobalServices.makeCustomHero(player2, this.x * 60L * 60L);
            Log.add((String)("UniversalMystic Custom Hero added " + player2.getName() + " | " + this.x + " hours"), (String)"AI");
        }
    }

    private void d(Player player) {
        if (player.isDead()) {
            return;
        }
        Party party = player.getParty();
        if (player.getParty() != null) {
            for (Player player2 : party) {
                if (player2 == null || player2.isDead() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                if (this.m[1] > 0) {
                    if (!Rnd.chance((int)this.m[1])) continue;
                    player2.setPvpKills(player2.getPvpKills() + this.m[0]);
                    player2.sendUserInfo(false);
                    continue;
                }
                player2.setPvpKills(player2.getPvpKills() + this.m[0]);
                player2.sendUserInfo(false);
            }
        } else if (this.m[1] > 0) {
            if (Rnd.chance((int)this.m[1])) {
                player.setPvpKills(player.getPvpKills() + this.m[0]);
                player.sendUserInfo(false);
            }
        } else {
            player.setPvpKills(player.getPvpKills() + this.m[0]);
            player.sendUserInfo(false);
        }
    }

    private void e(Player player) {
        if (player.isDead()) {
            return;
        }
        Party party = player.getParty();
        if (player.getParty() != null) {
            for (Player player2 : party) {
                if (player2 == null || player2.isDead() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                if (this.n[1] > 0) {
                    if (!Rnd.chance((int)this.n[1])) continue;
                    player2.setPkKills(player2.getPkKills() + this.n[0]);
                    player2.sendUserInfo(false);
                    continue;
                }
                player2.setPkKills(player2.getPkKills() + this.n[0]);
                player2.sendUserInfo(false);
            }
        } else if (this.n[1] > 0) {
            if (Rnd.chance((int)this.n[1])) {
                player.setPkKills(player.getPkKills() + this.n[0]);
                player.sendUserInfo(false);
            }
        } else {
            player.setPkKills(player.getPkKills() + this.n[0]);
            player.sendUserInfo(false);
        }
    }

    private void f(Player player) {
        if (player.isDead() || player.getParty() == null) {
            return;
        }
        Party party = player.getParty();
        for (Player player2 : party) {
            if (player2 == null || player2.isDead() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE || this.aE > 0 && Math.max(player2.getLevel() - this.getActor().getLevel() - this.aE, 0) > this.aE) continue;
            for (int i = 0; i < this.k.length; i += 2) {
                int n = this.k[i];
                long l = this.k[i + 1];
                ItemFunctions.addItem((Playable)player2, (int)n, (long)l, (boolean)true);
            }
        }
    }

    private void g(Player player) {
        if (player.isDead() || player.getParty() == null) {
            return;
        }
        Party party = player.getParty();
        Quest quest = QuestManager.getQuest(_234_FatesWhisper.class);
        Quest quest2 = QuestManager.getQuest(_235_MimirsElixir.class);
        for (Player player2 : party) {
            if (player2 == null || player2.isDead() || player2.getLevel() < 76 || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
            if (player2.getQuestState(_234_FatesWhisper.class) == null || !player2.getQuestState(_234_FatesWhisper.class).isCompleted()) {
                quest.newQuestState(player2, 3);
            }
            if (player2.getQuestState(_235_MimirsElixir.class) != null && player2.getQuestState(_235_MimirsElixir.class).isCompleted()) continue;
            quest2.newQuestState(player2, 3);
        }
    }

    private void h(Player player) {
        NpcInstance npcInstance = this.getActor();
        if (player.isDead() || player.getParty() == null || npcInstance == null) {
            return;
        }
        Party party = player.getParty();
        for (Player player2 : party) {
            if (player2 == null || player2.isDead() || this.getActor().getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
            for (int i = 0; i < this.l.length; i += 3) {
                int n = this.l[i];
                int n2 = this.l[i + 1];
                double d = this.l[i + 2];
                Skill skill = SkillTable.getInstance().getInfo(n, n2);
                if (skill == null) continue;
                skill.getEffects((Creature)npcInstance, (Creature)player2, false, false, 0L, d, false);
            }
        }
    }

    private void g() {
        ArrayList arrayList = new ArrayList(this.getActor().getAggroList().getCharMap().keySet());
        for (Creature creature : arrayList) {
            if (creature == null || !creature.isPlayer() || creature.isDead() || !(this.getActor().getDistance((GameObject)creature.getPlayer()) < (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) || this.getActor().getAggroList().get((Creature)creature.getPlayer()) == null && this.getActor().getAggroList().get((Creature)creature.getPlayer()).damage < this.aH || this.aG <= 0 || Math.max(creature.getLevel() - this.getActor().getLevel() - this.aG, 0) >= this.aG) continue;
            this.i(creature.getPlayer());
        }
    }

    private void i(Player player) {
        if (player == null || player.isDead() || this.getActor().getDistance((GameObject)player) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE || this.aG > 0 && Math.max(player.getLevel() - this.getActor().getLevel() - this.aG, 0) > this.aG) {
            return;
        }
        int n = 100;
        int n2 = this.p.length > 0 ? this.p[this.p.length - 1] : n;
        for (int i = 0; i < this.o.length; i += 2) {
            int n3;
            int n4 = this.o[i];
            long l = this.o[i + 1];
            int n5 = n3 = i / 2 < this.p.length ? this.p[i / 2] : n2;
            if (!Rnd.chance((int)n3)) continue;
            ItemFunctions.addItem((Playable)player, (int)n4, (long)l, (boolean)true);
        }
    }
}
