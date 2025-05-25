/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.StatsSet;

public class Ride
extends Skill {
    public Ride(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!creature.isPlayer()) {
            return false;
        }
        Player player = (Player)creature;
        if (this.getNpcId() != 0) {
            if (player.isOlyParticipant()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_THAT_ITEM_IN_A_GRAND_OLYMPIAD_MATCH);
                return false;
            }
            if (player.isInDuel() || player.isSitting() || player.isInCombat() || player.isFishing() || player.isCursedWeaponEquipped() || player.getTransformation() != 0 || player.getPet() != null || player.isMounted() || player.isInBoat()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                return false;
            }
        } else if (this.getNpcId() == 0 && !player.isMounted()) {
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = (Player)creature;
        player.setMount(this.getNpcId(), 0, 0);
    }
}
