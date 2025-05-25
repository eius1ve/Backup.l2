/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.TeamType
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.gameserver.model.base.TeamType;

static class PvPEvent.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$TeamType;
    static final /* synthetic */ int[] $SwitchMap$events$TvT2$PvPEvent$PvPEventRule;
    static final /* synthetic */ int[] $SwitchMap$events$TvT2$PvPEvent$PvPEventState;
    static final /* synthetic */ int[] $SwitchMap$events$TvT2$PvPEvent$RegisrationState;

    static {
        $SwitchMap$events$TvT2$PvPEvent$RegisrationState = new int[PvPEvent.RegisrationState.values().length];
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$RegisrationState[PvPEvent.RegisrationState.ANNOUNCE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$RegisrationState[PvPEvent.RegisrationState.REQUEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$RegisrationState[PvPEvent.RegisrationState.CAPCHA.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$RegisrationState[PvPEvent.RegisrationState.MORPH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$events$TvT2$PvPEvent$PvPEventState = new int[PvPEvent.PvPEventState.values().length];
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.STANDBY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.REGISTRATION.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.PORTING_TO.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.PREPARE_TO.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.COMPETITION.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.WINNER.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.PREPARE_FROM.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventState[PvPEvent.PvPEventState.PORTING_FROM.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$events$TvT2$PvPEvent$PvPEventRule = new int[PvPEvent.PvPEventRule.values().length];
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventRule[PvPEvent.PvPEventRule.TVT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventRule[PvPEvent.PvPEventRule.CTF.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$events$TvT2$PvPEvent$PvPEventRule[PvPEvent.PvPEventRule.DM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$model$base$TeamType = new int[TeamType.values().length];
        try {
            PvPEvent.1.$SwitchMap$l2$gameserver$model$base$TeamType[TeamType.RED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PvPEvent.1.$SwitchMap$l2$gameserver$model$base$TeamType[TeamType.BLUE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
