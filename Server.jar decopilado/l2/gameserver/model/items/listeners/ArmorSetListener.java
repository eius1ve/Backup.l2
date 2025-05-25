/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items.listeners;

import l2.gameserver.data.xml.holder.ArmorSetsHolder;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;

public final class ArmorSetListener
implements OnEquipListener {
    private static final ArmorSetListener a = new ArmorSetListener();

    public static ArmorSetListener getInstance() {
        return a;
    }

    @Override
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        if (!itemInstance.isEquipable()) {
            return;
        }
        Player player = (Player)playable;
        ItemInstance itemInstance2 = player.getInventory().getPaperdollItem(6);
        if (itemInstance2 == null) {
            return;
        }
        ArmorSet armorSet = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(itemInstance2.getItemId());
        if (armorSet != null && armorSet.onEquip(n, itemInstance, player)) {
            player.sendSkillList();
            player.updateStats();
            player.sendUserInfo(true, UserInfoType.ENCHANTLEVEL);
        }
    }

    @Override
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        ArmorSet armorSet;
        if (!itemInstance.isEquipable()) {
            return;
        }
        Player player = (Player)playable;
        if (n == 6) {
            armorSet = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(itemInstance.getItemId());
        } else {
            ItemInstance itemInstance2 = player.getInventory().getPaperdollItem(6);
            if (itemInstance2 == null) {
                return;
            }
            armorSet = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(itemInstance2.getItemId());
        }
        if (armorSet != null && armorSet.onUnequip(n, itemInstance, player)) {
            if (!player.getInventory().isRefresh && !player.getOpenCloak() && player.getInventory().setPaperdollItem(28, null) != null) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_CLOAK_HAS_BEEN_UNEQUIPPED_BECAUSE_YOUR_ARMOR_SET_IS_NO_LONGER_COMPLETE));
            }
            player.sendSkillList();
            player.updateStats();
            player.sendUserInfo(true, UserInfoType.ENCHANTLEVEL);
        }
    }
}
