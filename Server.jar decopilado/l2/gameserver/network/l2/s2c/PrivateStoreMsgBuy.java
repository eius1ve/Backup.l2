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

public class PrivateStoreMsgBuy
extends L2GameServerPacket {
    private int sR;
    private String _name;

    public PrivateStoreMsgBuy(Player player) {
        this.sR = player.getObjectId();
        this._name = StringUtils.defaultString((String)player.getBuyStoreName());
    }

    @Override
    protected final void writeImpl() {
        this.writeC(191);
        this.writeD(this.sR);
        this.writeS(this._name);
    }
}
