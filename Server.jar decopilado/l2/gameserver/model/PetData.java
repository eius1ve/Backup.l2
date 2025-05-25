/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 */
package l2.gameserver.model;

import gnu.trove.TIntIntHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.Summon;
import l2.gameserver.tables.SkillTable;

public class PetData {
    private int _id;
    private int _level;
    private int hw;
    private int hx;
    private int hy;
    private int hz;
    private int hA;
    private int hB;
    private int hC;
    private int _hp;
    private int hD;
    private int hE;
    private int hF;
    private long _exp;
    private int _accuracy;
    private int _evasion;
    private int hG;
    private int hH;
    private int hI;
    private int _castSpeed;
    private int hJ;
    private int hK;
    private int hL;
    private int b;
    private int hM;
    private int hN;
    private int hO;
    private double u;
    private boolean bx;
    private boolean by;
    private boolean bz;
    private boolean bA;
    private boolean bB;
    private boolean bC;
    private boolean bD;
    private boolean bE;
    private boolean bF;
    private boolean bG;
    private boolean bH;
    private List<SkillLearn> aT = Collections.emptyList();
    private TIntIntHashMap e = new TIntIntHashMap();
    public static final int WYVERN_ID = 12621;
    public static final int STRIDER_WIND_ID = 12526;
    public static final int SIN_EATER_ID = 12564;

    public PetData() {
    }

    public PetData(int n, int n2, MultiValueSet<String> multiValueSet) {
        this.setID(n);
        this.setLevel(n2);
        this.setExp(multiValueSet.getLong("exp", 0L));
        this.setHP(multiValueSet.getInteger("hp", 1));
        this.setMP(multiValueSet.getInteger("mp", 1));
        this.setHpRegen(multiValueSet.getInteger("hp_regen", 1));
        this.setMpRegen(multiValueSet.getInteger("mp_regen", 1));
        this.setPAtk(multiValueSet.getInteger("p_atk", 0));
        this.setPDef(multiValueSet.getInteger("p_def", 0));
        this.setMAtk(multiValueSet.getInteger("m_atk", 0));
        this.setMDef(multiValueSet.getInteger("m_def", 0));
        this.setAccuracy(multiValueSet.getInteger("accuracy", 0));
        this.setEvasion(multiValueSet.getInteger("evasion", 0));
        this.setCritical(multiValueSet.getInteger("critical", 0));
        this.setSpeed(multiValueSet.getInteger("speed", 1));
        this.setAtkSpeed(multiValueSet.getInteger("atk_speed", 0));
        this.setCastSpeed(multiValueSet.getInteger("cast_speed", 0));
        this.setFeedMax(multiValueSet.getInteger("feed_max", 0));
        this.setFeedNormal(multiValueSet.getInteger("feed_normal", 0));
        this.setFeedBattle(multiValueSet.getInteger("feed_battle", 0));
        this.setMaxLoad(multiValueSet.getInteger("max_load", 0));
        this.setSoulshots(multiValueSet.getInteger("soulshots", 2));
        this.setSpiritshots(multiValueSet.getInteger("spiritshots", 2));
        this.setControlItemId(multiValueSet.getInteger("control_item_id", 0));
        this.setFoodId(multiValueSet.getInteger("food_item_id", 0));
        this.setMinLevel(multiValueSet.getInteger("min_level", 1));
        this.setAddFed(multiValueSet.getInteger("add_feed", 12));
        this.setMountable(multiValueSet.getBool("is_mountable", false));
        this.setBabyPet(multiValueSet.getBool("is_baby_pet", false));
        this.setWolf(multiValueSet.getBool("is_wolf", false));
        this.setGreatWolf(multiValueSet.getBool("is_great_wolf", false));
        this.setHatchling(multiValueSet.getBool("is_hatchling", false));
        this.setImprovedBabyPet(multiValueSet.getBool("is_improved_baby_pet", false));
        this.setStrider(multiValueSet.getBool("is_strider", false));
        this.setWyvern(multiValueSet.getBool("is_wyvern", false));
        this.setImprovedBabyBuffalo(multiValueSet.getBool("is_improved_baby_buffalo", false));
        this.setImprovedBabyCougar(multiValueSet.getBool("is_improved_baby_cougar", false));
        this.setImprovedBabyKookaburra(multiValueSet.getBool("is_improved_baby_kookaburra", false));
        this.setExpPenalty(multiValueSet.getDouble("exp_penalty", 0.0));
    }

    public int getFeedBattle() {
        return this.hx;
    }

    public void setFeedBattle(int n) {
        this.hx = n;
    }

    public int getFeedNormal() {
        return this.hy;
    }

    public void setFeedNormal(int n) {
        this.hy = n;
    }

    public int getHP() {
        return this._hp;
    }

    public void setHP(int n) {
        this._hp = n;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int n) {
        this._id = n;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int n) {
        this._level = n;
    }

    public int getMAtk() {
        return this.hB;
    }

    public void setMAtk(int n) {
        this.hB = n;
    }

    public int getFeedMax() {
        return this.hw;
    }

    public void setFeedMax(int n) {
        this.hw = n;
    }

    public int getMDef() {
        return this.hC;
    }

    public void setMDef(int n) {
        this.hC = n;
    }

    public long getExp() {
        return this._exp;
    }

    public void setExp(long l) {
        this._exp = l;
    }

    public int getMP() {
        return this.hD;
    }

    public void setMP(int n) {
        this.hD = n;
    }

    public int getPAtk() {
        return this.hz;
    }

    public void setPAtk(int n) {
        this.hz = n;
    }

    public int getPDef() {
        return this.hA;
    }

    public int getAccuracy() {
        return this._accuracy;
    }

    public int getEvasion() {
        return this._evasion;
    }

    public int getCritical() {
        return this.hG;
    }

    public int getSpeed() {
        return this.hH;
    }

    public int getAtkSpeed() {
        return this.hI;
    }

    public int getCastSpeed() {
        return this._castSpeed;
    }

    public int getMaxLoad() {
        return this.hJ != 0 ? this.hJ : this._level * 300;
    }

    public void setPDef(int n) {
        this.hA = n;
    }

    public int getHpRegen() {
        return this.hE;
    }

    public void setHpRegen(int n) {
        this.hE = n;
    }

    public int getMpRegen() {
        return this.hF;
    }

    public void setMpRegen(int n) {
        this.hF = n;
    }

    public void setAccuracy(int n) {
        this._accuracy = n;
    }

    public void setEvasion(int n) {
        this._evasion = n;
    }

    public void setCritical(int n) {
        this.hG = n;
    }

    public void setSpeed(int n) {
        this.hH = n;
    }

    public void setAtkSpeed(int n) {
        this.hI = n;
    }

    public void setCastSpeed(int n) {
        this._castSpeed = n;
    }

    public void setMaxLoad(int n) {
        this.hJ = n;
    }

    public int getControlItemId() {
        return this.hK;
    }

    public void setControlItemId(int n) {
        this.hK = n;
    }

    public int getFoodId() {
        return this.hL;
    }

    public void setFoodId(int n) {
        this.hL = n;
    }

    public int getMinLevel() {
        return this.b;
    }

    public void setMinLevel(int n) {
        this.b = n;
    }

    public int getAddFed() {
        return this.hM;
    }

    public void setAddFed(int n) {
        this.hM = n;
    }

    public double getExpPenalty() {
        return this.u;
    }

    public void setExpPenalty(double d) {
        this.u = d;
    }

    public boolean isMountable() {
        return this.bx;
    }

    public void setMountable(boolean bl) {
        this.bx = bl;
    }

    public void setSoulshots(int n) {
        this.hN = n;
    }

    public int getSoulshots() {
        return this.hN;
    }

    public void setSpiritshots(int n) {
        this.hO = n;
    }

    public int getSpiritshots() {
        return this.hO;
    }

    public boolean isBabyPet() {
        return this.by;
    }

    public void setBabyPet(boolean bl) {
        this.by = bl;
    }

    public boolean isWolf() {
        return this.bz;
    }

    public void setWolf(boolean bl) {
        this.bz = bl;
    }

    public boolean isWyvern() {
        return this.bE;
    }

    public void setWyvern(boolean bl) {
        this.bE = bl;
    }

    public boolean isGreatWolf() {
        return this.bA;
    }

    public void setGreatWolf(boolean bl) {
        this.bA = bl;
    }

    public boolean isHatchling() {
        return this.bB;
    }

    public void setHatchling(boolean bl) {
        this.bB = bl;
    }

    public boolean isImprovedBabyPet() {
        return this.bC;
    }

    public void setImprovedBabyPet(boolean bl) {
        this.bC = bl;
    }

    public boolean isImprovedBabyBuffalo() {
        return this.bF;
    }

    public void setImprovedBabyBuffalo(boolean bl) {
        this.bF = bl;
    }

    public boolean isImprovedBabyKookaburra() {
        return this.bG;
    }

    public void setImprovedBabyKookaburra(boolean bl) {
        this.bG = bl;
    }

    public boolean isImprovedBabyCougar() {
        return this.bH;
    }

    public void setImprovedBabyCougar(boolean bl) {
        this.bH = bl;
    }

    public boolean isStrider() {
        return this.bD;
    }

    public void setStrider(boolean bl) {
        this.bD = bl;
    }

    public List<SkillLearn> getSkillLearns() {
        return this.aT;
    }

    public void setSkillLearns(List<SkillLearn> list) {
        this.aT = list;
    }

    public TIntIntHashMap getActionId2SkillId() {
        return this.e;
    }

    public void setActionId2SkillId(TIntIntHashMap tIntIntHashMap) {
        this.e = tIntIntHashMap;
    }

    public int getAvailableLevel(Summon summon, int n) {
        List<SkillLearn> list = this.getSkillLearns();
        if (list == null) {
            return 0;
        }
        int n2 = 0;
        for (SkillLearn skillLearn : list) {
            if (skillLearn.getId() != n) continue;
            if (skillLearn.getLevel() == 0) {
                int n3;
                if (summon.getLevel() < 70) {
                    n2 = summon.getLevel() / 10;
                    if (n2 <= 0) {
                        n2 = 1;
                    }
                } else {
                    n2 = 7 + (summon.getLevel() - 70) / 5;
                }
                if (n2 <= (n3 = SkillTable.getInstance().getMaxLevel(skillLearn.getId()))) break;
                n2 = n3;
                break;
            }
            if (skillLearn.getMinLevel() > summon.getLevel() || skillLearn.getLevel() <= n2) continue;
            n2 = skillLearn.getLevel();
        }
        return n2;
    }

    public List<Integer> getAvailableSkills(Summon summon) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        List<SkillLearn> list = this.getSkillLearns();
        if (list == null) {
            return arrayList;
        }
        for (SkillLearn skillLearn : list) {
            if (skillLearn == null || arrayList.contains(skillLearn.getId())) continue;
            arrayList.add(skillLearn.getId());
        }
        return arrayList;
    }
}
