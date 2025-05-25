/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.pfilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.Config;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.pfilter.Limit;
import l2.gameserver.network.pfilter.PacketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketFilterHolder
extends AbstractHolder {
    private static final Logger de = LoggerFactory.getLogger(PacketFilterHolder.class);
    private static final PacketFilterHolder a = new PacketFilterHolder();
    private Map<Integer, Limit> bD = new HashMap<Integer, Limit>();
    private Map<Class<? extends L2GameClientPacket>, Limit> bE = new HashMap<Class<? extends L2GameClientPacket>, Limit>();

    private PacketFilterHolder() {
    }

    public static PacketFilterHolder getInstance() {
        return a;
    }

    public void addByOpcodeLimit(int n, Limit limit) {
        this.bD.put(n, limit);
    }

    public void addByOpcodeExLimit(int n, int n2, Limit limit) {
        this.bD.put(PacketFilter.exOpcode(n, n2), limit);
    }

    public void addByPacketNameLimit(Class<? extends L2GameClientPacket> clazz, Limit limit) {
        this.bE.put(clazz, limit);
    }

    public Map<Integer, Limit> getByOpcodeLimits() {
        return Collections.unmodifiableMap(this.bD);
    }

    public Map<Class<? extends L2GameClientPacket>, Limit> getByPacketClassLimits() {
        return Collections.unmodifiableMap(this.bE);
    }

    public boolean isEnabled() {
        return Config.PFILTER_ENABLED;
    }

    @Override
    public int size() {
        return this.bD.size() + this.bE.size();
    }

    @Override
    public void clear() {
        this.bD.clear();
        this.bE.clear();
    }
}
