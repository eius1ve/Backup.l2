/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Language;

public class ExResponseShowStepTwo
extends L2GameServerPacket {
    private Language b;

    @Override
    protected void writeImpl() {
        this.writeEx(175);
        this.writeD(0);
        this.writeS("");
    }
}
