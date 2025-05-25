/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ServerObjectInfo
extends L2GameServerPacket {
    private final int Bi;
    private final int Bj;
    private final boolean fl;
    private final int Bk;
    private final int Bl;
    private final Location W;
    private final double ai;
    private final double aj;
    private final double ak;
    private String _name = "";
    private final int Bm;

    public ServerObjectInfo(NpcInstance npcInstance, Creature creature) {
        this.Bi = npcInstance.getObjectId();
        this.Bj = npcInstance.getDisplayId() != 0 ? npcInstance.getDisplayId() : npcInstance.getTemplate().npcId;
        this.fl = creature != null && npcInstance.isAutoAttackable(creature);
        this.ak = npcInstance.getAttackSpeedMultiplier();
        if (Config.SERVER_SIDE_NPC_NAME || npcInstance.getTemplate().displayId != 0 || npcInstance.getName() != npcInstance.getTemplate().name) {
            this._name = npcInstance.getName();
        }
        this.W = npcInstance.getLoc();
        this.ai = npcInstance.getCollisionHeight();
        this.aj = npcInstance.getCollisionRadius();
        this.Bk = (int)npcInstance.getCurrentHp();
        this.Bl = npcInstance.getMaxHp();
        this.Bm = npcInstance.getSpawnAnimation();
    }

    @Override
    protected void writeImpl() {
        this.writeC(146);
        this.writeD(this.Bi);
        this.writeD(this.Bj + 1000000);
        this.writeS(this._name);
        this.writeD(this.fl ? 1 : 0);
        this.writeD(this.W.x);
        this.writeD(this.W.y);
        this.writeD(this.W.z + Config.CLIENT_Z_SHIFT);
        this.writeD(this.W.h);
        this.writeF(1.0);
        this.writeF(this.ak);
        this.writeF(this.aj);
        this.writeF(this.ai);
        this.writeD(this.fl ? this.Bk : 0);
        this.writeD(this.fl ? this.Bl : 0);
        this.writeD(1);
        this.writeD(this.Bm);
    }
}
