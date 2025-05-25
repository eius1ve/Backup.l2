/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.CompetitionType;

static class Competition.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$entity$oly$CompetitionType;

    static {
        $SwitchMap$l2$gameserver$model$entity$oly$CompetitionType = new int[CompetitionType.values().length];
        try {
            Competition.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_FREE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Competition.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_INDIVIDUAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Competition.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_TYPE_INDIVIDUAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Competition.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.TEAM_CLASS_FREE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
