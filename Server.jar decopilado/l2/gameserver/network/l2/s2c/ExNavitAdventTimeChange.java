/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExNavitAdventTimeChange
extends L2GameServerPacket {
    public static final ExNavitAdventTimeChange STATIC_EMPTY = new ExNavitAdventTimeChange();
    private boolean eG = true;
    private int _time = 0;

    @Override
    protected void writeImpl() {
    }
}
