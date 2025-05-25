/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.templates.item.WeaponTemplate;

static class StatFunctions.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType;

    static {
        $SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType = new int[WeaponTemplate.WeaponType.values().length];
        try {
            StatFunctions.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.BOW.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            StatFunctions.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.DAGGER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
