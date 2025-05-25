/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.IntObjectMap$Entry
 *  org.napile.primitive.maps.impl.TreeIntObjectMap
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import java.util.Map;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

public class ExBR_MiniGameLoadScores
extends L2GameServerPacket {
    private int uG;
    private int qL;
    private int uH;
    private IntObjectMap<List<Map.Entry<String, Integer>>> n = new TreeIntObjectMap();

    @Override
    protected void writeImpl() {
        this.writeEx(221);
        this.writeD(this.uG);
        this.writeD(this.qL);
        this.writeD(0);
        this.writeD(this.uH);
        for (IntObjectMap.Entry entry : this.n.entrySet()) {
            for (Map.Entry entry2 : (List)entry.getValue()) {
                this.writeD(entry.getKey());
                this.writeS((CharSequence)entry2.getKey());
                this.writeD((Integer)entry2.getValue());
            }
        }
    }
}
