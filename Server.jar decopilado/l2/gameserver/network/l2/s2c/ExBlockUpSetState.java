/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBlockUpSetState
extends L2GameServerPacket {
    private int uJ = 0;

    @Override
    protected void writeImpl() {
        this.writeEx(152);
        this.writeD(this.uJ);
        switch (this.uJ) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
        }
    }
}
