/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.funcs;

import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.EFunction;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncSub;

final class EFunction.3
extends EFunction {
    @Override
    public Func create(Stats stats, int n, Object object, double d) {
        return new FuncSub(stats, n, object, d);
    }
}
