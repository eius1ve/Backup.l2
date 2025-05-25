/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.Config;
import l2.gameserver.model.DeathPenalty;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Experience;

public class SubClass {
    public static final int BASECLASS = 0;
    public static final int DUALCLASS = 0;
    public static final int SUBCLASS = 0;
    private int jy = 0;
    private long _exp;
    private long bL = Experience.LEVEL[Config.ALT_LEVEL_AFTER_GET_SUBCLASS];
    private long bM = Experience.LEVEL[Experience.LEVEL.length - 1];
    private int _sp = 0;
    private int _level = Config.ALT_LEVEL_AFTER_GET_SUBCLASS;
    private double w = 1.0;
    private double x = 1.0;
    private double y = 1.0;
    private boolean T = false;
    private boolean cy = false;
    private DeathPenalty a;

    public SubClass() {
        this._exp = Math.max(this.bL, Experience.LEVEL[this._level]);
    }

    public int getClassId() {
        return this.jy;
    }

    public long getExp() {
        return this._exp;
    }

    public long getMaxExp() {
        return this.bM;
    }

    public void addExp(long l) {
        this.setExp(this._exp + l);
    }

    public long getSp() {
        return Math.min(this._sp, Integer.MAX_VALUE);
    }

    public void addSp(long l) {
        this.setSp((long)this._sp + l);
    }

    public int getLevel() {
        return this._level;
    }

    public void setClassId(int n) {
        this.jy = n;
    }

    public void setExp(long l) {
        l = Math.max(l, this.bL);
        this._exp = l = Math.min(l, this.bM);
        this._level = Experience.getLevel(this._exp);
    }

    public void setSp(long l) {
        l = Math.max(l, 0L);
        l = Math.min(l, Integer.MAX_VALUE);
        this._sp = (int)l;
    }

    public void setHp(double d) {
        this.w = d;
    }

    public double getHp() {
        return this.w;
    }

    public void setMp(double d) {
        this.x = d;
    }

    public double getMp() {
        return this.x;
    }

    public void setCp(double d) {
        this.y = d;
    }

    public double getCp() {
        return this.y;
    }

    public void setActive(boolean bl) {
        this.T = bl;
    }

    public boolean isActive() {
        return this.T;
    }

    public void setBase(boolean bl) {
        this.cy = bl;
        this.bL = Experience.LEVEL[this.cy ? 1 : Config.ALT_LEVEL_AFTER_GET_SUBCLASS];
        this.bM = Experience.LEVEL[(this.cy ? Experience.getMaxLevel() : Experience.getMaxSubLevel()) + 1] - 1L;
    }

    public boolean isBase() {
        return this.cy;
    }

    public DeathPenalty getDeathPenalty(Player player) {
        if (this.a == null) {
            this.a = new DeathPenalty(player, 0);
        }
        return this.a;
    }

    public void setDeathPenalty(DeathPenalty deathPenalty) {
        this.a = deathPenalty;
    }

    public String toString() {
        return ClassId.VALUES[this.jy].toString() + " " + this._level;
    }
}
