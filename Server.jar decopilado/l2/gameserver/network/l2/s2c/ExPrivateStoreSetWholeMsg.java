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

public class ExPrivateStoreSetWholeMsg
extends L2GameServerPacket {
    private final int wS;
    private final String fl;

    public ExPrivateStoreSetWholeMsg(Player player, String string) {
        this.wS = player.getObjectId();
        this.fl = string;
    }

    public ExPrivateStoreSetWholeMsg(Player player) {
        this(player, StringUtils.defaultString((String)player.getSellStoreName()));
    }

    @Override
    protected void writeImpl() {
        this.writeEx(129);
        this.writeD(this.wS);
        this.writeS(this.fl);
    }
}
