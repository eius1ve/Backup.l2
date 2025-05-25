/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import java.util.Map;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.pfilter.PacketFilter;

class PacketFilter.1
extends PacketFilter {
    PacketFilter.1(GameClient gameClient, Map map, Map map2) {
        super(gameClient, map, map2);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean checkPacket(int n) {
        return true;
    }

    @Override
    public boolean checkPacketEx(int n, int n2) {
        return true;
    }

    @Override
    public boolean checkPacket(ReceivablePacket<GameClient> receivablePacket) {
        return true;
    }
}
