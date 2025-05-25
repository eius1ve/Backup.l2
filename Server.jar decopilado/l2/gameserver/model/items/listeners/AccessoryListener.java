/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items.listeners;

import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;

public final class AccessoryListener
implements OnEquipListener {
    private static final AccessoryListener a = new AccessoryListener();

    public static AccessoryListener getInstance() {
        return a;
    }

    @Override
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        if (!itemInstance.isEquipable()) {
            return;
        }
        Player player = (Player)playable;
        if (itemInstance.getBodyPart() == 0x200000L && itemInstance.getTemplate().getAttachedSkills().length > 0) {
            int n2 = player.getAgathionId();
            int n3 = player.getTransformationTemplate();
            for (Skill skill : itemInstance.getTemplate().getAttachedSkills()) {
                if (n2 > 0 && skill.getNpcId() == n2) {
                    player.setAgathion(0);
                }
                if (skill.getNpcId() != n3 || skill.getSkillType() != Skill.SkillType.TRANSFORMATION) continue;
                player.setTransformation(0);
            }
        }
        InventorySlot[] inventorySlotArray = null;
        if (itemInstance.getTemplate().isAccessory()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.REAR, InventorySlot.LEAR, InventorySlot.NECK, InventorySlot.RFINGER, InventorySlot.LFINGER};
        } else if (itemInstance.getTemplate().isBrooche()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.BROOCH};
        } else if (itemInstance.getTemplate().isBroochJewel()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.BROOCH_JEWEL, InventorySlot.BROOCH_JEWEL2, InventorySlot.BROOCH_JEWEL3, InventorySlot.BROOCH_JEWEL4, InventorySlot.BROOCH_JEWEL5, InventorySlot.BROOCH_JEWEL6};
        } else if (itemInstance.getTemplate().isBracelet()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.RBRACELET, InventorySlot.LBRACELET};
        } else if (itemInstance.getTemplate().isTalisman()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.DECO1, InventorySlot.DECO2, InventorySlot.DECO3, InventorySlot.DECO4, InventorySlot.DECO5, InventorySlot.DECO6};
        } else if (itemInstance.getTemplate().isAgathionCharm()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5};
        }
        if (inventorySlotArray != null) {
            player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, inventorySlotArray));
            player.sendUserInfo(false);
        } else {
            player.broadcastCharInfo();
        }
    }

    @Override
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        if (!itemInstance.isEquipable()) {
            return;
        }
        Player player = (Player)playable;
        InventorySlot[] inventorySlotArray = null;
        if (itemInstance.getTemplate().isAccessory()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.REAR, InventorySlot.LEAR, InventorySlot.NECK, InventorySlot.RFINGER, InventorySlot.LFINGER};
        } else if (itemInstance.getTemplate().isBrooche()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.BROOCH};
        } else if (itemInstance.getTemplate().isBroochJewel()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.BROOCH_JEWEL, InventorySlot.BROOCH_JEWEL2, InventorySlot.BROOCH_JEWEL3, InventorySlot.BROOCH_JEWEL4, InventorySlot.BROOCH_JEWEL5, InventorySlot.BROOCH_JEWEL6};
        } else if (itemInstance.getTemplate().isBracelet()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.RBRACELET, InventorySlot.LBRACELET};
        } else if (itemInstance.getTemplate().isTalisman()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.DECO1, InventorySlot.DECO2, InventorySlot.DECO3, InventorySlot.DECO4, InventorySlot.DECO5, InventorySlot.DECO6};
        } else if (itemInstance.getTemplate().isAgathionCharm()) {
            inventorySlotArray = new InventorySlot[]{InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5};
        }
        if (inventorySlotArray != null) {
            player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, inventorySlotArray));
            player.sendUserInfo(false);
        } else {
            player.broadcastCharInfo();
        }
    }
}
