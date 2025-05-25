/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBasicActionList
extends L2GameServerPacket {
    private final int[] aZ;

    public ExBasicActionList(Player player) {
        this.aZ = player.getTransformation() == 0 || player.getTransformation() >= 312 && player.getTransformation() <= 318 ? Config.ALT_BASIC_ACTIONS : Config.ALT_TRANSFORMATION_ACTIONS;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(96);
        this.writeDD(this.aZ, true);
    }
}
