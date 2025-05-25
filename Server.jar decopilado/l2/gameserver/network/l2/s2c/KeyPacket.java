/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import l2.gameserver.Config;
import l2.gameserver.network.authcomm.ServerType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class KeyPacket
extends L2GameServerPacket {
    private Long a = null;

    public KeyPacket(Object object) {
        if (object != null) {
            ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])object).order(ByteOrder.LITTLE_ENDIAN);
            this.a = byteBuffer.getLong();
        }
    }

    @Override
    public void writeImpl() {
        this.writeC(46);
        if (this.a == null) {
            this.writeC(0);
            return;
        }
        this.writeC(1);
        this.writeQ(this.a);
        this.writeD(1);
        this.writeD(0);
        this.writeC(0);
        this.writeD(0);
        this.writeC((Config.AUTH_SERVER_SERVER_TYPE & ServerType.CLASSIC.getMask()) != 0);
        this.writeC(0);
        this.writeC(0);
    }
}
