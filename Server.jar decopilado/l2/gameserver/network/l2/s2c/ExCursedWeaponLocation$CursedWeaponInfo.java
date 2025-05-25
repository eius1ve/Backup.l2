/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.utils.Location;

public static class ExCursedWeaponLocation.CursedWeaponInfo {
    public Location _pos;
    public int _id;
    public int _status;

    public ExCursedWeaponLocation.CursedWeaponInfo(Location location, int n, int n2) {
        this._pos = location;
        this._id = n;
        this._status = n2;
    }
}
