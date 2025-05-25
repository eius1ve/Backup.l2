/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge;

import l2.gameserver.model.entity.residence.ResidenceType;

static class Clan.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$entity$residence$ResidenceType;

    static {
        $SwitchMap$l2$gameserver$model$entity$residence$ResidenceType = new int[ResidenceType.values().length];
        try {
            Clan.1.$SwitchMap$l2$gameserver$model$entity$residence$ResidenceType[ResidenceType.Castle.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Clan.1.$SwitchMap$l2$gameserver$model$entity$residence$ResidenceType[ResidenceType.ClanHall.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
