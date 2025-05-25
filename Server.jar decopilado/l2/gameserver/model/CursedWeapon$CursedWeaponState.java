/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class CursedWeapon.CursedWeaponState
extends Enum<CursedWeapon.CursedWeaponState> {
    public static final /* enum */ CursedWeapon.CursedWeaponState NONE = new CursedWeapon.CursedWeaponState();
    public static final /* enum */ CursedWeapon.CursedWeaponState ACTIVATED = new CursedWeapon.CursedWeaponState();
    public static final /* enum */ CursedWeapon.CursedWeaponState DROPPED = new CursedWeapon.CursedWeaponState();
    private static final /* synthetic */ CursedWeapon.CursedWeaponState[] a;

    public static CursedWeapon.CursedWeaponState[] values() {
        return (CursedWeapon.CursedWeaponState[])a.clone();
    }

    public static CursedWeapon.CursedWeaponState valueOf(String string) {
        return Enum.valueOf(CursedWeapon.CursedWeaponState.class, string);
    }

    private static /* synthetic */ CursedWeapon.CursedWeaponState[] a() {
        return new CursedWeapon.CursedWeaponState[]{NONE, ACTIVATED, DROPPED};
    }

    static {
        a = CursedWeapon.CursedWeaponState.a();
    }
}
