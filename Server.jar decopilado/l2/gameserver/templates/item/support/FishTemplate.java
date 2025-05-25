/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import l2.commons.collections.MultiValueSet;
import l2.gameserver.templates.item.support.FishGrade;
import l2.gameserver.templates.item.support.FishGroup;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FishTemplate {
    private final FishGroup a;
    private final FishGrade a;
    private final double aH;
    private final double aI;
    private final double aJ;
    private final double aK;
    private final double aL;
    private final double aM;
    private final int GC;
    private final int GD;
    private final int GE;
    private final int GF;
    private final int GG;
    private final int GH;
    private final int GI;

    public FishTemplate(MultiValueSet<String> multiValueSet) {
        this.a = multiValueSet.getEnum("group", FishGroup.class);
        this.a = multiValueSet.getEnum("grade", FishGrade.class);
        this.aH = multiValueSet.getDouble("bite_rate");
        this.aI = multiValueSet.getDouble("guts");
        this.aJ = multiValueSet.getDouble("length_rate");
        this.aK = multiValueSet.getDouble("hp_regen");
        this.aL = multiValueSet.getDouble("guts_check_probability");
        this.aM = multiValueSet.getDouble("cheating_prob");
        this.GC = multiValueSet.getInteger("item_id");
        this.GE = multiValueSet.getInteger("level");
        this.GD = multiValueSet.getInteger("hp");
        this.GF = multiValueSet.getInteger("max_length");
        this.GG = multiValueSet.getInteger("start_combat_time");
        this.GH = multiValueSet.getInteger("combat_duration");
        this.GI = multiValueSet.getInteger("guts_check_time");
    }

    public FishGroup getGroup() {
        return this.a;
    }

    public FishGrade getGrade() {
        return this.a;
    }

    public double getBiteRate() {
        return this.aH;
    }

    public double getGuts() {
        return this.aI;
    }

    public double getLengthRate() {
        return this.aJ;
    }

    public double getHpRegen() {
        return this.aK;
    }

    public double getGutsCheckProbability() {
        return this.aL;
    }

    public double getCheatingProb() {
        return this.aM;
    }

    public int getItemId() {
        return this.GC;
    }

    public int getHp() {
        return this.GD;
    }

    public int getLevel() {
        return this.GE;
    }

    public int getMaxLength() {
        return this.GF;
    }

    public int getStartCombatTime() {
        return this.GG;
    }

    public int getCombatDuration() {
        return this.GH;
    }

    public int getGutsCheckTime() {
        return this.GI;
    }
}
