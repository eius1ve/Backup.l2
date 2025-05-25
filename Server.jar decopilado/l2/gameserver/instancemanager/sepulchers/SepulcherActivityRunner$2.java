/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers;

import l2.gameserver.instancemanager.sepulchers.SepulcherActivityRunner;

static class SepulcherActivityRunner.2 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State;

    static {
        $SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State = new int[SepulcherActivityRunner.State.values().length];
        try {
            SepulcherActivityRunner.2.$SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State[SepulcherActivityRunner.State.REGISTRATION_OPEN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            SepulcherActivityRunner.2.$SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State[SepulcherActivityRunner.State.ACTIVE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            SepulcherActivityRunner.2.$SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State[SepulcherActivityRunner.State.COLLAPSE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            SepulcherActivityRunner.2.$SwitchMap$l2$gameserver$instancemanager$sepulchers$SepulcherActivityRunner$State[SepulcherActivityRunner.State.MOB_DESPAWN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
