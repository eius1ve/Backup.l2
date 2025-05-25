/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.base.AcquireType;

static class RequestAquireSkill.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$AcquireType;

    static {
        $SwitchMap$l2$gameserver$model$base$AcquireType = new int[AcquireType.values().length];
        try {
            RequestAquireSkill.1.$SwitchMap$l2$gameserver$model$base$AcquireType[AcquireType.NORMAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestAquireSkill.1.$SwitchMap$l2$gameserver$model$base$AcquireType[AcquireType.FISHING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestAquireSkill.1.$SwitchMap$l2$gameserver$model$base$AcquireType[AcquireType.CERTIFICATION.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestAquireSkill.1.$SwitchMap$l2$gameserver$model$base$AcquireType[AcquireType.CLAN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
