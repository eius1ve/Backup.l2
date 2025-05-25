/*
 * Decompiled with CFR 0.152.
 */
package services;

import services.Buffer;

static class Buffer.2 {
    static final /* synthetic */ int[] $SwitchMap$services$Buffer$BuffTarget;

    static {
        $SwitchMap$services$Buffer$BuffTarget = new int[Buffer.BuffTarget.values().length];
        try {
            Buffer.2.$SwitchMap$services$Buffer$BuffTarget[Buffer.BuffTarget.BUFF_PLAYER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Buffer.2.$SwitchMap$services$Buffer$BuffTarget[Buffer.BuffTarget.BUFF_PET.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
