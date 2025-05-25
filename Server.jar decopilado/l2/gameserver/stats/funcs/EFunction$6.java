/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.funcs;

import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.EFunction;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncEnchant;

final class EFunction.6
extends EFunction {
    @Override
    public Func create(Stats stats, int n, Object object, double d) {
        return new FuncEnchant(stats, n, object, d);
    }
}
