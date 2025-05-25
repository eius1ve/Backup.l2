/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model.entity.oly;

import gnu.trove.TIntIntHashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.model.entity.oly.Participant;
import l2.gameserver.model.entity.oly.Stadium;
import l2.gameserver.model.entity.oneDayReward.requirement.BattleInOlympiadRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.WinInOlympiadRequirement;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.OlympiadBufferInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.ExOlympiadMode;
import l2.gameserver.network.l2.s2c.ExOlympiadSpelledInfo;
import l2.gameserver.network.l2.s2c.ExOlympiadUserInfo;
import l2.gameserver.network.l2.s2c.ExReceiveOlympiadResult;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Competition {
    private final Stadium b;
    public Participant[] _participants;
    private final CompetitionType a;
    private long ch;
    private static final double D = 0.8;
    private volatile CompetitionState a;
    private ScheduledFuture<?> V;

    public Competition(CompetitionType competitionType, Stadium stadium) {
        this.a = competitionType;
        this.b = stadium;
        this.a = null;
        this.ch = 0L;
    }

    public CompetitionState getState() {
        return this.a;
    }

    public void setState(CompetitionState competitionState) {
        if (this.a == CompetitionState.STAND_BY && competitionState == CompetitionState.PLAYING) {
            this.ch = System.currentTimeMillis();
        }
        this.a = competitionState;
    }

    public CompetitionType getType() {
        return this.a;
    }

    public Stadium getStadium() {
        return this.b;
    }

    public void setPlayers(Participant[] participantArray) {
        for (Participant participant : this._participants = participantArray) {
            for (Player player : participant.getPlayers()) {
                player.setOlyParticipant(participant);
            }
        }
    }

    public void start() {
        for (Participant participant : this._participants) {
            participant.OnStart();
        }
    }

    private void bB() {
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                try {
                    if (player.getClan() != null) {
                        player.getClan().enableSkills(player);
                    }
                    for (int i = 0; i < Config.OLY_RESTRICTED_SKILL_IDS.length; ++i) {
                        Skill skill;
                        int n = Config.OLY_RESTRICTED_SKILL_IDS[i];
                        if (!player.isUnActiveSkill(n) || (skill = player.getKnownSkill(n)) == null) continue;
                        player.removeUnActiveSkill(skill);
                    }
                    Collection<TimeStamp> collection = player.getSkillReuses();
                    if (Config.OLYMPIAD_REMOVE_SKILL_REUSE_FROM_STADIUM) {
                        for (TimeStamp timeStamp : collection) {
                            Skill skill = SkillTable.getInstance().getInfo(timeStamp.getId(), timeStamp.getLevel());
                            player.enableSkill(skill);
                        }
                    }
                    if (player.isHero()) {
                        HeroController.addSkills(player);
                    }
                    player.sendPacket(new ExOlympiadMode(0), new SkillCoolTime(player));
                    player.sendSkillList();
                    player.updateStats();
                    player.updateEffectIcons();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void bC() {
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                try {
                    void var11_18;
                    Object object;
                    Object object2;
                    boolean bl = false;
                    for (Effect summon : player.getEffectList().getAllEffects()) {
                        if (summon == null || summon.getSkill() == null || summon.getSkill().isToggle() || summon.getEffectType() == EffectType.Cubic && player.getSkillLevel(summon.getSkill().getId()) > 0) continue;
                        if (Config.OLY_REMOVE_FORCE_BUFFS) {
                            player.setIncreasedForce(0);
                        }
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(summon.getSkill().getId(), summon.getSkill().getDisplayLevel()));
                        summon.exit();
                        bl = true;
                    }
                    Collection<TimeStamp> collection = player.getSkillReuses();
                    if (Config.OLYMPIAD_REMOVE_SKILL_REUSE_ON_STADIUM) {
                        Iterator iterator = collection.iterator();
                        while (iterator.hasNext()) {
                            object2 = (TimeStamp)iterator.next();
                            object = SkillTable.getInstance().getInfo(((TimeStamp)object2).getId(), ((TimeStamp)object2).getLevel());
                            player.enableSkill((Skill)object);
                            bl = true;
                        }
                    }
                    if (bl) {
                        player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                        player.updateStats();
                        player.updateEffectIcons();
                    }
                    if (Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ARMOR >= 0) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.entity.OlympiadGame.Competition.EnchantArmorLevelLimited", player, Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ARMOR));
                    }
                    if (Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_PHYS >= 0) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.entity.OlympiadGame.Competition.EnchantWeaponPhysLevelLimited", player, Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_PHYS));
                    }
                    if (Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_MAGE >= 0) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.entity.OlympiadGame.Competition.EnchantWeaponMageLevelLimited", player, Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_MAGE));
                    }
                    if (Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ACCESSORY >= 0) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.model.entity.OlympiadGame.Competition.EnchantAccessoryLevelLimited", player, Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ACCESSORY));
                    }
                    if (player.getClan() != null) {
                        player.getClan().disableSkills(player);
                    }
                    boolean bl2 = false;
                    while (var11_18 < Config.OLY_RESTRICTED_SKILL_IDS.length) {
                        int n = Config.OLY_RESTRICTED_SKILL_IDS[var11_18];
                        object = player.getKnownSkill(n);
                        if (object != null) {
                            player.addUnActiveSkill((Skill)object);
                        }
                        ++var11_18;
                    }
                    if (player.isHero()) {
                        HeroController.removeSkills(player);
                    }
                    if (player.isCastingNow()) {
                        player.abortCast(true, false);
                    }
                    if (player.isMounted()) {
                        player.setMount(0, 0, 0);
                    }
                    if (player.getPet() != null) {
                        Summon summon = player.getPet();
                        if (summon.isPet()) {
                            summon.unSummon();
                        } else {
                            summon.getEffectList().stopAllEffects();
                        }
                    }
                    if (player.getAgathionId() > 0) {
                        player.setAgathion(0);
                    }
                    player.sendSkillList();
                    ItemInstance object32 = player.getInventory().getPaperdollItem(5);
                    if (object32 != null && object32.isHeroWeapon()) {
                        player.getInventory().unEquipItem(object32);
                        player.abortAttack(true, true);
                        player.refreshExpertisePenalty();
                    }
                    if (Config.OLY_REMOVE_AUTOSHOTS) {
                        Set<Integer> set = player.getAutoSoulShot();
                        object = set.iterator();
                        while (object.hasNext()) {
                            int n = (Integer)object.next();
                            player.removeAutoSoulShot(n);
                            player.sendPacket((IStaticPacket)new ExAutoSoulShot(n, false, 0));
                        }
                    }
                    if ((object2 = player.getActiveWeaponInstance()) != null) {
                        ((ItemInstance)object2).setChargedSpiritshot(0);
                        ((ItemInstance)object2).setChargedSoulshot(0);
                    }
                    this.restoreHPCPMP();
                    player.broadcastUserInfo(true, new UserInfoType[0]);
                    if (this.getType() != CompetitionType.TEAM_CLASS_FREE) {
                        if (player.getParty() == null) continue;
                        player.getParty().removePartyMember(player, false);
                        continue;
                    }
                    boolean bl3 = false;
                    if (player.getParty() != null) {
                        if (player.getParty().getPartyMembers().size() != participant.getPlayers().length) {
                            bl3 = true;
                        } else {
                            for (Player player2 : player.getParty().getPartyMembers()) {
                                boolean bl4 = false;
                                for (Player player3 : participant.getPlayers()) {
                                    if (player2 != player3) continue;
                                    bl4 = true;
                                }
                                if (bl4) continue;
                                bl3 = true;
                            }
                        }
                    } else {
                        bl3 = true;
                    }
                    if (!bl3) continue;
                    Player[] playerArray = participant.getPlayers();
                    if (playerArray[0].getParty() != null) {
                        playerArray[0].getParty().removePartyMember(playerArray[0], false);
                    }
                    Party party = new Party(playerArray[0], 0);
                    playerArray[0].setParty(party);
                    if (playerArray[1].isInParty()) {
                        playerArray[1].getParty().removePartyMember(playerArray[1], false);
                    }
                    party.addPartyMember(playerArray[1]);
                    if (playerArray[2].isInParty()) {
                        playerArray[2].getParty().removePartyMember(playerArray[2], false);
                    }
                    party.addPartyMember(playerArray[2]);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void applyBuffsIfNoOlympiadBufferInstance() {
        boolean bl = false;
        for (NpcInstance npcInstance : this.getStadium().getNpcs()) {
            if (!(npcInstance instanceof OlympiadBufferInstance)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            for (Participant participant : this._participants) {
                for (Player player : participant.getPlayers()) {
                    try {
                        player.getEffectList().stopEffects(EffectType.BuffImmunity);
                        TIntIntHashMap tIntIntHashMap = (TIntIntHashMap)Config.OLY_BUFFS.get(player.getActiveClassId());
                        if (tIntIntHashMap == null || tIntIntHashMap.isEmpty()) continue;
                        for (int n : tIntIntHashMap.keys()) {
                            Skill skill = SkillTable.getInstance().getInfo(n, tIntIntHashMap.get(n));
                            skill.getEffects(player, player, false, false);
                        }
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    public void restoreHPCPMP() {
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                try {
                    player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
                    player.setCurrentCp(player.getMaxCp());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void finish() {
        for (Participant participant : this._participants) {
            try {
                for (Player player : participant.getPlayers()) {
                    if (this.getState() == CompetitionState.INIT) continue;
                    if ((double)player.getMaxCp() * 0.8 > player.getCurrentCp()) {
                        player.setCurrentCp((double)player.getMaxCp() * 0.8);
                    }
                    if ((double)player.getMaxHp() * 0.8 > player.getCurrentHp()) {
                        player.setCurrentHp((double)player.getMaxHp() * 0.8, false);
                    }
                    if (!((double)player.getMaxMp() * 0.8 > player.getCurrentMp())) continue;
                    player.setCurrentMp((double)player.getMaxMp() * 0.8);
                }
            }
            finally {
                participant.OnFinish();
            }
        }
    }

    private static int g(int n) {
        return Math.max(1, (Math.min(50, n) - 1) / 5 + 1);
    }

    private int w() {
        int n = Integer.MAX_VALUE;
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                int n2;
                if (player == null || (n2 = NoblesController.getInstance().getPointsOf(player.getObjectId())) >= n) continue;
                n = n2;
            }
        }
        return n;
    }

    private void a(Participant participant, Participant participant2, boolean bl) {
        this.a(participant, participant2, bl, false);
    }

    /*
     * WARNING - void declaration
     */
    private void a(Participant participant, Participant participant2, boolean bl, boolean bl2) {
        int n;
        Object object;
        Player player;
        int n2;
        Player player2;
        int n3;
        if (!bl2) {
            this.broadcastPacket(bl ? SystemMsg.THERE_IS_NO_VICTOR_THE_MATCH_ENDS_IN_A_TIE : new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addString(participant.getName()), true, true);
        }
        ExReceiveOlympiadResult exReceiveOlympiadResult = new ExReceiveOlympiadResult(bl ? 0 : participant.getSide(), bl ? "" : participant.getName());
        long l = 0L;
        if (this.ch > 0L) {
            l = Math.min(Config.OLYMPIAD_COMPETITION_TIME, System.currentTimeMillis() - this.ch) / 1000L;
        }
        if (bl) {
            if (NoblesController.getInstance() == null) {
                return;
            }
            for (Participant participant3 : new Participant[]{participant, participant2}) {
                for (Player player3 : participant3.getPlayers()) {
                    Effect effect22;
                    if (player3 == null) continue;
                    NoblesController.NobleRecord nobleRecord = NoblesController.getInstance().getNobleRecord(player3.getObjectId());
                    ++nobleRecord.comp_done;
                    switch (this.getType()) {
                        case CLASS_FREE: {
                            ++nobleRecord.class_free_cnt;
                            break;
                        }
                        case CLASS_INDIVIDUAL: 
                        case CLASS_TYPE_INDIVIDUAL: {
                            ++nobleRecord.class_based_cnt;
                            break;
                        }
                        case TEAM_CLASS_FREE: {
                            ++nobleRecord.team_cnt;
                        }
                    }
                    NoblesController.getInstance().SaveNobleRecord(nobleRecord);
                    for (Effect effect22 : player3.getEffectList().getAllEffects()) {
                        if (effect22 == null || !effect22.isCancelable()) continue;
                        effect22.exit();
                    }
                    player3.sendChanges();
                    player3.updateEffectIcons();
                    Participant participant4 = participant3 == participant2 ? participant : participant2;
                    effect22 = participant4.getPlayers().length > 0 && participant4.getPlayers()[0] != null ? NoblesController.getInstance().getNobleRecord(participant4.getPlayers()[0].getObjectId()) : null;
                    CompetitionController.getInstance().addCompetitionResult(OlyController.getInstance().getCurrentSeason(), nobleRecord, 0, (NoblesController.NobleRecord)((Object)effect22), 0, this.getType(), bl, bl2, l);
                    player3.getListeners().onOlyCompetitionCompleted(this, false);
                }
            }
            return;
        }
        Player[] playerArray = participant2.getPlayers();
        Player[] playerArray2 = participant.getPlayers();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for (n3 = 0; n3 < playerArray.length; ++n3) {
            try {
                player2 = playerArray[n3];
                if (player2 == null) continue;
                n5 += NoblesController.getInstance().getPointsOf(player2.getObjectId());
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (n3 = 0; n3 < playerArray2.length; ++n3) {
            try {
                player2 = playerArray2[n3];
                if (player2 == null) continue;
                n6 += NoblesController.getInstance().getPointsOf(player2.getObjectId());
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        n3 = Math.max(0, Math.min(n6, n5));
        for (n2 = 0; n2 < playerArray.length; ++n2) {
            try {
                player = playerArray[n2];
                if (player == null || NoblesController.getInstance() == null) continue;
                object = NoblesController.getInstance().getNobleRecord(player.getObjectId());
                int n7 = ((NoblesController.NobleRecord)object).points_current;
                n = Math.max(1, (int)((double)n3 * Config.OLY_LOOSE_POINTS_MUL));
                int n8 = Math.max(0, n7 - n);
                n4 += n;
                ((NoblesController.NobleRecord)object).points_current = n8;
                ++((NoblesController.NobleRecord)object).comp_loose;
                ++((NoblesController.NobleRecord)object).comp_done;
                switch (this.getType()) {
                    case CLASS_FREE: {
                        ++((NoblesController.NobleRecord)object).class_free_cnt;
                        break;
                    }
                    case CLASS_INDIVIDUAL: 
                    case CLASS_TYPE_INDIVIDUAL: {
                        ++((NoblesController.NobleRecord)object).class_based_cnt;
                        break;
                    }
                    case TEAM_CLASS_FREE: {
                        ++((NoblesController.NobleRecord)object).team_cnt;
                    }
                }
                NoblesController.getInstance().SaveNobleRecord((NoblesController.NobleRecord)object);
                exReceiveOlympiadResult.add(participant2.getSide(), player, (int)participant2.getDamageOf(player), n8, -n);
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_LOST_S2_POINTS_IN_THE_GRAND_OLYMPIAD_GAMES).addName(player)).addNumber(n));
                for (QuestState questState : player.getAllQuestsStates()) {
                    if (!questState.isStarted()) continue;
                    questState.getQuest().notifyOlympiadResult(questState, this.getType(), false);
                }
                for (Effect effect : player.getEffectList().getAllEffects()) {
                    if (effect == null || !effect.isCancelable()) continue;
                    effect.exit();
                }
                player.sendChanges();
                player.updateEffectIcons();
                CompetitionController.getInstance().addCompetitionResult(OlyController.getInstance().getCurrentSeason(), NoblesController.getInstance().getNobleRecord(playerArray2[n2].getObjectId()), n, NoblesController.getInstance().getNobleRecord(player.getObjectId()), n, this.getType(), bl, bl2, l);
                player.getListeners().onOlyCompetitionCompleted(this, false);
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (!bl2) {
            n2 = n4 / playerArray.length;
            for (int i = 0; i < playerArray2.length; ++i) {
                try {
                    void var19_45;
                    object = playerArray2[i];
                    NoblesController.NobleRecord nobleRecord = NoblesController.getInstance().getNobleRecord(((GameObject)object).getObjectId());
                    n = Math.max(0, nobleRecord.points_current + n2);
                    NoblesController.getInstance().setPointsOf(((GameObject)object).getObjectId(), n);
                    nobleRecord.points_current = n;
                    ++nobleRecord.comp_win;
                    ++nobleRecord.comp_done;
                    switch (this.getType()) {
                        case CLASS_FREE: {
                            ++nobleRecord.class_free_cnt;
                            break;
                        }
                        case CLASS_INDIVIDUAL: 
                        case CLASS_TYPE_INDIVIDUAL: {
                            ++nobleRecord.class_based_cnt;
                            break;
                        }
                        case TEAM_CLASS_FREE: {
                            ++nobleRecord.team_cnt;
                        }
                    }
                    NoblesController.getInstance().SaveNobleRecord(nobleRecord);
                    exReceiveOlympiadResult.add(participant.getSide(), (Player)object, (int)participant.getDamageOf((Player)object), n, n2);
                    ((Player)object).sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_EARNED_S2_POINTS_IN_THE_GRAND_OLYMPIAD_GAMES).addName((GameObject)object)).addNumber(n2));
                    QuestState[] object2 = ((Player)object).getAllQuestsStates();
                    int n7 = object2.length;
                    for (int j = 0; j < n7; ++j) {
                        QuestState questState = object2[j];
                        if (!questState.isStarted()) continue;
                        questState.getQuest().notifyOlympiadResult(questState, this.getType(), !bl);
                    }
                    for (Effect effect : ((Creature)object).getEffectList().getAllEffects()) {
                        if (effect == null || !effect.isCancelable()) continue;
                        effect.exit();
                    }
                    ((Player)object).sendChanges();
                    ((Player)object).updateEffectIcons();
                    Object var19_41 = null;
                    switch (this.getType()) {
                        case CLASS_FREE: {
                            int[] nArray = Config.OLY_VICTORY_CFREE_RITEMCNT;
                            break;
                        }
                        case CLASS_INDIVIDUAL: 
                        case CLASS_TYPE_INDIVIDUAL: {
                            int[] nArray = Config.OLY_VICTORY_CBASE_RITEMCNT;
                            break;
                        }
                        case TEAM_CLASS_FREE: {
                            int[] nArray = Config.OLY_VICTORY_3TEAM_RITEMCNT;
                        }
                    }
                    if (!ArrayUtils.isEmpty((int[])var19_45)) {
                        for (n7 = 0; n7 < Config.OLY_VICTORY_RITEMID.length; ++n7) {
                            if (Config.OLY_VICTORY_RITEMID[n7] == 0 || var19_45[n7] <= 0 || !Rnd.chance(Config.OLY_VICTORY_RITEMID_CHANCE[n7])) continue;
                            ((Player)object).getInventory().addItem(Config.OLY_VICTORY_RITEMID[n7], (long)var19_45[n7]);
                            ((Player)object).sendPacket((IStaticPacket)SystemMessage.obtainItems(Config.OLY_VICTORY_RITEMID[n7], (long)var19_45[n7], 0));
                        }
                    }
                    CompetitionController.getInstance().addCompetitionResult(OlyController.getInstance().getCurrentSeason(), NoblesController.getInstance().getNobleRecord(((GameObject)object).getObjectId()), n2, NoblesController.getInstance().getNobleRecord(playerArray[i].getObjectId()), n2, this.getType(), bl, bl2, l);
                    ((Player)object).getListeners().onOlyCompetitionCompleted(this, !bl);
                    if (bl) continue;
                    OneDayRewardHolder.getInstance().fireRequirements((Player)object, null, WinInOlympiadRequirement.class);
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            for (n2 = 0; n2 < playerArray2.length; ++n2) {
                try {
                    player = playerArray2[n2];
                    object = NoblesController.getInstance().getNobleRecord(player.getObjectId());
                    exReceiveOlympiadResult.add(participant.getSide(), player, (int)participant.getDamageOf(player), ((NoblesController.NobleRecord)object).points_current, 0);
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        this.broadcastPacket(exReceiveOlympiadResult, true, false);
    }

    public boolean ValidateParticipants() {
        boolean bl = false;
        Participant participant = null;
        Participant participant2 = null;
        for (Participant participant3 : this._participants) {
            if (!participant3.validateThis()) {
                bl = true;
                participant2 = participant3;
                break;
            }
            participant = participant3;
        }
        if (bl) {
            if (participant2 != null) {
                if (participant == null) {
                    for (Participant participant3 : this._participants) {
                        if (participant3 == participant2) continue;
                        participant = participant3;
                    }
                }
                this.a(participant, participant2, false, true);
            }
            this.cancelTask();
            CompetitionController.getInstance().FinishCompetition(this);
            return true;
        }
        return false;
    }

    public synchronized void ValidateWinner() {
        if (this.getState() == CompetitionState.INIT) {
            this.broadcastPacket(SystemMsg.YOUR_OPPONENT_MADE_HASTE_WITH_THEIR_TAIL_BETWEEN_THEIR_LEGS_THE_MATCH_HAS_BEEN_CANCELLED, true, false);
            if (!this._participants[0].isAlive()) {
                this.a(this._participants[1], this._participants[0], false, true);
            } else if (!this._participants[1].isAlive()) {
                this.a(this._participants[0], this._participants[1], false, true);
            }
            this.cancelTask();
            CompetitionController.getInstance().FinishCompetition(this);
            return;
        }
        if (!(this.getState() == CompetitionState.FINISH || this._participants[0].isAlive() && this._participants[1].isAlive())) {
            this.cancelTask();
            this.setState(CompetitionState.FINISH);
            CompetitionController.getInstance().scheduleFinishCompetition(this, 20, 100L);
        }
        if (this.getState() == CompetitionState.FINISH) {
            if (!this._participants[0].isAlive()) {
                this.a(this._participants[1], this._participants[0], false);
            } else if (!this._participants[1].isAlive()) {
                this.a(this._participants[0], this._participants[1], false);
            } else {
                double d;
                double d2 = this._participants[0].getTotalDamage();
                if (d2 < (d = this._participants[1].getTotalDamage())) {
                    this.a(this._participants[0], this._participants[1], false);
                } else if (d2 > d) {
                    this.a(this._participants[1], this._participants[0], false);
                } else {
                    this.a(this._participants[0], this._participants[1], true);
                }
            }
            for (Participant participant : this._participants) {
                for (Player player : participant.getPlayers()) {
                    if (player.getCurrentHp() <= 0.5) {
                        player.doRevive(100.0);
                    }
                    player.sendPacket((IStaticPacket)new ExOlympiadMode(0));
                    OneDayRewardHolder.getInstance().fireRequirements(player, null, BattleInOlympiadRequirement.class);
                }
            }
            this.broadcastPacket(new ExOlympiadMode(3), false, true);
        }
    }

    public void teleportParticipantsOnStadium() {
        this.bC();
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                try {
                    Summon summon;
                    if (player == null) continue;
                    Location location = Location.findAroundPosition(this.getStadium().getLocForParticipant(participant), 0, 32);
                    player.setVar("backCoords", player.getLoc().toXYZString(), -1L);
                    player.teleToLocation(location, this.b);
                    player.sendPacket((IStaticPacket)new ExOlympiadMode(participant.getSide()));
                    if (this.getType() == CompetitionType.TEAM_CLASS_FREE) {
                        player.setTeam(participant.getSide() == 1 ? TeamType.BLUE : TeamType.RED);
                    }
                    if ((summon = player.getPet()) == null) continue;
                    if (summon.isPet()) {
                        summon.unSummon();
                        continue;
                    }
                    summon.teleToLocation(location, this.b);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void teleportParticipantsBack() {
        this.bB();
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                try {
                    if (player == null || player.getVar("backCoords") == null) continue;
                    Location location = Location.parseLoc(player.getVar("backCoords"));
                    player.unsetVar("backCoords");
                    player.sendPacket((IStaticPacket)new ExOlympiadMode(0));
                    if (player.isBlocked()) {
                        player.unblock();
                    }
                    if (this.getType() == CompetitionType.TEAM_CLASS_FREE) {
                        player.setTeam(TeamType.NONE);
                    }
                    player.setReflection(0);
                    player.teleToLocation(location);
                    Summon summon = player.getPet();
                    if (summon == null) continue;
                    if (summon.isPet()) {
                        summon.unSummon();
                        continue;
                    }
                    summon.setReflection(0);
                    summon.teleToLocation(location);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void broadcastEverybodyOlympiadUserInfo() {
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                if (player == null) continue;
                ExOlympiadUserInfo exOlympiadUserInfo = new ExOlympiadUserInfo(player);
                this.broadcastPacket(exOlympiadUserInfo, true, true);
                player.broadcastRelation();
            }
        }
    }

    public void broadcastEverybodyEffectIcons() {
        for (Participant participant : this._participants) {
            for (Player player : participant.getPlayers()) {
                this.broadcastEffectIcons(player, player.getEffectList().getAllFirstEffects());
            }
        }
    }

    public void broadcastEffectIcons(Player player, Collection<Effect> collection) {
        ExOlympiadSpelledInfo exOlympiadSpelledInfo = new ExOlympiadSpelledInfo();
        for (Effect effect : collection) {
            if (effect == null || !effect.isInUse()) continue;
            effect.addOlympiadSpelledIcon(player, exOlympiadSpelledInfo);
        }
        if (this.getState() == CompetitionState.PLAYING) {
            this.broadcastPacket(exOlympiadSpelledInfo, true, true);
        } else {
            player.getOlyParticipant().sendPacket(exOlympiadSpelledInfo);
        }
    }

    public void broadcastPacket(IStaticPacket iStaticPacket, boolean bl, boolean bl2) {
        block5: {
            if (this.getState() == null) break block5;
            if (this.getState() == CompetitionState.INIT && bl) {
                for (Participant participant : this._participants) {
                    for (Player player : participant.getPlayers()) {
                        player.sendPacket(iStaticPacket);
                    }
                }
            } else {
                for (Player player : this.getStadium().getPlayers()) {
                    if ((!bl || !player.isOlyParticipant()) && (!bl2 || !player.isOlyObserver())) continue;
                    player.sendPacket(iStaticPacket);
                }
            }
        }
    }

    public synchronized void scheduleTask(Runnable runnable, long l) {
        this.V = ThreadPoolManager.getInstance().schedule(runnable, l);
    }

    public synchronized void cancelTask() {
        if (this.V != null) {
            this.V.cancel(false);
            this.V = null;
        }
    }

    public Participant[] getParticipants() {
        return this._participants;
    }

    public static SystemMessage checkPlayer(Player player) {
        if (!player.isNoble()) {
            return (SystemMessage)new SystemMessage(SystemMsg.C1_DOES_NOT_MEET_THE_PARTICIPATION_REQUIREMENTS_ONLY_NOBLESSE_CHARACTERS_CAN_PARTICIPATE_IN_THE_OLYMPIAD).addName(player);
        }
        if (player.isInDuel()) {
            return new SystemMessage(SystemMsg.YOU_CANNOT_OBSERVE_WHILE_YOU_ARE_IN_COMBAT);
        }
        if (player.getBaseSubClass().getClassId() != player.getClassId().getId() || player.getClassId().getLevel() < 4) {
            return (SystemMessage)new SystemMessage(SystemMsg.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER).addName(player);
        }
        if ((double)player.getInventoryLimit() * 0.8 <= (double)player.getInventory().getSize()) {
            return (SystemMessage)new SystemMessage(SystemMsg.C1_SINCE_80_PERCENT_OR_MORE_OF_YOUR_INVENTORY_SLOTS_ARE_FULL_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player);
        }
        if (player.isCursedWeaponEquipped()) {
            return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.SINCE_YOU_NOW_OWN_S1_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player)).addItemName(player.getCursedWeaponEquippedId());
        }
        if (NoblesController.getInstance().getPointsOf(player.getObjectId()) < 1) {
            return (SystemMessage)new SystemMessage(SystemMsg.S1).addString(new CustomMessage("THE_REQUEST_CANNOT_BE_COMPLETED_BECAUSE_THE_REQUIREMENTS_ARE_NOT_MET_IN_ORDER_TO_PARTICIPATE_IN", player, new Object[0]).toString());
        }
        return null;
    }
}
