/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import java.lang.constant.Constable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.gameserver.Config;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.pfilter.Limit;
import l2.gameserver.network.pfilter.PacketFilterHolder;
import l2.gameserver.network.pfilter.RateLimit;

public class PacketFilter {
    private static final PacketFilter b = new PacketFilter(null, Collections.emptyMap(), Collections.emptyMap()){

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
    };
    private final Map<Integer, RateLimit> bB;
    private final Map<Class<? extends L2GameClientPacket>, RateLimit> bC;
    private final GameClient d;

    private PacketFilter(GameClient gameClient, Map<Integer, RateLimit> map, Map<Class<? extends L2GameClientPacket>, RateLimit> map2) {
        this.d = gameClient;
        this.bB = new HashMap<Integer, RateLimit>(map);
        this.bC = new HashMap<Class<? extends L2GameClientPacket>, RateLimit>(map2);
    }

    static int exOpcode(int n, int n2) {
        return n << 16 | n2 & 0xFFFF;
    }

    public static PacketFilter create(GameClient gameClient) {
        if (!Config.PFILTER_ENABLED) {
            return b;
        }
        HashMap<Integer, RateLimit> hashMap = new HashMap<Integer, RateLimit>();
        HashMap<Class<? extends L2GameClientPacket>, RateLimit> hashMap2 = new HashMap<Class<? extends L2GameClientPacket>, RateLimit>();
        for (Map.Entry<Integer, Limit> entry : PacketFilterHolder.getInstance().getByOpcodeLimits().entrySet()) {
            hashMap.put(entry.getKey(), new RateLimit(entry.getValue()));
        }
        for (Map.Entry<Constable, Limit> entry : PacketFilterHolder.getInstance().getByPacketClassLimits().entrySet()) {
            hashMap2.put((Class)entry.getKey(), new RateLimit(entry.getValue()));
        }
        return new PacketFilter(gameClient, hashMap, hashMap2);
    }

    public boolean isEnabled() {
        return Config.PFILTER_ENABLED;
    }

    private RateLimit a(int n) {
        return this.bB.get(n);
    }

    private RateLimit a(int n, int n2) {
        return this.bB.get(PacketFilter.exOpcode(n, n2));
    }

    private RateLimit a(Class<? extends L2GameClientPacket> clazz) {
        return this.bC.get(clazz);
    }

    public boolean checkRate(RateLimit rateLimit) {
        return rateLimit == null || rateLimit.tryPass(this.d);
    }

    public boolean checkPacket(int n) {
        return this.checkRate(this.a(n));
    }

    public boolean checkPacketEx(int n, int n2) {
        return this.checkRate(this.a(n, n2));
    }

    public boolean checkPacket(ReceivablePacket<GameClient> receivablePacket) {
        return this.checkRate(this.a(receivablePacket.getClass()));
    }
}
