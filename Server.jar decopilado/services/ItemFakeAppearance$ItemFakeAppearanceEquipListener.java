/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.inventory.OnEquipListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 */
package services;

import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;

private static final class ItemFakeAppearance.ItemFakeAppearanceEquipListener
implements OnEquipListener {
    private ItemFakeAppearance.ItemFakeAppearanceEquipListener() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        Player player = playable.getPlayer();
        if (itemInstance == null || player == null) {
            return;
        }
        int n2 = itemInstance.getItemId();
        PcInventory pcInventory = player.getInventory();
        Map<Integer, Integer> map = cp.get(n2);
        if (map == null || map.isEmpty()) {
            return;
        }
        pcInventory.writeLock();
        LinkedHashMap<Integer, ItemInstance> linkedHashMap = new LinkedHashMap<Integer, ItemInstance>();
        try {
            for (Integer n3 : map.keySet()) {
                ItemInstance itemInstance2 = pcInventory.getPaperdollItem(n3.intValue());
                if (itemInstance2 == null || itemInstance2 == itemInstance) continue;
                linkedHashMap.put(n3, itemInstance2);
            }
            for (Integer n3 : linkedHashMap.values()) {
                pcInventory.unEquipItem((ItemInstance)n3);
            }
            a.c(player, n2);
            player.sendUserInfo(true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            for (ItemInstance itemInstance3 : linkedHashMap.values()) {
                pcInventory.equipItem(itemInstance3);
            }
            pcInventory.writeUnlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        Player player = playable.getPlayer();
        if (itemInstance == null || player == null) {
            return;
        }
        int n2 = itemInstance.getItemId();
        PcInventory pcInventory = player.getInventory();
        Map<Integer, Integer> map = cp.get(n2);
        if (map == null || map.isEmpty()) {
            return;
        }
        pcInventory.writeLock();
        LinkedHashMap<Integer, ItemInstance> linkedHashMap = new LinkedHashMap<Integer, ItemInstance>();
        try {
            for (Integer n3 : map.keySet()) {
                ItemInstance itemInstance2 = pcInventory.getPaperdollItem(n3.intValue());
                if (itemInstance2 == null || itemInstance2 == itemInstance) continue;
                linkedHashMap.put(n3, itemInstance2);
            }
            for (Integer n3 : linkedHashMap.values()) {
                pcInventory.unEquipItem((ItemInstance)n3);
            }
            a.A(playable.getPlayer());
            player.sendUserInfo(true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            for (ItemInstance itemInstance3 : linkedHashMap.values()) {
                pcInventory.equipItem(itemInstance3);
            }
            pcInventory.writeUnlock();
        }
    }
}
