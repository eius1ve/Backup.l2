/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExCursedWeaponList
extends L2GameServerPacket {
    private int[] ba = CursedWeaponsManager.getInstance().getCursedWeaponsIds();

    @Override
    protected final void writeImpl() {
        this.writeEx(72);
        this.writeD(this.ba.length);
        for (int n : this.ba) {
            Player player = ((GameClient)this.getClient()).getActiveChar();
            this.writeD(n);
            this.writeD(1);
            Location location = player.getLoc();
            this.writeD(location.x);
            this.writeD(location.y);
            this.writeD(location.z);
            this.writeQ(5000L);
            this.writeQ(0L);
        }
    }
}
