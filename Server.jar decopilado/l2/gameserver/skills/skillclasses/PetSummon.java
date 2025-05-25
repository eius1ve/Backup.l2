/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.StatsSet;

public class PetSummon
extends Skill {
    public PetSummon(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        Player player = creature.getPlayer();
        if (player == null) {
            return false;
        }
        if (player.getPetControlItem() == null) {
            return false;
        }
        PetData petData = PetDataHolder.getInstance().getByControlItemId(player.getPetControlItem());
        if (petData == null) {
            return false;
        }
        if (player.isInCombat()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_SUMMON_DURING_COMBAT);
            return false;
        }
        if (player.isProcessingRequest()) {
            player.sendPacket((IStaticPacket)SystemMsg.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
            return false;
        }
        if (player.isMounted() || player.getPet() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_HAVE_A_PET);
            return false;
        }
        if (player.isInBoat()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_CALL_FORTH_A_PET_OR_SUMMONED_CREATURE_FROM_THIS_LOCATION);
            return false;
        }
        if (player.isInFlyingTransform()) {
            return false;
        }
        if (player.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_THAT_ITEM_IN_A_GRAND_OLYMPIAD_MATCH);
            return false;
        }
        if (player.isCursedWeaponEquipped()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_USE_MULTIPLE_PETS_OR_SERVITORS_AT_THE_SAME_TIME);
            return false;
        }
        for (GameObject gameObject : World.getAroundObjects(player, 120, 200)) {
            if (!gameObject.isDoor()) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_SUMMON_FROM_YOUR_CURRENT_LOCATION);
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        Player player = creature.getPlayer();
        player.summonPet();
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
