/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.apache.commons.lang3.StringUtils;

public class PrivateStoreMsgSell
extends L2GameServerPacket {
    private final int AF;
    private String _name;

    public PrivateStoreMsgSell(Player player) {
        this.AF = player.getObjectId();
        if (player.getSellList() != null || player.isSellingBuffs()) {
            this._name = StringUtils.defaultString((String)player.getSellStoreName());
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(162);
        this.writeD(this.AF);
        this.writeS(this._name);
    }
}
