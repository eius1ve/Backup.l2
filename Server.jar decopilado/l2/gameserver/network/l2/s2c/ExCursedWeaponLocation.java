/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExCursedWeaponLocation
extends L2GameServerPacket {
    private List<CursedWeaponInfo> ce;

    public ExCursedWeaponLocation(List<CursedWeaponInfo> list) {
        this.ce = list;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(72);
        if (this.ce.isEmpty()) {
            this.writeD(0);
        } else {
            this.writeD(this.ce.size());
            for (CursedWeaponInfo cursedWeaponInfo : this.ce) {
                this.writeD(cursedWeaponInfo._id);
                this.writeD(cursedWeaponInfo._status);
                this.writeD(cursedWeaponInfo._pos.x);
                this.writeD(cursedWeaponInfo._pos.y);
                this.writeD(cursedWeaponInfo._pos.z);
            }
        }
    }

    public static class CursedWeaponInfo {
        public Location _pos;
        public int _id;
        public int _status;

        public CursedWeaponInfo(Location location, int n, int n2) {
            this._pos = location;
            this._id = n;
            this._status = n2;
        }
    }
}
