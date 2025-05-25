/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.InstantZone;

public class ExInzoneWaitingInfo
extends L2GameServerPacket {
    private int ru;
    private int vz = -1;
    private Map<Integer, Integer> bu = new LinkedHashMap<Integer, Integer>();

    public ExInzoneWaitingInfo(Player player, int n) {
        this.ru = n;
        if (player.getActiveReflection() != null) {
            this.vz = player.getActiveReflection().getInstancedZoneId();
        }
        for (Map.Entry<Integer, Long> entry : player.getInstanceReuses().entrySet()) {
            int n2;
            InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(entry.getKey());
            if (instantZone == null || (n2 = instantZone.getMinutesToNextEntrance(player)) <= 0) continue;
            this.bu.put(entry.getKey(), Math.toIntExact(n2 * 60));
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(286);
        this.writeC(this.ru);
        this.writeD(this.vz);
        this.writeD(this.bu.size());
        for (Map.Entry<Integer, Integer> entry : this.bu.entrySet()) {
            this.writeD(entry.getKey());
            this.writeD(Math.toIntExact(entry.getValue().intValue()));
        }
    }
}
