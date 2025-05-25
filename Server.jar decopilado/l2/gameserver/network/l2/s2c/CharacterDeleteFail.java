/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.base.CharacterDeleteFailType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class CharacterDeleteFail
extends L2GameServerPacket {
    private final int tD;

    public CharacterDeleteFail(CharacterDeleteFailType characterDeleteFailType) {
        this.tD = characterDeleteFailType.ordinal();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(30);
        this.writeD(this.tD);
    }
}
