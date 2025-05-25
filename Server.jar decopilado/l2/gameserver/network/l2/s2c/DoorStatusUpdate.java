/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

@Deprecated
public class DoorStatusUpdate
extends L2GameServerPacket {
    private final int tJ;
    private final boolean ek;
    private final int tK;
    private final boolean el;
    private final int tL;
    private final int tM;
    private final int tN;

    public DoorStatusUpdate(DoorInstance doorInstance, Player player) {
        this.tJ = doorInstance.getObjectId();
        this.tL = doorInstance.getDoorId();
        this.ek = !doorInstance.isOpen();
        this.el = doorInstance.isAutoAttackable(player);
        this.tM = (int)doorInstance.getCurrentHp();
        this.tN = doorInstance.getMaxHp();
        this.tK = doorInstance.getDamage();
    }

    @Override
    protected void writeImpl() {
        this.writeC(77);
        this.writeD(this.tJ);
        this.writeD(this.ek);
        this.writeD(this.tK);
        this.writeD(this.el);
        this.writeD(this.tL);
        this.writeD(this.tN);
        this.writeD(this.tM);
    }
}
