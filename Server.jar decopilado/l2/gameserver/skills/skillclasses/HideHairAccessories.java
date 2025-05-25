/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.templates.StatsSet;

public class HideHairAccessories
extends Skill {
    public HideHairAccessories(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!creature.isPlayer()) {
            return false;
        }
        PcInventory pcInventory = creature.getPlayer().getInventory();
        ItemInstance itemInstance = pcInventory.getPaperdollItem(2);
        if (itemInstance == null) {
            itemInstance = pcInventory.getPaperdollItem(3);
        }
        if (itemInstance == null) {
            creature.sendPacket((IStaticPacket)SystemMsg.PLEASE_EQUIP_THE_HAIR_ACCESSORY_AND_TRY_AGAIN);
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this));
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        Player player = creature.getPlayer();
        if (player == null) {
            return;
        }
        player.setHideHeadAccessories(!player.hideHeadAccessories());
        player.setVar("hideAccessory", String.valueOf(player.hideHeadAccessories()), -1L);
        player.broadcastUserInfo(true, UserInfoType.APPAREANCE);
        if (player.hideHeadAccessories()) {
            player.sendPacket((IStaticPacket)SystemMsg.HAIR_ACCESSORIES_WILL_NO_LONGER_BE_DISPLAYED);
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.HAIR_ACCESSORIES_WILL_BE_DISPLAYED_FROM_NOW_ON);
        }
    }
}
