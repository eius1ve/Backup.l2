/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowEnsoulWindow
extends L2GameServerPacket {
    public static final ExShowEnsoulWindow STATIC = new ExShowEnsoulWindow();

    private ExShowEnsoulWindow() {
    }

    @Override
    protected void writeImpl() {
        this.writeEx(384);
    }
}
