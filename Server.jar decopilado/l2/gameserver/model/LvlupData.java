/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public class LvlupData {
    private int hl;
    private int hm;
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;
    private double q;
    private double r;
    private double s;
    private double t;

    public double get_classHpAdd() {
        return this.l;
    }

    public void set_classHpAdd(double d) {
        this.l = d;
    }

    public double get_classHpBase() {
        return this.m;
    }

    public void set_classHpBase(double d) {
        this.m = d;
    }

    public double get_classHpModifier() {
        return this.n;
    }

    public void set_classHpModifier(double d) {
        this.n = d;
    }

    public double get_classCpAdd() {
        return this.o;
    }

    public void set_classCpAdd(double d) {
        this.o = d;
    }

    public double get_classCpBase() {
        return this.p;
    }

    public void set_classCpBase(double d) {
        this.p = d;
    }

    public double get_classCpModifier() {
        return this.q;
    }

    public void set_classCpModifier(double d) {
        this.q = d;
    }

    public int get_classid() {
        return this.hl;
    }

    public void set_classid(int n) {
        this.hl = n;
    }

    public int get_classLvl() {
        return this.hm;
    }

    public void set_classLvl(int n) {
        this.hm = n;
    }

    public double get_classMpAdd() {
        return this.r;
    }

    public void set_classMpAdd(double d) {
        this.r = d;
    }

    public double get_classMpBase() {
        return this.s;
    }

    public void set_classMpBase(double d) {
        this.s = d;
    }

    public double get_classMpModifier() {
        return this.t;
    }

    public void set_classMpModifier(double d) {
        this.t = d;
    }
}
