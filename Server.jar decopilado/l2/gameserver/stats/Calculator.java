/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncOwner;

public final class Calculator {
    private Func[] a;
    private double ap;
    private double aq;
    public final Stats _stat;
    public final Creature _character;

    public Calculator(Stats stats, Creature creature) {
        this._stat = stats;
        this._character = creature;
        this.a = Func.EMPTY_FUNC_ARRAY;
    }

    public int size() {
        return this.a.length;
    }

    public void addFunc(Func func) {
        this.a = ArrayUtils.add(this.a, func);
        ArrayUtils.eqSort((Comparable[])this.a);
    }

    public void removeFunc(Func func) {
        this.a = ArrayUtils.remove(this.a, func);
        if (this.a.length == 0) {
            this.a = Func.EMPTY_FUNC_ARRAY;
        } else {
            ArrayUtils.eqSort((Comparable[])this.a);
        }
    }

    public void removeOwner(Object object) {
        Func[] funcArray;
        for (Func func : funcArray = this.a) {
            if (func.owner != object) continue;
            this.removeFunc(func);
        }
    }

    public void calc(Env env) {
        Func[] funcArray = this.a;
        this.ap = env.value;
        boolean bl = false;
        for (Func func : funcArray) {
            if (func == null) continue;
            if (func.owner instanceof FuncOwner) {
                if (!((FuncOwner)func.owner).isFuncEnabled()) continue;
                if (((FuncOwner)func.owner).overrideLimits()) {
                    bl = true;
                }
            }
            if (func.getCondition() != null && !func.getCondition().test(env)) continue;
            func.calc(env);
        }
        if (!bl) {
            env.value = this._stat.validate(env.value);
        }
        if (env.value != this.aq) {
            double d = this.aq;
            this.aq = env.value;
        }
    }

    public Func[] getFunctions() {
        return this.a;
    }

    public double getBase() {
        return this.ap;
    }

    public double getLast() {
        return this.aq;
    }
}
