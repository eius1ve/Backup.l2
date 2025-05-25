/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class DoorInfo
extends L2GameServerPacket {
    private int tG;
    private int tH;
    private int tI;

    @Deprecated
    public DoorInfo(DoorInstance doorInstance) {
        this.tG = doorInstance.getObjectId();
        this.tH = doorInstance.getDoorId();
        this.tI = doorInstance.isHPVisible() ? 1 : 0;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(76);
        this.writeD(this.tG);
        this.writeD(this.tH);
        this.writeD(this.tI);
    }
}
