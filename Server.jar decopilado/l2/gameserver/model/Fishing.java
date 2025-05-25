/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.GameTimeController;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.games.FishingChampionShipManager;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.oneDayReward.requirement.FishingRequirement;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExFishingEnd;
import l2.gameserver.network.l2.s2c.ExFishingHpRegen;
import l2.gameserver.network.l2.s2c.ExFishingStart;
import l2.gameserver.network.l2.s2c.ExFishingStartCombat;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.FishTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;

public class Fishing {
    private final Player e;
    public static final int FISHING_NONE = 0;
    public static final int FISHING_STARTED = 1;
    public static final int FISHING_WAITING = 2;
    public static final int FISHING_COMBAT = 3;
    private AtomicInteger h;
    private int _time;
    private int gU;
    private int gV;
    private int gW;
    private int gX = -1;
    private int _deceptiveMode;
    private int gY;
    private FishTemplate a;
    private int gZ;
    private Future<?> j;
    private Location x = new Location();

    public Fishing(Player player) {
        this.e = player;
        this.h = new AtomicInteger(0);
    }

    public void setFish(FishTemplate fishTemplate) {
        this.a = fishTemplate;
    }

    public void setLureId(int n) {
        this.gZ = n;
    }

    public int getLureId() {
        return this.gZ;
    }

    public void setFishLoc(Location location) {
        this.x.x = location.x;
        this.x.y = location.y;
        this.x.z = location.z;
    }

    public Location getFishLoc() {
        return this.x;
    }

    public void startFishing() {
        if (!this.h.compareAndSet(0, 1)) {
            return;
        }
        this.e.setFishing(true);
        this.e.broadcastCharInfo();
        this.e.broadcastPacket(new ExFishingStart(this.e, this.a.getType(), this.e.getFishLoc(), Fishing.isNightLure(this.gZ)));
        this.e.sendPacket((IStaticPacket)SystemMsg.YOU_CAST_YOUR_LINE_AND_START_TO_FISH);
        this.bb();
    }

    public void stopFishing() {
        if (this.h.getAndSet(0) == 0) {
            return;
        }
        this.ba();
        this.e.setFishing(false);
        this.e.broadcastPacket(new ExFishingEnd(this.e, false));
        this.e.broadcastCharInfo();
        this.e.sendPacket((IStaticPacket)SystemMsg.YOUR_ATTEMPT_AT_FISHING_HAS_BEEN_CANCELLED);
    }

    public void endFishing(boolean bl) {
        if (!this.h.compareAndSet(3, 0)) {
            return;
        }
        this.ba();
        this.e.setFishing(false);
        this.e.broadcastPacket(new ExFishingEnd(this.e, bl));
        this.e.broadcastCharInfo();
        this.e.sendPacket((IStaticPacket)SystemMsg.YOU_REEL_YOUR_LINE_IN_AND_STOP_FISHING);
        OneDayRewardHolder.getInstance().fireRequirements(this.e, null, FishingRequirement.class);
    }

    private void ba() {
        if (this.j != null) {
            this.j.cancel(false);
            this.j = null;
        }
    }

    private void bb() {
        if (!this.h.compareAndSet(1, 2)) {
            return;
        }
        long l = 10000L;
        switch (this.a.getGroup()) {
            case 0: {
                l = Math.round((double)this.a.getGutsCheckTime() * 1.33);
                break;
            }
            case 1: {
                l = this.a.getGutsCheckTime();
                break;
            }
            case 2: {
                l = Math.round((double)this.a.getGutsCheckTime() * 0.66);
            }
        }
        this.j = ThreadPoolManager.getInstance().scheduleAtFixedRate(new LookingForFishTask(), 10000L, l);
    }

    public boolean isInCombat() {
        return this.h.get() == 3;
    }

    private void bc() {
        if (!this.h.compareAndSet(2, 3)) {
            return;
        }
        this.gU = 0;
        this.gV = 0;
        this.gW = 0;
        this._time = this.a.getCombatTime() / 1000;
        this.gY = this.a.getHP();
        this.gX = Rnd.chance(20) ? 1 : 0;
        switch (Fishing.getLureGrade(this.gZ)) {
            case 0: 
            case 1: {
                this._deceptiveMode = 0;
                break;
            }
            case 2: {
                this._deceptiveMode = Rnd.chance(10) ? 1 : 0;
            }
        }
        ExFishingStartCombat exFishingStartCombat = new ExFishingStartCombat(this.e, this._time, this.a.getHP(), this.gX, this.a.getGroup(), this._deceptiveMode);
        this.e.broadcastPacket(exFishingStartCombat);
        this.e.sendPacket((IStaticPacket)SystemMsg.YOUVE_GOT_A_BITE);
        this.j = ThreadPoolManager.getInstance().scheduleAtFixedRate(new FishCombatTask(), 1000L, 1000L);
    }

    private void a(int n, int n2) {
        this.gY -= n;
        if (this.gY < 0) {
            this.gY = 0;
        }
        this.e.broadcastPacket(new ExFishingHpRegen(this.e, this._time, this.gY, this.gX, this.gV, this.gW, n2, this._deceptiveMode));
        this.gV = 0;
        this.gW = 0;
        if (this.gY > this.a.getHP() * 2) {
            this.gY = this.a.getHP() * 2;
            this.b(false);
        } else if (this.gY == 0) {
            this.b(true);
        }
    }

    private void b(boolean bl) {
        this.ba();
        if (bl) {
            if (!this.e.isInPeaceZone() && Rnd.chance(5)) {
                bl = false;
                this.e.sendPacket((IStaticPacket)SystemMsg.YOU_CAUGHT_SOMETHING_SMELLY_AND_SCARY_MAYBE_YOU_SHOULD_THROW_IT_BACK);
                Fishing.spawnPenaltyMonster(this.e);
            } else {
                this.e.sendPacket((IStaticPacket)SystemMsg.YOU_CAUGHT_SOMETHING);
                ItemFunctions.addItem((Playable)this.e, this.a.getId(), 1L, true);
                FishingChampionShipManager.getInstance().newFish(this.e, this.gZ);
            }
        }
        this.endFishing(bl);
    }

    public void useFishingSkill(int n, int n2, Skill.SkillType skillType) {
        if (!this.isInCombat()) {
            return;
        }
        int n3 = skillType == Skill.SkillType.REELING && !GameTimeController.getInstance().isNowNight() ? 1 : (skillType == Skill.SkillType.PUMPING && GameTimeController.getInstance().isNowNight() ? 1 : 0);
        this.gW = n3 + 1;
        if (Rnd.chance(10)) {
            this.e.sendPacket((IStaticPacket)SystemMsg.THE_FISH_HAS_RESISTED_YOUR_ATTEMPT_TO_BRING_IT_IN);
            this.gV = 0;
            this.a(0, n2);
            return;
        }
        if (this.gX == n3) {
            if (this._deceptiveMode == 0) {
                Fishing.a(this.e, n, n2, skillType, 1);
                this.gV = 1;
                this.a(n, n2);
            } else {
                Fishing.a(this.e, n, n2, skillType, 2);
                this.gV = 2;
                this.a(-n, n2);
            }
        } else if (this._deceptiveMode == 0) {
            Fishing.a(this.e, n, n2, skillType, 2);
            this.gV = 2;
            this.a(-n, n2);
        } else {
            Fishing.a(this.e, n, n2, skillType, 3);
            this.gV = 1;
            this.a(n, n2);
        }
    }

    private static void a(Player player, int n, int n2, Skill.SkillType skillType, int n3) {
        switch (n3) {
            case 1: {
                if (skillType == Skill.SkillType.PUMPING) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PUMPING_IS_SUCCESSFUL_CAUSING_S1_DAMAGE).addNumber(n));
                    if (n2 != 50) break;
                    player.sendPacket((IStaticPacket)SystemMsg.DUE_TO_YOUR_REELING_ANDOR_PUMPING_SKILL_BEING_THREE_OR_MORE_LEVELS_HIGHER_THAN_YOUR_FISHING_SKILL_A_50_DAMAGE_PENALTY_WILL_BE_APPLIED);
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PUMPING_WAS_SUCCESSFUL_MASTERY_PENALTY_S1).addNumber(n2));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_REEL_THAT_FISH_IN_CLOSER_AND_CAUSE_S1_DAMAGE).addNumber(n));
                if (n2 != 50) break;
                player.sendPacket((IStaticPacket)SystemMsg.DUE_TO_YOUR_REELING_ANDOR_PUMPING_SKILL_BEING_THREE_OR_MORE_LEVELS_HIGHER_THAN_YOUR_FISHING_SKILL_A_50_DAMAGE_PENALTY_WILL_BE_APPLIED);
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_REELING_WAS_SUCCESSFUL_MASTERY_PENALTY_S1).addNumber(n2));
                break;
            }
            case 2: {
                if (skillType == Skill.SkillType.PUMPING) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_FAILED_TO_DO_ANYTHING_WITH_THE_FISH_AND_IT_REGAINS_S1_HP).addNumber(n));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_FAILED_TO_REEL_THAT_FISH_IN_FURTHER_AND_IT_REGAINS_S1_HP).addNumber(n));
                break;
            }
            case 3: {
                if (skillType == Skill.SkillType.PUMPING) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PUMPING_IS_SUCCESSFUL_CAUSING_S1_DAMAGE).addNumber(n));
                    if (n2 != 50) break;
                    player.sendPacket((IStaticPacket)SystemMsg.DUE_TO_YOUR_REELING_ANDOR_PUMPING_SKILL_BEING_THREE_OR_MORE_LEVELS_HIGHER_THAN_YOUR_FISHING_SKILL_A_50_DAMAGE_PENALTY_WILL_BE_APPLIED);
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PUMPING_WAS_SUCCESSFUL_MASTERY_PENALTY_S1).addNumber(n2));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_REEL_THAT_FISH_IN_CLOSER_AND_CAUSE_S1_DAMAGE).addNumber(n));
                if (n2 != 50) break;
                player.sendPacket((IStaticPacket)SystemMsg.DUE_TO_YOUR_REELING_ANDOR_PUMPING_SKILL_BEING_THREE_OR_MORE_LEVELS_HIGHER_THAN_YOUR_FISHING_SKILL_A_50_DAMAGE_PENALTY_WILL_BE_APPLIED);
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_REELING_WAS_SUCCESSFUL_MASTERY_PENALTY_S1).addNumber(n2));
                break;
            }
        }
    }

    public static void spawnPenaltyMonster(Player player) {
        int n = 18319 + Math.min(player.getLevel() / 11, 7);
        MonsterInstance monsterInstance = new MonsterInstance(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(n));
        monsterInstance.setSpawnedLoc(Location.findPointToStay(player, 100, 120));
        monsterInstance.setReflection(player.getReflection());
        monsterInstance.setHeading(player.getHeading() - 32768);
        monsterInstance.spawnMe(monsterInstance.getSpawnedLoc());
        monsterInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, player, Rnd.get(1, 100));
    }

    public static int getRandomFishType(int n, int n2, int n3) {
        int n4 = Rnd.get(100);
        return switch (n) {
            case 7807 -> {
                if (n4 <= 54) {
                    yield 5;
                }
                if (n4 <= 77) {
                    yield 4;
                }
                yield 6;
            }
            case 7808 -> {
                if (n4 <= 54) {
                    yield 4;
                }
                if (n4 <= 77) {
                    yield 6;
                }
                yield 5;
            }
            case 7809 -> {
                if (n4 <= 54) {
                    yield 6;
                }
                if (n4 <= 77) {
                    yield 5;
                }
                yield 4;
            }
            case 8486 -> {
                if (n4 <= 33) {
                    yield 4;
                }
                if (n4 <= 66) {
                    yield 5;
                }
                yield 6;
            }
            case 7610, 7611, 7612, 7613, 8496, 8497, 8498, 8499, 8500, 8501, 8502, 8503, 8504 -> 3;
            case 8548 -> {
                if (n4 <= 32) {
                    yield 1;
                }
                if (n4 <= 64) {
                    yield 2;
                }
                if (n4 <= 96) {
                    yield 0;
                }
                if (n3 == 4 && n2 > 19) {
                    yield 10;
                }
                yield 0;
            }
            case 6519, 6520, 6521, 8505, 8507 -> {
                if (n4 <= 54) {
                    yield 1;
                }
                if (n4 <= 74) {
                    yield 0;
                }
                if (n4 <= 94) {
                    yield 2;
                }
                yield 3;
            }
            case 6522, 6523, 6524, 8508, 8510 -> {
                if (n4 <= 54) {
                    yield 0;
                }
                if (n4 <= 74) {
                    yield 1;
                }
                if (n4 <= 94) {
                    yield 2;
                }
                yield 3;
            }
            case 6525, 6526, 6527, 8511, 8513 -> {
                if (n4 <= 55) {
                    yield 2;
                }
                if (n4 <= 74) {
                    yield 1;
                }
                if (n4 <= 94) {
                    yield 0;
                }
                yield 3;
            }
            case 8484 -> {
                if (n4 <= 33) {
                    yield 0;
                }
                if (n4 <= 66) {
                    yield 1;
                }
                yield 2;
            }
            case 8506 -> {
                if (n4 <= 54) {
                    yield 8;
                }
                if (n4 <= 77) {
                    yield 7;
                }
                yield 9;
            }
            case 8509 -> {
                if (n4 <= 54) {
                    yield 7;
                }
                if (n4 <= 77) {
                    yield 9;
                }
                yield 8;
            }
            case 8512 -> {
                if (n4 <= 54) {
                    yield 9;
                }
                if (n4 <= 77) {
                    yield 8;
                }
                yield 7;
            }
            case 8485 -> {
                if (n4 <= 33) {
                    yield 7;
                }
                if (n4 <= 66) {
                    yield 8;
                }
                yield 9;
            }
            default -> 1;
        };
    }

    public static int getRandomFishLvl(Player player) {
        int n;
        int n2 = 0;
        Effect effect = player.getEffectList().getEffectByStackType("fishPot");
        n2 = effect != null ? (int)effect.getSkill().getPower() : player.getSkillLevel(1315);
        if (n2 <= 0) {
            return 1;
        }
        int n3 = Rnd.get(100);
        if (n3 < 50) {
            n = n2;
        } else if (n3 <= 85) {
            n = n2 - 1;
            if (n <= 0) {
                n = 1;
            }
        } else {
            n = n2 + 1;
        }
        n = Math.min(27, Math.max(1, n));
        return n;
    }

    public static int getFishGroup(int n) {
        switch (n) {
            case 7807: 
            case 7808: 
            case 7809: 
            case 8486: {
                return 0;
            }
            case 8485: 
            case 8506: 
            case 8509: 
            case 8512: {
                return 2;
            }
        }
        return 1;
    }

    public static int getLureGrade(int n) {
        switch (n) {
            case 6519: 
            case 6522: 
            case 6525: 
            case 8505: 
            case 8508: 
            case 8511: {
                return 0;
            }
            case 6520: 
            case 6523: 
            case 6526: 
            case 7610: 
            case 7611: 
            case 7612: 
            case 7613: 
            case 7807: 
            case 7808: 
            case 7809: 
            case 8484: 
            case 8485: 
            case 8486: 
            case 8496: 
            case 8497: 
            case 8498: 
            case 8499: 
            case 8500: 
            case 8501: 
            case 8502: 
            case 8503: 
            case 8504: 
            case 8506: 
            case 8509: 
            case 8512: 
            case 8548: {
                return 1;
            }
            case 6521: 
            case 6524: 
            case 6527: 
            case 8507: 
            case 8510: 
            case 8513: {
                return 2;
            }
        }
        return -1;
    }

    public static boolean isNightLure(int n) {
        switch (n) {
            case 8505: 
            case 8508: 
            case 8511: {
                return true;
            }
            case 8496: 
            case 8497: 
            case 8498: 
            case 8499: 
            case 8500: 
            case 8501: 
            case 8502: 
            case 8503: 
            case 8504: {
                return true;
            }
            case 8506: 
            case 8509: 
            case 8512: {
                return true;
            }
            case 8510: 
            case 8513: {
                return true;
            }
            case 8485: {
                return true;
            }
        }
        return false;
    }

    protected class LookingForFishTask
    extends RunnableImpl {
        private long bd;

        protected LookingForFishTask() {
            this.bd = System.currentTimeMillis() + (long)Fishing.this.a.getWaitTime() + 10000L;
        }

        @Override
        public void runImpl() throws Exception {
            if (System.currentTimeMillis() >= this.bd) {
                Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THE_BAIT_HAS_BEEN_LOST_BECAUSE_THE_FISH_GOT_AWAY);
                Fishing.this.ba();
                Fishing.this.endFishing(false);
                return;
            }
            if (!GameTimeController.getInstance().isNowNight() && Fishing.isNightLure(Fishing.this.gZ)) {
                Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THE_BAIT_HAS_BEEN_LOST_BECAUSE_THE_FISH_GOT_AWAY);
                Fishing.this.ba();
                Fishing.this.endFishing(false);
                return;
            }
            int n = Rnd.get(1000);
            if (Fishing.this.a.getFishGuts() > n) {
                Fishing.this.ba();
                Fishing.this.bc();
            }
        }
    }

    private class FishCombatTask
    extends RunnableImpl {
        private FishCombatTask() {
        }

        @Override
        public void runImpl() throws Exception {
            if (Fishing.this.gY >= Fishing.this.a.getHP() * 2) {
                Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.YOUR_BAIT_WAS_STOLEN_BY_THAT_FISH);
                Fishing.this.b(false);
            } else if (Fishing.this._time <= 0) {
                Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THAT_FISH_IS_MORE_DETERMINED_THAN_YOU_ARE__IT_SPIT_THE_HOOK);
                Fishing.this.b(false);
            } else {
                --Fishing.this._time;
                if (Fishing.this.gX == 1 && Fishing.this._deceptiveMode == 0 || Fishing.this.gX == 0 && Fishing.this._deceptiveMode == 1) {
                    Fishing.this.gY += Fishing.this.a.getHpRegen();
                }
                if (Fishing.this.gU == 0) {
                    Fishing.this.gU = 1;
                    if (Rnd.chance(30)) {
                        int n = Fishing.this.gX = Fishing.this.gX == 0 ? 1 : 0;
                    }
                    if (Fishing.this.a.getGroup() == 2 && Rnd.chance(10)) {
                        Fishing.this._deceptiveMode = Fishing.this._deceptiveMode == 0 ? 1 : 0;
                    }
                } else {
                    --Fishing.this.gU;
                }
                ExFishingHpRegen exFishingHpRegen = new ExFishingHpRegen(Fishing.this.e, Fishing.this._time, Fishing.this.gY, Fishing.this.gX, 0, Fishing.this.gW, 0, Fishing.this._deceptiveMode);
                if (Fishing.this.gW != 0) {
                    Fishing.this.e.broadcastPacket(exFishingHpRegen);
                } else {
                    Fishing.this.e.sendPacket((IStaticPacket)exFishingHpRegen);
                }
            }
        }
    }
}
