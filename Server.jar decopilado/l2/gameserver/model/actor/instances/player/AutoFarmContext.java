/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model.actor.instances.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import l2.commons.lang.reference.HardReference;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.actor.player.AutoArcherFarmTask;
import l2.gameserver.model.actor.player.AutoFarmEndTask;
import l2.gameserver.model.actor.player.AutoHealFarmTask;
import l2.gameserver.model.actor.player.AutoMagicFarmTask;
import l2.gameserver.model.actor.player.AutoPhysicalFarmTask;
import l2.gameserver.model.actor.player.AutoSummonFarmTask;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.instances.ChestInstance;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.taskmanager.AutoFarmManager;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AutoFarmContext {
    private final List<Integer> bh = Arrays.asList(0, 1, 2, 3);
    private final List<Integer> bi = Arrays.asList(4, 5);
    private final List<Integer> bj = Arrays.asList(6, 7, 8, 9);
    private final List<Integer> bk = Arrays.asList(10, 11);
    private final List<Integer> bl = new ArrayList<Integer>();
    private final List<Integer> bm = new ArrayList<Integer>();
    private final List<Integer> bn = new ArrayList<Integer>();
    private final List<Integer> bo = new ArrayList<Integer>();
    private final List<Integer> bp = new ArrayList<Integer>();
    private final List<Integer> bq = new ArrayList<Integer>();
    private final List<Integer> br = new ArrayList<Integer>();
    private final HardReference<Player> S;
    private int jO;
    private int jP;
    private int jQ;
    private int jR;
    private boolean cD = false;
    private int jS;
    private boolean cE = false;
    private int jT;
    private boolean cF = false;
    private int jU;
    private boolean cG = false;
    private int jV;
    private boolean cH = false;
    private int jW;
    private boolean cI = false;
    private int jX;
    private boolean cJ = false;
    private int jY;
    private int jZ;
    private int ka;
    private int kb;
    private int kc;
    private int kd;
    private int ke;
    private boolean cK = false;
    private boolean cL = false;
    private boolean cM = false;
    private boolean cN = false;
    private boolean cO = false;
    private boolean cP = false;
    private boolean cQ = false;
    private boolean cR = false;
    private long bN;
    private long bO = 0L;
    private Location E = null;
    private ScheduledFuture<?> S;
    private ScheduledFuture<?> T;
    public static final int FARM_TYPE_Fighter = 0;
    public static final int FARM_TYPE_Archer = 1;
    public static final int FARM_TYPE_Mage = 2;
    public static final int FARM_TYPE_Support = 3;
    public static final int FARM_TYPE_Summon = 4;
    public static final String VAR_NAME_activeFarmOnlineTask = "activeFarmOnlineTask";
    public static final String VAR_NAME_activeFarmTask = "activeFarmTask";

    public AutoFarmContext(Player player) {
        this.S = player.getRef();
    }

    private static boolean a(Skill skill) {
        return skill.getSkillType() == Skill.SkillType.MANAHEAL || skill.getSkillType() == Skill.SkillType.MANAHEAL_PERCENT;
    }

    private static boolean b(Skill skill) {
        return skill.getSkillType() == Skill.SkillType.HEAL || skill.getSkillType() == Skill.SkillType.HEAL_PERCENT;
    }

    public Player getPlayer() {
        return this.S.get();
    }

    public void setFarmTypeValue(int n) {
        this.jO = Math.max(0, Math.min(n, 4));
        if (this.isAutofarming()) {
            this.stopFarmTask(true);
        }
    }

    private int u() {
        return this.jO;
    }

    public void setRadiusValue(int n) {
        this.jQ = n;
    }

    public void setShortcutPageValue(int n) {
        int n2;
        if (n < 1) {
            n = 1;
        } else if (n > 10) {
            n = 10;
        }
        this.jP = n2 = n - 1;
    }

    public int getAttackPercent() {
        return this.kb;
    }

    public int getAttackChance() {
        return this.jR;
    }

    public int getChancePercent() {
        return this.kc;
    }

    public int getChanceChance() {
        return this.jS;
    }

    public int getSelfPercent() {
        return this.kd;
    }

    public int getSelfChance() {
        return this.jT;
    }

    public int getLifePercent() {
        return this.ke;
    }

    public int getLifeChance() {
        return this.jU;
    }

    public void setAttackSkillValue(boolean bl, int n) {
        if (bl) {
            this.kb = n;
        } else {
            this.jR = n;
        }
    }

    public void setChanceSkillValue(boolean bl, int n) {
        if (bl) {
            this.kc = n;
        } else {
            this.jS = n;
        }
    }

    public void setSelfSkillValue(boolean bl, int n) {
        if (bl) {
            this.kd = n;
        } else {
            this.jT = n;
        }
    }

    public void setLifeSkillValue(boolean bl, int n) {
        if (bl) {
            this.ke = n;
        } else {
            this.jU = n;
        }
    }

    public void restoreVariables(Player player) {
        this.setAttackSkillValue(false, player.getVarInt("attackChanceSkills", Config.ATTACK_SKILL_CHANCE));
        this.setAttackSkillValue(true, player.getVarInt("attackSkillsPercent", Config.ATTACK_SKILL_PERCENT));
        this.setChanceSkillValue(false, player.getVarInt("chanceChanceSkills", Config.CHANCE_SKILL_CHANCE));
        this.setChanceSkillValue(true, player.getVarInt("chanceSkillsPercent", Config.CHANCE_SKILL_PERCENT));
        this.setSelfSkillValue(false, player.getVarInt("selfChanceSkills", Config.SELF_SKILL_CHANCE));
        this.setSelfSkillValue(true, player.getVarInt("selfSkillsPercent", Config.SELF_SKILL_PERCENT));
        this.setLifeSkillValue(false, player.getVarInt("healChanceSkills", Config.HEAL_SKILL_CHANCE));
        this.setLifeSkillValue(true, player.getVarInt("healSkillsPercent", Config.HEAL_SKILL_PERCENT));
        this.setSummonAttackSkillValue(false, player.getVarInt("attackSummonChanceSkills", Config.SUMMON_ATTACK_SKILL_CHANCE));
        this.setSummonAttackSkillValue(true, player.getVarInt("attackSummonSkillsPercent", Config.SUMMON_ATTACK_SKILL_PERCENT));
        this.setSummonSelfSkillValue(false, player.getVarInt("selfSummonChanceSkills", Config.SUMMON_SELF_SKILL_CHANCE));
        this.setSummonSelfSkillValue(true, player.getVarInt("selfSummonSkillsPercent", Config.SUMMON_SELF_SKILL_PERCENT));
        this.setSummonLifeSkillValue(false, player.getVarInt("healSummonChanceSkills", Config.SUMMON_HEAL_SKILL_CHANCE));
        this.setSummonLifeSkillValue(true, player.getVarInt("healSummonSkillsPercent", Config.SUMMON_HEAL_SKILL_PERCENT));
        this.setShortcutPageValue(player.getVarInt("shortcutPage", Config.SHORTCUT_PAGE));
        this.setRadiusValue(player.getVarInt("farmDistance", Config.SEARCH_DISTANCE));
        this.setFarmTypeValue(player.getVarInt("farmType", Config.FARM_TYPE));
        this.setRndAttackSkills(player.getVarB("farmRndAttackSkills", false), true);
        this.setRndChanceSkills(player.getVarB("farmRndChanceSkills", false), true);
        this.setRndSelfSkills(player.getVarB("farmRndSelfSkills", false), true);
        this.setRndLifeSkills(player.getVarB("farmRndLifeSkills", false), true);
        this.setRndSummonAttackSkills(player.getVarB("farmRndSummonAttackSkills", false), true);
        this.setRndSummonSelfSkills(player.getVarB("farmRndSummonSelfSkills", false), true);
        this.setRndSummonLifeSkills(player.getVarB("farmRndSummonLifeSkills", false), true);
        this.setLeaderAssist(player.getVarB("farmLeaderAssist", false), true);
        this.setKeepLocation(player.getLoc(), player.getVarB("farmKeepLocation", false), true);
        this.setExDelaySkill(player.getVarB("farmExDelaySkill", false), true);
        this.setExSummonDelaySkill(player.getVarB("farmExSummonDelaySkill", false), true);
        this.setRunTargetCloseUp(player.getVarB("farmRunTargetCloseUp", false), true);
        this.setUseSummonSkills(player.getVarB("farmUseSummonSkills", false), true);
        this.setAssistMonsterAttack(player.getVarB("farmAssistMonsterAttack", false), true);
        this.setTargetRestoreMp(player.getVarB("farmTargetRestoreMp", false), true);
        this.U(player);
    }

    /*
     * WARNING - void declaration
     */
    private void U(Player player) {
        String string;
        String string2;
        String string3;
        String string4;
        String[] stringArray;
        String[] stringArray2;
        String string5 = player.getVar("farmAttackSkills");
        if (string5 != null && !string5.isEmpty()) {
            this.getAttackSpells().clear();
            stringArray = stringArray2 = string5.split(";");
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                Skill object;
                String string6 = stringArray[i];
                if (string6 == null || (object = player.getKnownSkill(Integer.parseInt(string6))) == null) continue;
                this.getAttackSpells().add(object.getId());
            }
        }
        if ((stringArray2 = player.getVar("farmChanceSkills")) != null && !stringArray2.isEmpty()) {
            this.getChanceSpells().clear();
            for (String string6 : stringArray = stringArray2.split(";")) {
                Skill skill;
                if (string6 == null || (skill = player.getKnownSkill(Integer.parseInt(string6))) == null) continue;
                this.getChanceSpells().add(skill.getId());
            }
        }
        if ((stringArray = player.getVar("farmSelfSkills")) != null && !stringArray.isEmpty()) {
            void stringArray6;
            String[] stringArray3;
            this.getSelfSpells().clear();
            String[] stringArray4 = stringArray3 = stringArray.split(";");
            int n = stringArray4.length;
            boolean bl = false;
            while (stringArray6 < n) {
                Skill skill;
                String string7 = stringArray4[stringArray6];
                if (string7 != null && (skill = player.getKnownSkill(Integer.parseInt(string7))) != null) {
                    this.getSelfSpells().add(skill.getId());
                }
                ++stringArray6;
            }
        }
        if ((string4 = player.getVar("farmHealSkills")) != null && !string4.isEmpty()) {
            void var9_32;
            String[] stringArray4;
            this.getLowLifeSpells().clear();
            String[] stringArray5 = stringArray4 = string4.split(";");
            int string8 = stringArray5.length;
            boolean bl = false;
            while (var9_32 < string8) {
                Skill skill;
                String string9 = stringArray5[var9_32];
                if (string9 != null && (skill = player.getKnownSkill(Integer.parseInt(string9))) != null) {
                    this.getLowLifeSpells().add(skill.getId());
                }
                ++var9_32;
            }
        }
        if ((string3 = player.getVar("farmAttackSummonSkills")) != null && !string3.isEmpty()) {
            void var10_40;
            String[] stringArray5;
            this.getSummonAttackSpells().clear();
            String[] stringArray6 = stringArray5 = string3.split(";");
            int n = stringArray6.length;
            boolean bl = false;
            while (var10_40 < n) {
                String string10 = stringArray6[var10_40];
                if (string10 != null) {
                    this.getSummonAttackSpells().add(Integer.parseInt(string10));
                }
                ++var10_40;
            }
        }
        if ((string2 = player.getVar("farmSelfSummonSkills")) != null && !string2.isEmpty()) {
            void var11_47;
            String[] stringArray7;
            this.getSummonSelfSpells().clear();
            String[] stringArray8 = stringArray7 = string2.split(";");
            int n = stringArray8.length;
            boolean bl = false;
            while (var11_47 < n) {
                String string11 = stringArray8[var11_47];
                if (string11 != null) {
                    this.getSummonSelfSpells().add(Integer.parseInt(string11));
                }
                ++var11_47;
            }
        }
        if ((string = player.getVar("farmHealSummonSkills")) != null && !string.isEmpty()) {
            String[] stringArray9;
            this.getSummonHealSpells().clear();
            for (String string12 : stringArray9 = string.split(";")) {
                if (string12 == null) continue;
                this.getSummonHealSpells().add(Integer.parseInt(string12));
            }
        }
    }

    public void saveSkills(String string) {
        Player player = this.getPlayer();
        if (player != null) {
            this.l(player, string);
        }
    }

    private void l(Player player, String string) {
        List<Integer> list = null;
        switch (string) {
            case "farmAttackSkills": {
                list = this.getAttackSpells();
                break;
            }
            case "farmChanceSkills": {
                list = this.getChanceSpells();
                break;
            }
            case "farmSelfSkills": {
                list = this.getSelfSpells();
                break;
            }
            case "farmHealSkills": {
                list = this.getLowLifeSpells();
                break;
            }
            case "farmAttackSummonSkills": {
                list = this.getSummonAttackSpells();
                break;
            }
            case "farmSelfSummonSkills": {
                list = this.getSummonSelfSpells();
                break;
            }
            case "farmHealSummonSkills": {
                list = this.getSummonHealSpells();
            }
        }
        if (list != null && !list.isEmpty()) {
            Object object = "";
            for (int n : list) {
                object = (String)object + n + ";";
            }
            player.setVar(string, (String)object, -1L);
        }
    }

    public int getShortcutsIndex() {
        return this.jP;
    }

    public int getFarmRadius() {
        return this.jQ;
    }

    private List<Integer> e(List<Integer> list) {
        Player player = this.getPlayer();
        if (player == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(player.getShortCuts()).filter(shortCut -> shortCut.getPage() == this.getShortcutsIndex() && shortCut.getType() == 2 && list.contains(shortCut.getSlot())).map(ShortCut::getId).collect(Collectors.toList());
    }

    private void V(Player player) {
        this.bm.clear();
        List<Integer> list = this.e(this.bi);
        if (!list.isEmpty()) {
            for (int n : list) {
                Skill skill = player.getKnownSkill(n);
                if (skill == null || skill.getSkillType() != Skill.SkillType.DOT && skill.getSkillType() != Skill.SkillType.MDOT && skill.getSkillType() != Skill.SkillType.POISON && skill.getSkillType() != Skill.SkillType.BLEED && skill.getSkillType() != Skill.SkillType.DEBUFF && skill.getSkillType() != Skill.SkillType.SLEEP && skill.getSkillType() != Skill.SkillType.ROOT && skill.getSkillType() != Skill.SkillType.PARALYZE && skill.getSkillType() != Skill.SkillType.MUTE && !skill.isSpoilSkill() && !skill.isSweepSkill() && skill.getId() != 1263) continue;
                this.bm.add(n);
            }
            this.l(player, "farmChanceSkills");
            list.clear();
        }
    }

    public List<Integer> getChanceSpells() {
        return this.bm;
    }

    private void W(Player player) {
        this.bl.clear();
        List<Integer> list = this.e(this.bh);
        if (!list.isEmpty()) {
            for (int n : list) {
                Skill skill = player.getKnownSkill(n);
                if (skill == null || skill.isSpoilSkill() || skill.isSweepSkill() || skill.getId() == 1263 || skill.getSkillType() != Skill.SkillType.AGGRESSION && skill.getSkillType() != Skill.SkillType.PDAM && skill.getSkillType() != Skill.SkillType.MANADAM && skill.getSkillType() != Skill.SkillType.MDAM && skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.CPDAM && skill.getSkillType() != Skill.SkillType.STUN) continue;
                this.bl.add(n);
            }
            this.l(player, "farmAttackSkills");
            list.clear();
        }
    }

    public List<Integer> getAttackSpells() {
        return this.bl;
    }

    private void X(Player player) {
        this.bn.clear();
        List<Integer> list = this.e(this.bj);
        if (!list.isEmpty()) {
            for (int n : list) {
                Skill skill = player.getKnownSkill(n);
                if (skill == null || !skill.isToggle() && !skill.isMusic() && skill.getSkillType() != Skill.SkillType.BUFF && !skill.isCubicSkill()) continue;
                this.bn.add(n);
            }
            this.l(player, "farmSelfSkills");
            list.clear();
        }
    }

    public List<Integer> getSelfSpells() {
        return this.bn;
    }

    public void refreshLowLifeSkills(Player player) {
        this.bo.clear();
        List<Integer> list = this.e(this.bk);
        if (!list.isEmpty()) {
            for (int n : list) {
                Skill skill = player.getKnownSkill(n);
                if (skill == null || skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.HEAL && skill.getSkillType() != Skill.SkillType.HEAL_PERCENT && skill.getSkillType() != Skill.SkillType.MANAHEAL && skill.getSkillType() != Skill.SkillType.MANAHEAL_PERCENT) continue;
                this.bo.add(n);
            }
            this.l(player, "farmHealSkills");
            list.clear();
        }
    }

    public List<Integer> getLowLifeSpells() {
        return this.bo;
    }

    public void checkAllSlots() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        this.V(player);
        this.W(player);
        this.X(player);
        this.refreshLowLifeSkills(player);
    }

    public void startFarmTask() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (this.isAutofarming() || !Config.ALLOW_AUTO_FARM || Config.AUTO_FARM_FOR_PREMIUM && !player.hasBonus()) {
            player.sendMessage(new CustomMessage("AUTO_FARM_PREMIUM_ONLY", player, new Object[0]));
            return;
        }
        int n = AutoFarmManager.getInstance().getActiveFarms(Config.ALLOW_CHECK_HWID_LIMIT ? player.getNetConnection().getHwid() : player.getIP());
        if (n <= 0 && !AutoFarmManager.getInstance().isNonCheckPlayer(player.getObjectId())) {
            player.sendMessage(new CustomMessage("EXCEEDED_LIMIT_ON_USE_OF_SERVICE", player, new Object[0]));
            return;
        }
        if (!Config.AUTO_FARM_ALLOW_FOR_CURSED_WEAPON && player.isCursedWeaponEquipped()) {
            player.sendMessage(new CustomMessage("AUTO_HUNTING_PROHIBITED_CW", player, new Object[0]));
            return;
        }
        if (!Config.AUTO_FARM_LIMIT_ZONE_NAMES.isEmpty()) {
            for (Zone zone : player.getZones()) {
                if (!Config.AUTO_FARM_LIMIT_ZONE_NAMES.contains(zone.getName())) continue;
                player.sendMessage(new CustomMessage("AUTO_HUNTING_PROHIBITED", player, new Object[0]));
                return;
            }
        }
        try {
            if (this.S != null) {
                this.S.cancel(false);
                this.S = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        AutoFarmManager.getInstance().addActiveFarm(Config.ALLOW_CHECK_HWID_LIMIT ? player.getNetConnection().getHwid() : player.getIP(), player.getObjectId());
        if (this.isKeepLocation()) {
            this.setKeepLocation(player.getLoc());
        }
        int n2 = Config.FARM_INTERVAL_TASK;
        switch (this.u()) {
            case 0: {
                if (n2 <= 0) {
                    n2 = player.getPAtkSpd() > 1000 ? 500 : 1000;
                }
                this.S = ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoPhysicalFarmTask(this), 1000L, n2);
                break;
            }
            case 1: {
                if (n2 <= 0) {
                    n2 = player.getPAtkSpd() > 1000 ? 500 : 1000;
                }
                this.S = ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoArcherFarmTask(this), 1000L, n2);
                break;
            }
            case 2: {
                if (n2 <= 0) {
                    n2 = player.getMAtkSpd() > 1000 ? 500 : 1000;
                }
                this.S = ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoMagicFarmTask(this), 1000L, n2);
                break;
            }
            case 3: {
                if (n2 <= 0) {
                    n2 = player.getPAtkSpd() > player.getMAtkSpd() ? (player.getPAtkSpd() > 1000 ? 500 : 1000) : (player.getMAtkSpd() > 1000 ? 500 : 1000);
                }
                this.S = ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoHealFarmTask(this), 1000L, n2);
                break;
            }
            case 4: {
                if (player.getPet() == null) {
                    player.sendMessage(new CustomMessage("YOU_HAVE_NO_SUMMON_AUTOFARMING_DEACTIVATE", player, new Object[0]));
                    AutoFarmManager.getInstance().removeActiveFarm(Config.ALLOW_CHECK_HWID_LIMIT ? player.getNetConnection().getHwid() : player.getIP(), player.getObjectId());
                    return;
                }
                if (n2 <= 0) {
                    n2 = player.getPAtkSpd() > player.getMAtkSpd() ? (player.getPAtkSpd() > 1000 ? 700 : 1000) : (player.getMAtkSpd() > 1000 ? 700 : 1000);
                }
                this.S = ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoSummonFarmTask(this), 1000L, n2);
            }
        }
        if (!(!Config.AUTOFARM_TIME_TRACK_USAGE_ONLY || Config.AUTO_FARM_UNLIMITED || Config.AUTO_FARM_PA_UNLIMITED && player.hasBonus())) {
            this.startFarmOnlineTime();
            this.scheduleEnd();
        }
        if (Config.SERVICES_AUTO_FARM_ABNORMAL != AbnormalEffect.NULL) {
            player.startAbnormalEffect(Config.SERVICES_AUTO_FARM_ABNORMAL);
        }
        if (Config.SERVICE_AUTO_FARM_SET_RED_RING) {
            player.setTeam(TeamType.RED);
        }
        player.sendMessage(new CustomMessage("AUTOFARMING_ACTIVATED", player, new Object[0]));
        GameServer.getInstance().getListeners().fireEvent("autoFarmStart", player);
    }

    public void scheduleEnd() {
        Player player = this.getPlayer();
        if (player == null || !this.isAutofarming() || !Config.ALLOW_AUTO_FARM) {
            return;
        }
        long l = this.getActiveTimeRemaining();
        this.cancelEndFuture();
        this.T = ThreadPoolManager.getInstance().schedule(this::stopFarmTask, l);
    }

    public void stopFarmTask() {
        this.stopFarmTask(false);
    }

    public void stopFarmTask(boolean bl) {
        Player player = this.getPlayer();
        if (player == null || !this.isAutofarming() || !Config.ALLOW_AUTO_FARM) {
            return;
        }
        try {
            if (this.S != null) {
                this.S.cancel(false);
                this.S = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (!(!Config.AUTOFARM_TIME_TRACK_USAGE_ONLY || Config.AUTO_FARM_UNLIMITED || Config.AUTO_FARM_PA_UNLIMITED && player.hasBonus())) {
            this.cancelEndFuture();
            long l = this.getActiveTimeRemaining();
            this.resetFarmOnlineTimestamp();
            if (l > 0L) {
                player.setVar(VAR_NAME_activeFarmOnlineTask, l, -1L);
            } else {
                player.unsetVar(VAR_NAME_activeFarmOnlineTask);
            }
        }
        AutoFarmManager.getInstance().removeActiveFarm(Config.ALLOW_CHECK_HWID_LIMIT ? player.getNetConnection().getHwid() : player.getIP(), player.getObjectId());
        if (Config.SERVICES_AUTO_FARM_ABNORMAL != AbnormalEffect.NULL) {
            player.stopAbnormalEffect(Config.SERVICES_AUTO_FARM_ABNORMAL);
        }
        if (Config.SERVICE_AUTO_FARM_SET_RED_RING) {
            player.setTeam(TeamType.NONE);
        }
        player.sendMessage(new CustomMessage("AUTOFARMING_DEACTIVATED", player, new Object[0]));
        GameServer.getInstance().getListeners().fireEvent("autoFarmStop", player);
        if (bl) {
            this.startFarmTask();
        }
    }

    public boolean isAutofarming() {
        return this.S != null;
    }

    public void checkFarmTask() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (!Config.AUTOFARM_TIME_TRACK_USAGE_ONLY) {
            long l2 = player.getVarLong(VAR_NAME_activeFarmTask, 0L);
            if (l2 > l) {
                if (this.T == null) {
                    this.T = ThreadPoolManager.getInstance().schedule(new AutoFarmEndTask(this), l2 - l);
                }
                this.bN = l2;
            } else {
                this.bN = 0L;
            }
        }
    }

    public void cancelEndFuture() {
        try {
            if (this.T != null) {
                this.T.cancel(false);
                this.T = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean isActiveAutofarmAllowed() {
        Player player = this.getPlayer();
        if (player == null) {
            return false;
        }
        return this.T != null || Config.AUTO_FARM_UNLIMITED || Config.AUTO_FARM_PA_UNLIMITED && player.hasBonus() || Config.AUTOFARM_TIME_TRACK_USAGE_ONLY && this.isActiveTimeRemains();
    }

    public long getActiveTimeRemaining() {
        Player player = this.getPlayer();
        if (player == null) {
            return 0L;
        }
        long l = player.getVarLong(VAR_NAME_activeFarmOnlineTask, 0L);
        if (this.getFarmOnlineTimestamp() > 0L) {
            long l2 = System.currentTimeMillis() - this.getFarmOnlineTimestamp();
            l = Math.max(0L, l - l2);
        }
        return l;
    }

    public boolean isActiveTimeRemains() {
        return this.getActiveTimeRemaining() > 0L;
    }

    public boolean isRndAttackSkills() {
        return this.cD;
    }

    public boolean isRndChanceSkills() {
        return this.cE;
    }

    public boolean isRndSelfSkills() {
        return this.cF;
    }

    public boolean isRndLifeSkills() {
        return this.cG;
    }

    public void setRndAttackSkills(boolean bl, boolean bl2) {
        this.cD = bl;
        if (!bl2) {
            this.b("farmRndAttackSkills", this.cD ? 1 : 0);
        }
    }

    public void setRndChanceSkills(boolean bl, boolean bl2) {
        this.cE = bl;
        if (!bl2) {
            this.b("farmRndChanceSkills", this.cE ? 1 : 0);
        }
    }

    public void setRndSelfSkills(boolean bl, boolean bl2) {
        this.cF = bl;
        if (!bl2) {
            this.b("farmRndSelfSkills", this.cF ? 1 : 0);
        }
    }

    public void setRndLifeSkills(boolean bl, boolean bl2) {
        this.cG = bl;
        if (!bl2) {
            this.b("farmRndLifeSkills", this.cG ? 1 : 0);
        }
    }

    public boolean isLeaderAssist() {
        return this.cK;
    }

    public boolean isKeepLocation() {
        return this.cL;
    }

    public boolean isExtraDelaySkill() {
        return this.cM;
    }

    public boolean isExtraSummonDelaySkill() {
        return this.cN;
    }

    public boolean isRunTargetCloseUp() {
        return this.cO;
    }

    public boolean isUseSummonSkills() {
        return this.cR;
    }

    public void setLeaderAssist(boolean bl, boolean bl2) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        this.cK = player.getParty() != null && player.getParty().isLeader(player) ? false : bl;
        if (!bl2) {
            this.b("farmLeaderAssist", this.cK ? 1 : 0);
        }
    }

    public void setKeepLocation(Location location, boolean bl, boolean bl2) {
        this.cL = bl;
        if (!bl2) {
            this.b("farmKeepLocation", this.cL ? 1 : 0);
            if (this.cL) {
                this.setKeepLocation(location);
            }
        }
    }

    public void setExDelaySkill(boolean bl, boolean bl2) {
        this.cM = bl;
        if (!bl2) {
            this.b("farmExDelaySkill", this.cM ? 1 : 0);
        }
    }

    public void setExSummonDelaySkill(boolean bl, boolean bl2) {
        this.cN = bl;
        if (!bl2) {
            this.b("farmExSummonDelaySkill", this.cN ? 1 : 0);
        }
    }

    public void setRunTargetCloseUp(boolean bl, boolean bl2) {
        this.cO = bl;
        if (!bl2) {
            this.b("farmRunTargetCloseUp", this.cO ? 1 : 0);
        }
    }

    public void setUseSummonSkills(boolean bl, boolean bl2) {
        this.cR = bl;
        if (!bl2) {
            this.b("farmUseSummonSkills", this.cR ? 1 : 0);
        }
    }

    public boolean isAssistMonsterAttack() {
        return this.cP;
    }

    public boolean isTargetRestoreMp() {
        return this.cQ;
    }

    public void setAssistMonsterAttack(boolean bl, boolean bl2) {
        this.cP = bl;
        if (!bl2) {
            this.b("farmAssistMonsterAttack", this.cP ? 1 : 0);
        }
    }

    public void setTargetRestoreMp(boolean bl, boolean bl2) {
        this.cQ = bl;
        if (!bl2) {
            this.b("farmTargetRestoreMp", this.cQ ? 1 : 0);
        }
    }

    public List<Integer> getSummonAttackSpells() {
        return this.bp;
    }

    public List<Integer> getSummonSelfSpells() {
        return this.bq;
    }

    public List<Integer> getSummonHealSpells() {
        return this.br;
    }

    public int getSummonAttackPercent() {
        return this.jY;
    }

    public int getSummonAttackChance() {
        return this.jV;
    }

    public int getSummonSelfPercent() {
        return this.jZ;
    }

    public int getSummonSelfChance() {
        return this.jW;
    }

    public int getSummonLifePercent() {
        return this.ka;
    }

    public int getSummonLifeChance() {
        return this.jX;
    }

    public void setSummonAttackSkillValue(boolean bl, int n) {
        if (bl) {
            this.jY = n;
        } else {
            this.jV = n;
        }
    }

    public void setSummonSelfSkillValue(boolean bl, int n) {
        if (bl) {
            this.jZ = n;
        } else {
            this.jW = n;
        }
    }

    public void setSummonLifeSkillValue(boolean bl, int n) {
        if (bl) {
            this.ka = n;
        } else {
            this.jX = n;
        }
    }

    public boolean isRndSummonAttackSkills() {
        return this.cH;
    }

    public boolean isRndSummonSelfSkills() {
        return this.cI;
    }

    public boolean isRndSummonLifeSkills() {
        return this.cJ;
    }

    public void setRndSummonAttackSkills(boolean bl, boolean bl2) {
        this.cH = bl;
        if (!bl2) {
            this.b("farmRndSummonAttackSkills", this.cH ? 1 : 0);
        }
    }

    public void setRndSummonSelfSkills(boolean bl, boolean bl2) {
        this.cI = bl;
        if (!bl2) {
            this.b("farmRndSummonSelfSkills", this.cI ? 1 : 0);
        }
    }

    public void setRndSummonLifeSkills(boolean bl, boolean bl2) {
        this.cJ = bl;
        if (!bl2) {
            this.b("farmRndSummonLifeSkills", this.cJ ? 1 : 0);
        }
    }

    private void b(String string, int n) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        player.setVar(string, n, -1L);
    }

    public final List<NpcInstance> getAroundNpc(Player player, Function<NpcInstance, Boolean> function) {
        ArrayList<NpcInstance> arrayList = new ArrayList<NpcInstance>();
        for (NpcInstance npcInstance : World.getAroundNpc(player, this.getFarmRadius(), 600)) {
            if (!npcInstance.isMonster() || npcInstance.isDead() || !npcInstance.isVisible() || npcInstance instanceof ChestInstance || npcInstance instanceof MinionInstance && ((MinionInstance)npcInstance).getLeader() instanceof RaidBossInstance || npcInstance.isRaid() || !function.apply(npcInstance).booleanValue() || ArrayUtils.contains((int[])Config.AUTO_FARM_IGNORED_NPC_ID, (int)npcInstance.getNpcId()) || !npcInstance.hasAI()) continue;
            if (npcInstance.getAI().getTargetList().isEmpty() || npcInstance.getAI().getTargetList().contains(player)) {
                arrayList.add(npcInstance);
                continue;
            }
            if (player.getParty() == null) continue;
            for (Player player2 : player.getParty()) {
                if (player2 == null || !npcInstance.getAI().getTargetList().contains(player2)) continue;
                arrayList.add(npcInstance);
            }
        }
        return arrayList;
    }

    public Skill nextAttackSkill(NpcInstance npcInstance, long l) {
        Player player = this.getPlayer();
        if (player == null || this.getAttackSpells().isEmpty() || !Rnd.chance(this.getAttackChance())) {
            return null;
        }
        if (this.isExtraDelaySkill() && l > System.currentTimeMillis()) {
            return null;
        }
        double d = player.getCurrentMpPercents();
        if (d < (double)this.getAttackPercent()) {
            return null;
        }
        if (this.isRndAttackSkills()) {
            return this.a(npcInstance);
        }
        for (int n : this.getAttackSpells()) {
            Skill skill = player.getKnownSkill(n);
            if (skill == null || !skill.checkCondition(player, npcInstance, false, false, true) || skill.isOffensive() && skill.getTargetType() == Skill.SkillTargetType.TARGET_ONE && npcInstance == null) continue;
            assert (npcInstance != null);
            player.setTarget(npcInstance);
            player.sendPacket((IStaticPacket)new MyTargetSelected(npcInstance.getObjectId(), player.getLevel() - npcInstance.getLevel()));
            player.sendPacket((IStaticPacket)npcInstance.makeStatusUpdate(9, 10));
            return skill;
        }
        return null;
    }

    private Skill a(NpcInstance npcInstance) {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        Skill skill = null;
        for (int n : this.getAttackSpells()) {
            Skill skill2 = player.getKnownSkill(n);
            if (skill2 == null || !skill2.checkCondition(player, npcInstance, false, false, true) || skill2.isOffensive() && skill2.getTargetType() == Skill.SkillTargetType.TARGET_ONE && npcInstance == null) continue;
            arrayList.add(skill2);
        }
        if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
            assert (npcInstance != null);
            player.setTarget(npcInstance);
            player.sendPacket((IStaticPacket)new MyTargetSelected(npcInstance.getObjectId(), player.getLevel() - npcInstance.getLevel()));
            player.sendPacket((IStaticPacket)npcInstance.makeStatusUpdate(9, 10));
        }
        arrayList.clear();
        return skill;
    }

    public Skill nextChanceSkill(NpcInstance npcInstance, long l) {
        Player player = this.getPlayer();
        if (player == null || this.getChanceSpells().isEmpty() || !Rnd.chance(this.getChanceChance())) {
            return null;
        }
        if (this.isExtraDelaySkill() && l > System.currentTimeMillis()) {
            return null;
        }
        double d = player.getCurrentMpPercents();
        if (npcInstance == null || d < (double)this.getChancePercent()) {
            return null;
        }
        if (this.isRndChanceSkills()) {
            return this.b(npcInstance);
        }
        for (int n : this.getChanceSpells()) {
            Skill skill = player.getKnownSkill(n);
            if (skill == null || !skill.checkCondition(player, npcInstance, false, false, true) || skill.isSpoilSkill() && ((MonsterInstance)npcInstance).isSpoiled() || skill.isSweepSkill() && !npcInstance.isDead() || npcInstance.getEffectList().getEffectsBySkillId(n) != null) continue;
            return skill;
        }
        return null;
    }

    private Skill b(NpcInstance npcInstance) {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        Skill skill = null;
        for (int n : this.getChanceSpells()) {
            Skill skill2 = player.getKnownSkill(n);
            if (skill2 == null || !skill2.checkCondition(player, npcInstance, false, false, true) || skill2.isSpoilSkill() && ((MonsterInstance)npcInstance).isSpoiled() || skill2.isSweepSkill() && !npcInstance.isDead() || npcInstance.getEffectList().getEffectsBySkillId(n) != null) continue;
            arrayList.add(skill2);
        }
        if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        return skill;
    }

    public Skill nextSelfSkill(Creature creature) {
        Player player = this.getPlayer();
        if (player == null || this.getSelfSpells().isEmpty() || !Rnd.chance(this.getSelfChance())) {
            return null;
        }
        double d = player.getCurrentMpPercents();
        if (d < (double)this.getSelfPercent()) {
            return null;
        }
        if (this.isRndSelfSkills()) {
            return this.a(creature);
        }
        for (int n : this.getSelfSpells()) {
            Skill skill = player.getKnownSkill(n);
            if (skill == null || !skill.checkCondition(player, creature != null ? creature : player, false, false, true)) continue;
            if (skill.isToggle() && player.getEffectList().getEffectsBySkillId(n) == null) {
                return skill;
            }
            if (creature != null && creature.getEffectList().getEffectsBySkillId(n) == null && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
                player.setTarget(creature);
                return skill;
            }
            if (creature != null && creature.isSummon() || player.getEffectList().getEffectsBySkillId(n) != null || skill.getTargetType() == Skill.SkillTargetType.TARGET_PET) continue;
            player.setTarget(player);
            return skill;
        }
        return null;
    }

    private Skill a(Creature creature) {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        ArrayList<Skill> arrayList2 = new ArrayList<Skill>();
        Skill skill = null;
        for (int n : this.getSelfSpells()) {
            Skill skill2 = player.getKnownSkill(n);
            if (skill2 == null || !skill2.checkCondition(player, creature != null ? creature : player, false, false, true)) continue;
            if (skill2.isToggle() && player.getEffectList().getEffectsBySkillId(n) == null) {
                arrayList.add(skill2);
                continue;
            }
            if (player.getEffectList().getEffectsBySkillId(n) == null && skill2.getTargetType() != Skill.SkillTargetType.TARGET_PET) {
                arrayList.add(skill2);
            }
            if (creature == null || creature.getEffectList().getEffectsBySkillId(n) != null || skill2.getTargetType() == Skill.SkillTargetType.TARGET_SELF) continue;
            arrayList2.add(skill2);
        }
        boolean bl = true;
        if (!arrayList2.isEmpty()) {
            skill = (Skill)arrayList2.get(Rnd.get(arrayList2.size()));
            bl = false;
        } else if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        arrayList2.clear();
        if (skill == null) {
            return null;
        }
        if (creature != null && !bl) {
            player.setTarget(creature);
        } else {
            player.setTarget(player);
        }
        return skill;
    }

    public Skill nextHealSkill(NpcInstance npcInstance, Creature creature) {
        boolean bl;
        Player player = this.getPlayer();
        if (player == null || this.getLowLifeSpells().isEmpty() || !Rnd.chance(this.getLifeChance())) {
            return null;
        }
        double d = player.getCurrentHpPercents();
        double d2 = creature != null ? creature.getCurrentHpPercents() : 100.0;
        double d3 = creature != null ? creature.getCurrentMpPercents() : 100.0;
        boolean bl2 = d2 < (double)this.getLifePercent();
        boolean bl3 = d < (double)this.getLifePercent();
        boolean bl4 = bl = d3 < (double)this.getLifePercent();
        if (!(bl2 || bl3 || bl)) {
            return null;
        }
        if (this.isRndLifeSkills()) {
            return this.a(npcInstance, creature);
        }
        for (int n : this.getLowLifeSpells()) {
            Skill skill = player.getKnownSkill(n);
            if (skill == null || !skill.checkCondition(player, skill.isOffensive() ? npcInstance : player, false, false, true) || skill.isOffensive() && npcInstance == null) continue;
            if (AutoFarmContext.b(skill)) {
                if (!bl2 && !bl3) continue;
                if (bl2 && creature != null && !creature.isDead() && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
                    if (skill.getTargetType() == Skill.SkillTargetType.TARGET_PET && !creature.isSummon()) continue;
                    player.setTarget(creature);
                    return skill;
                }
                if (bl3) {
                    if (skill.getTargetType() == Skill.SkillTargetType.TARGET_PET) continue;
                    player.setTarget(player);
                    return skill;
                }
                return null;
            }
            if (AutoFarmContext.a(skill) && this.isTargetRestoreMp() && creature != null && bl && !creature.isDead() && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
                player.setTarget(creature);
                return skill;
            }
            return skill;
        }
        return null;
    }

    private Skill a(NpcInstance npcInstance, Creature creature) {
        boolean bl;
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        Skill skill = null;
        double d = player.getCurrentHpPercents();
        double d2 = creature != null ? creature.getCurrentHpPercents() : 100.0;
        double d3 = creature != null ? creature.getCurrentMpPercents() : 100.0;
        boolean bl2 = d2 < (double)this.getLifePercent();
        boolean bl3 = d < (double)this.getLifePercent();
        boolean bl4 = bl = d3 < (double)this.getLifePercent();
        if (!(bl2 || bl3 || bl)) {
            return null;
        }
        for (int n : this.getLowLifeSpells()) {
            Skill skill2 = player.getKnownSkill(n);
            if (skill2 == null || !skill2.checkCondition(player, skill2.isOffensive() ? npcInstance : player, false, false, true) || skill2.isOffensive() && npcInstance == null) continue;
            if (bl2 || bl3) {
                if (!AutoFarmContext.b(skill2)) continue;
                if (bl2) {
                    if (creature == null || creature.isDead() || skill2.getTargetType() == Skill.SkillTargetType.TARGET_SELF || skill2.getTargetType() == Skill.SkillTargetType.TARGET_PET && !creature.isSummon()) continue;
                    arrayList.add(skill2);
                    continue;
                }
                if (!bl3 || skill2.getTargetType() == Skill.SkillTargetType.TARGET_PET) continue;
                arrayList.add(skill2);
                continue;
            }
            if (!bl || !AutoFarmContext.a(skill2) || !this.isTargetRestoreMp() || creature == null || creature.isDead() || skill2.getTargetType() == Skill.SkillTargetType.TARGET_SELF) continue;
            arrayList.add(skill2);
        }
        if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        if (skill == null) {
            return null;
        }
        if (bl2 || bl) {
            player.setTarget(creature);
        } else {
            player.setTarget(player);
        }
        return skill;
    }

    public Location getKeepLocation() {
        return this.E;
    }

    public void setKeepLocation(Location location) {
        this.E = location;
    }

    public Skill nextSummonAttackSkill(NpcInstance npcInstance, Summon summon, long l) {
        if (this.getSummonAttackSpells().isEmpty() || !Rnd.chance(this.getSummonAttackChance())) {
            return null;
        }
        if (this.isExtraSummonDelaySkill() && l > System.currentTimeMillis()) {
            return null;
        }
        double d = summon.getCurrentMpPercents();
        if (d < (double)this.getSummonAttackPercent()) {
            return null;
        }
        if (this.isRndSummonAttackSkills()) {
            return this.a(npcInstance, summon);
        }
        for (int n : this.getSummonAttackSpells()) {
            Skill skill;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, npcInstance, false, false, true) || skill.isOffensive() && skill.getTargetType() == Skill.SkillTargetType.TARGET_ONE && npcInstance == null) continue;
            return skill;
        }
        return null;
    }

    private Skill a(NpcInstance npcInstance, Summon summon) {
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        Skill skill = null;
        for (int n : this.getSummonAttackSpells()) {
            Skill skill2;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill2 = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, npcInstance, false, false, true) || skill2.isOffensive() && skill2.getTargetType() == Skill.SkillTargetType.TARGET_ONE && npcInstance == null) continue;
            arrayList.add(skill2);
        }
        if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        return skill;
    }

    public Skill nextSummonSelfSkill(Summon summon, Creature creature) {
        if (this.getSummonSelfSpells().isEmpty() || !Rnd.chance(this.getSummonSelfChance())) {
            return null;
        }
        double d = summon.getCurrentMpPercents();
        if (d < (double)this.getSummonSelfPercent()) {
            return null;
        }
        if (this.isRndSummonSelfSkills()) {
            return this.a(summon, creature);
        }
        for (int n : this.getSummonSelfSpells()) {
            Skill skill;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, creature != null ? creature : summon, false, false, true)) continue;
            if (skill.isToggle() && summon.getEffectList().getEffectsBySkillId(n) == null) {
                return skill;
            }
            if (creature != null && creature.getEffectList().getEffectsBySkillId(n) == null && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF && skill.getTargetType() != Skill.SkillTargetType.TARGET_PET) {
                summon.setTarget(creature);
                return skill;
            }
            if (summon.getEffectList().getEffectsBySkillId(n) != null) continue;
            return skill;
        }
        return null;
    }

    private Skill a(Summon summon, Creature creature) {
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        ArrayList<Skill> arrayList2 = new ArrayList<Skill>();
        Skill skill = null;
        for (int n : this.getSelfSpells()) {
            Skill skill2;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill2 = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, creature != null ? creature : summon, false, false, true)) continue;
            if (skill2.isToggle() && summon.getEffectList().getEffectsBySkillId(n) == null) {
                arrayList.add(skill2);
                continue;
            }
            if (creature != null && creature.getEffectList().getEffectsBySkillId(n) == null && skill2.getTargetType() != Skill.SkillTargetType.TARGET_SELF && skill2.getTargetType() != Skill.SkillTargetType.TARGET_PET) {
                arrayList2.add(skill2);
            }
            if (summon.getEffectList().getEffectsBySkillId(n) != null) continue;
            arrayList.add(skill2);
        }
        boolean bl = true;
        if (!arrayList2.isEmpty()) {
            skill = (Skill)arrayList2.get(Rnd.get(arrayList2.size()));
            bl = false;
        } else if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        arrayList2.clear();
        if (skill == null) {
            return null;
        }
        if (creature != null && !bl) {
            summon.setTarget(creature);
        } else {
            summon.setTarget(summon);
        }
        return skill;
    }

    public Skill nextSummonHealSkill(NpcInstance npcInstance, Summon summon, Creature creature) {
        boolean bl;
        if (this.getSummonHealSpells().isEmpty() || !Rnd.chance(this.getSummonLifeChance())) {
            return null;
        }
        double d = summon.getCurrentHpPercents();
        double d2 = creature != null ? creature.getCurrentHpPercents() : 100.0;
        double d3 = creature != null ? creature.getCurrentMpPercents() : 100.0;
        boolean bl2 = d2 < (double)this.getSummonLifePercent();
        boolean bl3 = d < (double)this.getSummonLifePercent();
        boolean bl4 = bl = d3 < (double)this.getSummonLifePercent();
        if (!(bl2 || bl3 || bl)) {
            return null;
        }
        if (this.isRndLifeSkills()) {
            return this.a(npcInstance, summon, creature);
        }
        for (int n : this.getSummonHealSpells()) {
            Skill skill;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, creature != null && bl2 ? creature : summon, false, false, true) || skill.isOffensive() && npcInstance == null) continue;
            if (AutoFarmContext.b(skill)) {
                if (!bl2 && !bl3) continue;
                if (bl2 && creature != null && !creature.isDead() && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
                    if (skill.getTargetType() == Skill.SkillTargetType.TARGET_PET && !creature.isSummon()) continue;
                    summon.setTarget(creature);
                    return skill;
                }
                if (bl3) {
                    summon.setTarget(summon);
                    return skill;
                }
                return null;
            }
            if (AutoFarmContext.a(skill) && creature != null && bl && !creature.isDead() && skill.getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
                summon.setTarget(creature);
                return skill;
            }
            return skill;
        }
        return null;
    }

    private Skill a(NpcInstance npcInstance, Summon summon, Creature creature) {
        boolean bl;
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        Skill skill = null;
        double d = summon.getCurrentHpPercents();
        double d2 = creature != null ? creature.getCurrentHpPercents() : 100.0;
        double d3 = creature != null ? creature.getCurrentMpPercents() : 100.0;
        boolean bl2 = d2 < (double)this.getSummonLifePercent();
        boolean bl3 = d < (double)this.getSummonLifePercent();
        boolean bl4 = bl = d3 < (double)this.getSummonLifePercent();
        if (!(bl2 || bl3 || bl)) {
            return null;
        }
        for (int n : this.getSummonHealSpells()) {
            Skill skill2;
            int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
            if (n2 <= 0 || !(skill2 = SkillTable.getInstance().getInfo(n, n2)).checkCondition(summon, creature != null && bl2 ? creature : summon, false, false, true) || skill2.isOffensive() && npcInstance == null) continue;
            if (bl2 || bl3) {
                if (!AutoFarmContext.b(skill2)) continue;
                if (bl2) {
                    if (creature == null || creature.isDead() || skill2.getTargetType() == Skill.SkillTargetType.TARGET_SELF || skill2.getTargetType() == Skill.SkillTargetType.TARGET_PET && !creature.isSummon()) continue;
                    arrayList.add(skill2);
                    continue;
                }
                if (!bl3) continue;
                arrayList.add(skill2);
                continue;
            }
            if (!bl || !AutoFarmContext.a(skill2) || creature == null || creature.isDead() || skill2.getTargetType() == Skill.SkillTargetType.TARGET_SELF) continue;
            arrayList.add(skill2);
        }
        if (!arrayList.isEmpty()) {
            skill = (Skill)arrayList.get(Rnd.get(arrayList.size()));
        }
        arrayList.clear();
        if (skill == null) {
            return null;
        }
        if (bl2 || bl) {
            summon.setTarget(creature);
        } else {
            summon.setTarget(summon);
        }
        return skill;
    }

    public NpcInstance getLeaderTarget(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject != null && gameObject != player && gameObject instanceof NpcInstance && ((NpcInstance)gameObject).hasAI() && ((NpcInstance)gameObject).getAI().getTargetList().contains(player)) {
            return (NpcInstance)gameObject;
        }
        return null;
    }

    public void startFarmOnlineTime() {
        this.bO = System.currentTimeMillis();
    }

    public void resetFarmOnlineTimestamp() {
        this.bO = 0L;
    }

    public long getFarmOnlineTimestamp() {
        return this.bO;
    }

    public void setAutoFarmEndTask(long l) {
        if (l == 0L && this.T != null) {
            this.T.cancel(false);
            this.T = null;
        }
        this.bN = l;
    }

    public long getAutoFarmEnd() {
        return this.bN;
    }

    public static final class SpellType
    extends Enum<SpellType> {
        public static final /* enum */ SpellType ATTACK = new SpellType();
        public static final /* enum */ SpellType CHANCE = new SpellType();
        public static final /* enum */ SpellType SELF = new SpellType();
        public static final /* enum */ SpellType LOWLIFE = new SpellType();
        private static final /* synthetic */ SpellType[] a;

        public static SpellType[] values() {
            return (SpellType[])a.clone();
        }

        public static SpellType valueOf(String string) {
            return Enum.valueOf(SpellType.class, string);
        }

        private static /* synthetic */ SpellType[] a() {
            return new SpellType[]{ATTACK, CHANCE, SELF, LOWLIFE};
        }

        static {
            a = SpellType.a();
        }
    }
}
