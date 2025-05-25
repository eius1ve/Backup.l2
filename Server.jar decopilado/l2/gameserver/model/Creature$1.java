/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.network.l2.s2c.FlyToLocation;
import l2.gameserver.templates.item.WeaponTemplate;

static class Creature.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType;

    static {
        $SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType = new int[FlyToLocation.FlyType.values().length];
        try {
            Creature.1.$SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType[FlyToLocation.FlyType.DUMMY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType[FlyToLocation.FlyType.CHARGE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType[FlyToLocation.FlyType.THROW_UP.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$network$l2$s2c$FlyToLocation$FlyType[FlyToLocation.FlyType.THROW_HORIZONTAL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType = new int[WeaponTemplate.WeaponType.values().length];
        try {
            Creature.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.BOW.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.POLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.DUAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Creature.1.$SwitchMap$l2$gameserver$templates$item$WeaponTemplate$WeaponType[WeaponTemplate.WeaponType.DUALFIST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
