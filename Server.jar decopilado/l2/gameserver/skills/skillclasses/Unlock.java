/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.ChestInstance;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.StatsSet;

public class Unlock
extends Skill {
    private final int DA;

    public Unlock(StatsSet statsSet) {
        super(statsSet);
        this.DA = statsSet.getInteger("unlockPower", 0) + 100;
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (creature2 == null || creature2 instanceof ChestInstance && creature2.isDead()) {
            creature.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return false;
        }
        if (creature2 instanceof ChestInstance && creature.isPlayer()) {
            return super.checkCondition(creature, creature2, bl, bl2, bl3);
        }
        if (!creature2.isDoor() || this.DA == 0) {
            creature.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return false;
        }
        DoorInstance doorInstance = (DoorInstance)creature2;
        if (doorInstance.isOpen()) {
            creature.sendPacket((IStaticPacket)SystemMsg.IT_IS_NOT_LOCKED);
            return false;
        }
        if (!doorInstance.isUnlockable()) {
            creature.sendPacket((IStaticPacket)SystemMsg.THIS_DOOR_CANNOT_BE_UNLOCKED);
            return false;
        }
        if (doorInstance.getKey() > 0) {
            creature.sendPacket((IStaticPacket)SystemMsg.THIS_DOOR_CANNOT_BE_UNLOCKED);
            return false;
        }
        if (this.DA - doorInstance.getLevel() * 100 < 0) {
            creature.sendPacket((IStaticPacket)SystemMsg.THIS_DOOR_CANNOT_BE_UNLOCKED);
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            Creature creature3;
            if (creature2 == null) continue;
            if (creature2.isDoor()) {
                creature3 = (DoorInstance)creature2;
                if (!((DoorInstance)creature3).isOpen() && (((DoorInstance)creature3).getKey() > 0 || Rnd.chance(this.DA - ((DoorInstance)creature3).getLevel() * 100))) {
                    ((DoorInstance)creature3).openMe((Player)creature, true);
                    continue;
                }
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_UNLOCK_THE_DOOR);
                continue;
            }
            if (!(creature2 instanceof ChestInstance) || (creature3 = (ChestInstance)creature2).isDead()) continue;
            ((ChestInstance)creature3).tryOpen((Player)creature, this);
        }
    }
}
