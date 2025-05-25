/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.Map;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PackageToList
extends L2GameServerPacket {
    private Map<Integer, String> bx = Collections.emptyMap();

    public PackageToList(Player player) {
        this.bx = player.getAccountChars();
    }

    @Override
    protected void writeImpl() {
        this.writeC(200);
        this.writeD(this.bx.size());
        for (Map.Entry<Integer, String> entry : this.bx.entrySet()) {
            this.writeD(entry.getKey());
            this.writeS(entry.getValue());
        }
    }
}
