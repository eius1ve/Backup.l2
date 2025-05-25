/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import java.util.Collection;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;

public class Grade {
    public static Grade NONE;
    public static Grade D_GRADE;
    public static Grade C_GRADE;
    public static Grade B_GRADE;
    public static Grade A_GRADE;
    public static Grade S_GRADE;
    private final String gv;
    private final int GJ;
    private final int GK;
    private final int GL;
    private final int GM;
    private final double aN;
    private final double aO;
    private final double aP;
    private final double aQ;
    private final double aR;
    private final double aS;
    private final double aT;
    private final double aU;
    private final double aV;
    private final double aW;
    private final double aX;
    private final double aY;
    private final int GN;
    private final int GO;

    public Grade(String string, int n, int n2, int n3, int n4, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, int n5) {
        this.gv = string;
        this.GJ = n;
        this.GK = n2;
        this.GL = n3;
        this.GM = n4;
        this.GO = n5;
        this.aN = d;
        this.aO = d2;
        this.aP = d3;
        this.aQ = d4;
        this.aR = d5;
        this.aS = d6;
        this.aT = d7;
        this.aU = d8;
        this.aV = d9;
        this.aW = d10;
        this.aX = d11;
        this.aY = d12;
        switch (string) {
            case "NONE": {
                NONE = this;
                this.GN = Config.WEAING_NON_GRADE_PRICE;
                break;
            }
            case "D": {
                D_GRADE = this;
                this.GN = Config.WEAING_D_GRADE_PRICE;
                break;
            }
            case "C": {
                C_GRADE = this;
                this.GN = Config.WEAING_C_GRADE_PRICE;
                break;
            }
            case "B": {
                B_GRADE = this;
                this.GN = Config.WEAING_B_GRADE_PRICE;
                break;
            }
            case "A": {
                A_GRADE = this;
                this.GN = Config.WEAING_A_GRADE_PRICE;
                break;
            }
            case "S": {
                S_GRADE = this;
                this.GN = Config.WEAING_S_GRADE_PRICE;
                break;
            }
            default: {
                this.GN = Config.WEAING_NON_GRADE_PRICE;
            }
        }
    }

    public double getShieldDefence() {
        return this.aN;
    }

    public double getMagicDefence() {
        return this.aO;
    }

    public double getPhysicDefence() {
        return this.aP;
    }

    public double getMagicAttack() {
        return this.aQ;
    }

    public double getPhysicAttack() {
        return this.aR;
    }

    public double getPhysicAttackBow() {
        return this.aS;
    }

    public double getPowerAttackDoubleSword() {
        return this.aT;
    }

    public int getCry() {
        return this.GJ;
    }

    public int getCryMod() {
        return this.GK;
    }

    public int ordinal() {
        return this.GL;
    }

    public int gradeOrd() {
        return this.GM;
    }

    public String getId() {
        return this.gv;
    }

    public double getPhysicAttackSpeed() {
        return this.aU;
    }

    public double getMagicAttackSpeed() {
        return this.aV;
    }

    public double getMaxHp() {
        return this.aW;
    }

    public double getMaxMp() {
        return this.aX;
    }

    public double getMaxCp() {
        return this.aY;
    }

    public int getWearPreviewPrice() {
        return this.GN;
    }

    public static Collection<Grade> values() {
        return CrystalGradeDataHolder.getInstance().getAllGrades();
    }

    public int getExperienceLevel() {
        return this.GO;
    }
}
