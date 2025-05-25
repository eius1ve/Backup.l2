/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class StaticObject
extends L2GameServerPacket {
    private final int BK;
    private final int BL;
    private final int BM;
    private final int BN;
    private final int BO;
    private final int BP;
    private final int BQ;
    private final int BR;
    private final int BS;
    private final int BT;
    private final int BU;

    public StaticObject(StaticObjectInstance staticObjectInstance) {
        this.BK = staticObjectInstance.getUId();
        this.BL = staticObjectInstance.getObjectId();
        this.BM = 0;
        this.BN = 1;
        this.BO = staticObjectInstance.getMeshIndex();
        this.BP = 0;
        this.BQ = 0;
        this.BR = 0;
        this.BS = 0;
        this.BT = 0;
        this.BU = 0;
    }

    public StaticObject(DoorInstance doorInstance, Player player) {
        this.BK = doorInstance.getDoorId();
        this.BL = doorInstance.getObjectId();
        this.BM = 1;
        this.BN = doorInstance.getTemplate().isTargetable() ? 1 : 0;
        this.BO = 1;
        this.BP = doorInstance.isOpen() ? 0 : 1;
        this.BQ = doorInstance.isAutoAttackable(player) ? 1 : 0;
        this.BS = (int)doorInstance.getCurrentHp();
        this.BR = doorInstance.getMaxHp();
        this.BT = doorInstance.isHPVisible() ? 1 : 0;
        this.BU = doorInstance.getDamage();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(159);
        this.writeD(this.BK);
        this.writeD(this.BL);
        this.writeD(this.BM);
        this.writeD(this.BN);
        this.writeD(this.BO);
        this.writeD(this.BP);
        this.writeD(this.BQ);
        this.writeD(this.BS);
        this.writeD(this.BR);
        this.writeD(this.BT);
        this.writeD(this.BU);
    }
}
