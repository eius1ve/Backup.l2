/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.Player;
import l2.gameserver.tables.SkillTable;

public final class EnchantSkillLearn {
    private final int kG;
    private final int kH;
    private final String cY;
    private final String cZ;
    private final int kI;
    private final int kJ;
    private final int kK;
    private int kL;
    private static final int[][] e = new int[][]{new int[0], {82, 92, 97, 97, 97, 97, 97, 97, 97, 97}, {80, 90, 95, 95, 95, 95, 95, 95, 95, 95}, {78, 88, 93, 93, 93, 93, 93, 93, 93, 93}, {52, 76, 86, 91, 91, 91, 91, 91, 91, 91}, {50, 74, 84, 89, 89, 89, 89, 89, 89, 89}, {48, 72, 82, 87, 87, 87, 87, 87, 87, 87}, {1, 46, 70, 80, 85, 85, 85, 85, 85, 85}, {1, 44, 68, 78, 83, 83, 83, 83, 83, 83}, {1, 42, 66, 76, 81, 81, 81, 81, 81, 81}, {1, 1, 40, 64, 74, 79, 79, 79, 79, 79}, {1, 1, 38, 62, 72, 77, 77, 77, 77, 77}, {1, 1, 36, 60, 70, 75, 75, 75, 75, 75}, {1, 1, 1, 34, 58, 68, 73, 73, 73, 73}, {1, 1, 1, 32, 56, 66, 71, 71, 71, 71}, {1, 1, 1, 30, 54, 64, 69, 69, 69, 69}, {1, 1, 1, 1, 28, 52, 62, 67, 67, 67}, {1, 1, 1, 1, 26, 50, 60, 65, 65, 65}, {1, 1, 1, 1, 24, 48, 58, 63, 63, 63}, {1, 1, 1, 1, 1, 22, 46, 56, 61, 61}, {1, 1, 1, 1, 1, 20, 44, 54, 59, 59}, {1, 1, 1, 1, 1, 18, 42, 52, 57, 57}, {1, 1, 1, 1, 1, 1, 16, 40, 50, 55}, {1, 1, 1, 1, 1, 1, 14, 38, 48, 53}, {1, 1, 1, 1, 1, 1, 12, 36, 46, 51}, {1, 1, 1, 1, 1, 1, 1, 10, 34, 44}, {1, 1, 1, 1, 1, 1, 1, 8, 32, 42}, {1, 1, 1, 1, 1, 1, 1, 6, 30, 40}, {1, 1, 1, 1, 1, 1, 1, 1, 4, 28}, {1, 1, 1, 1, 1, 1, 1, 1, 2, 26}, {1, 1, 1, 1, 1, 1, 1, 1, 2, 24}};
    private static final int[][] f = new int[][]{new int[0], {18, 28, 38, 48, 58, 82, 92, 97, 97, 97}, {1, 1, 1, 46, 56, 80, 90, 95, 95, 95}, {1, 1, 1, 1, 54, 78, 88, 93, 93, 93}, {1, 1, 1, 1, 42, 52, 76, 86, 91, 91}, {1, 1, 1, 1, 1, 50, 74, 84, 89, 89}, {1, 1, 1, 1, 1, 48, 72, 82, 87, 87}, {1, 1, 1, 1, 1, 1, 46, 70, 80, 85}, {1, 1, 1, 1, 1, 1, 44, 68, 78, 83}, {1, 1, 1, 1, 1, 1, 42, 66, 76, 81}, {1, 1, 1, 1, 1, 1, 1, 40, 64, 74}, {1, 1, 1, 1, 1, 1, 1, 38, 62, 72}, {1, 1, 1, 1, 1, 1, 1, 36, 60, 70}, {1, 1, 1, 1, 1, 1, 1, 1, 34, 58}, {1, 1, 1, 1, 1, 1, 1, 1, 32, 56}, {1, 1, 1, 1, 1, 1, 1, 1, 30, 54}};
    private static final int[][] g = new int[][]{new int[0], {51975, 352786}, {51975, 352786}, {51975, 352786}, {78435, 370279}, {78435, 370279}, {78435, 370279}, {105210, 388290}, {105210, 388290}, {105210, 388290}, {132300, 416514}, {132300, 416514}, {132300, 416514}, {159705, 435466}, {159705, 435466}, {159705, 435466}, {187425, 466445}, {187425, 466445}, {187425, 466445}, {215460, 487483}, {215460, 487483}, {215460, 487483}, {243810, 520215}, {243810, 520215}, {243810, 520215}, {272475, 542829}, {272475, 542829}, {272475, 542829}, {304500, 566426}, {304500, 566426}, {304500, 566426}};
    private static final int[][] h = new int[][]{new int[0], {93555, 635014}, {93555, 635014}, {93555, 635014}, {141183, 666502}, {141183, 666502}, {141183, 666502}, {189378, 699010}, {189378, 699010}, {189378, 699010}, {238140, 749725}, {238140, 749725}, {238140, 749725}, {287469, 896981}, {287469, 896981}, {287469, 896981}, {337365, 959540}, {337365, 959540}, {337365, 959540}, {387828, 1002821}, {387828, 1002821}, {387828, 1002821}, {438858, 1070155}, {438858, 1070155}, {438858, 1070155}, {496601, 1142010}, {496601, 1142010}, {496601, 1142010}, {561939, 1218690}, {561939, 1218690}, {561939, 1218690}};

    public EnchantSkillLearn(int n, int n2, String string, String string2, int n3, int n4, int n5) {
        this.kG = n;
        this.kH = n2;
        this.kI = n4;
        this.kJ = n5;
        this.kK = n3;
        this.cY = string.intern();
        this.cZ = string2.intern();
        this.kL = this.kJ == 15 ? 5 : 1;
    }

    public int getId() {
        return this.kG;
    }

    public int getLevel() {
        return this.kH;
    }

    public int getBaseLevel() {
        return this.kI;
    }

    public int getMinSkillLevel() {
        return this.kK;
    }

    public String getName() {
        return this.cY;
    }

    public int getCostMult() {
        return this.kL;
    }

    public int[] getCost() {
        return SkillTable.getInstance().getInfo(this.kG, 1).isOffensive() ? h[this.kH % 100] : g[this.kH % 100];
    }

    public int getRate(Player player) {
        int n = this.kH % 100;
        int n2 = Math.min(e[n].length - 1, player.getLevel() - 76);
        return this.kJ == 15 ? f[n][n2] : e[n][n2];
    }

    public int getMaxLevel() {
        return this.kJ;
    }

    public String getType() {
        return this.cZ;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.kG;
        n = 31 * n + this.kH;
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (!(object instanceof EnchantSkillLearn)) {
            return false;
        }
        EnchantSkillLearn enchantSkillLearn = (EnchantSkillLearn)object;
        return this.getId() == enchantSkillLearn.getId() && this.getLevel() == enchantSkillLearn.getLevel();
    }
}
