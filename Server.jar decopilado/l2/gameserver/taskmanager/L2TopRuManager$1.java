/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.gameserver.taskmanager.L2TopRuManager;

static class L2TopRuManager.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$taskmanager$L2TopRuManager$L2TopRuVoteType;

    static {
        $SwitchMap$l2$gameserver$taskmanager$L2TopRuManager$L2TopRuVoteType = new int[L2TopRuManager.L2TopRuVoteType.values().length];
        try {
            L2TopRuManager.1.$SwitchMap$l2$gameserver$taskmanager$L2TopRuManager$L2TopRuVoteType[L2TopRuManager.L2TopRuVoteType.WEB.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            L2TopRuManager.1.$SwitchMap$l2$gameserver$taskmanager$L2TopRuManager$L2TopRuVoteType[L2TopRuManager.L2TopRuVoteType.SMS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
