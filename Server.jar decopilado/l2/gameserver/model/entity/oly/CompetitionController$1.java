/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.model.entity.oly.CompetitionType;

static class CompetitionController.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$entity$oly$CompetitionType;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$entity$oly$CompetitionState;

    static {
        $SwitchMap$l2$gameserver$model$entity$oly$CompetitionState = new int[CompetitionState.values().length];
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionState[CompetitionState.STAND_BY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionState[CompetitionState.PLAYING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionState[CompetitionState.FINISH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$model$entity$oly$CompetitionType = new int[CompetitionType.values().length];
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_INDIVIDUAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_TYPE_INDIVIDUAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.CLASS_FREE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CompetitionController.1.$SwitchMap$l2$gameserver$model$entity$oly$CompetitionType[CompetitionType.TEAM_CLASS_FREE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
