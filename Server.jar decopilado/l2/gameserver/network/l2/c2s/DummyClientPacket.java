/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class DummyClientPacket
extends L2GameClientPacket {
    public static final DummyClientPacket STATIC_DUMMY_CLIENT_PACKET = new DummyClientPacket();

    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
    }
}
