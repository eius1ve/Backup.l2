/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.funcs;

import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.EFunction;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncMul;

final class EFunction.4
extends EFunction {
    @Override
    public Func create(Stats stats, int n, Object object, double d) {
        return new FuncMul(stats, n, object, d);
    }
}
