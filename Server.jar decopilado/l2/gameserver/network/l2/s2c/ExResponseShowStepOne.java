/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Language;

public class ExResponseShowStepOne
extends L2GameServerPacket {
    private Language b;

    public ExResponseShowStepOne(Player player) {
        this.b = player.getLanguage();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(174);
        this.writeD(0);
    }
}
