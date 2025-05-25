/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.base.NpcInfoSpeed;
import l2.gameserver.network.l2.s2c.mask.AbstractMaskPacket;

public class ExNpcInfoSpeed
extends AbstractMaskPacket<NpcInfoSpeed> {
    private final int vN;
    private final float d;
    private final float e;
    private final byte[] t = new byte[1];

    public ExNpcInfoSpeed(Creature creature, NpcInfoSpeed ... npcInfoSpeedArray) {
        this.vN = creature.getObjectId();
        this.d = creature.isRunning() ? (float)creature.getRunSpeed() / (float)creature.getTemplate().baseRunSpd : (float)creature.getWalkSpeed() / (float)creature.getTemplate().baseWalkSpd;
        this.e = (float)creature.getAttackSpeedMultiplier();
        if (npcInfoSpeedArray != null && npcInfoSpeedArray.length > 0) {
            this.addComponentType(npcInfoSpeedArray);
        } else {
            this.addComponentType(NpcInfoSpeed.values());
        }
    }

    @Override
    protected byte[] getMasks() {
        return this.t;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(306);
        this.writeD(this.vN);
        this.writeH(NpcInfoSpeed.values().length);
        this.writeB(this.t);
        if (this.containsMask(NpcInfoSpeed.MOVE_SPEED_MUL)) {
            this.writeE(this.d);
        }
        if (this.containsMask(NpcInfoSpeed.ATTACK_SPEED_MUL)) {
            this.writeE(this.e);
        }
        if (this.containsMask(NpcInfoSpeed.CAST_M_SKILL_SPEED)) {
            this.writeH(0);
        }
        if (this.containsMask(NpcInfoSpeed.CAST_P_SKILL_SPEED)) {
            this.writeH(0);
        }
    }
}
