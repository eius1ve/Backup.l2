/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

import bosses.EpicBossState;
import handler.admincommands.AdminBossStatus;

static class AdminBossStatus.1 {
    static final /* synthetic */ int[] $SwitchMap$handler$admincommands$AdminBossStatus$Commands;
    static final /* synthetic */ int[] $SwitchMap$bosses$EpicBossState$State;

    static {
        $SwitchMap$bosses$EpicBossState$State = new int[EpicBossState.State.values().length];
        try {
            AdminBossStatus.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.ALIVE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminBossStatus.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.NOTSPAWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminBossStatus.1.$SwitchMap$bosses$EpicBossState$State[EpicBossState.State.DEAD.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$handler$admincommands$AdminBossStatus$Commands = new int[AdminBossStatus.Commands.values().length];
        try {
            AdminBossStatus.1.$SwitchMap$handler$admincommands$AdminBossStatus$Commands[AdminBossStatus.Commands.admin_boss_status.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
