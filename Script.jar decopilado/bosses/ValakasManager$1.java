/*
 * Decompiled with CFR 0.152.
 */
package bosses;

import bosses.EpicBossState;

static class ValakasManager.1 {
    static final /* synthetic */ int[] $SwitchMap$bosses$EpicBossState$State;

    static {
        $SwitchMap$bosses$EpicBossState$State = new int[EpicBossState.State.values().length];
        try {
            ValakasManager.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.ALIVE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ValakasManager.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.DEAD.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ValakasManager.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.INTERVAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
