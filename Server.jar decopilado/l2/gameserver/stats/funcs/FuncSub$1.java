/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.funcs;

import l2.gameserver.stats.Stats;

static class FuncSub.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$stats$Stats;

    static {
        $SwitchMap$l2$gameserver$stats$Stats = new int[Stats.values().length];
        try {
            FuncSub.1.$SwitchMap$l2$gameserver$stats$Stats[Stats.MAX_CP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FuncSub.1.$SwitchMap$l2$gameserver$stats$Stats[Stats.MAX_HP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FuncSub.1.$SwitchMap$l2$gameserver$stats$Stats[Stats.MAX_MP.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
