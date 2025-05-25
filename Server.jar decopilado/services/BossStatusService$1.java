/*
 * Decompiled with CFR 0.152.
 */
package services;

import bosses.EpicBossState;

static class BossStatusService.1 {
    static final /* synthetic */ int[] $SwitchMap$bosses$EpicBossState$State;

    static {
        $SwitchMap$bosses$EpicBossState$State = new int[EpicBossState.State.values().length];
        try {
            BossStatusService.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.ALIVE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BossStatusService.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.NOTSPAWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            BossStatusService.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.DEAD.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
