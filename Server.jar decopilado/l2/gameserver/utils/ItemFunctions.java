/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.attachment.PickableAttachment;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.MapUtils;

public final class ItemFunctions {
    public static final int[][] catalyst = new int[][]{{12362, 14078, 14702}, {12363, 14079, 14703}, {12364, 14080, 14704}, {12365, 14081, 14705}, {12366, 14082, 14706}, {12367, 14083, 14707}, {12368, 14084, 14708}, {12369, 14085, 14709}, {12370, 14086, 14710}, {12371, 14087, 14711}};
    private static final Map<Long, Integer> bZ = MapUtils.mapBuilder().add(1L, 0).add(2L, 8).add(4L, 9).add(8L, 4).add(16L, 13).add(32L, 14).add(64L, 1).add(128L, 5).add(256L, 7).add(16384L, 5).add(512L, 10).add(1024L, 6).add(32768L, 6).add(131072L, 6).add(2048L, 11).add(4096L, 12).add(8192L, 28).add(65536L, 2).add(524288L, 2).add(262144L, 3).add(0x100000L, 16).add(0x200000L, 15).add(0x400000L, 22).add(0x10000000L, 29).add(0x20000000L, 30).add(0x40000000L, 31).add(0x3000000000L, 17).build();

    private ItemFunctions() {
    }

    public static ItemInstance createItem(int n) {
        return ItemFunctions.createItem(n, 1L);
    }

    public static ItemInstance createItem(int n, long l) {
        ItemInstance itemInstance = new ItemInstance(IdFactory.getInstance().getNextId(), n);
        itemInstance.setLocation(ItemInstance.ItemLocation.VOID);
        itemInstance.setCount(l);
        return itemInstance;
    }

    public static void addItem(Playable playable, int n, long l, boolean bl) {
        if (playable == null || l < 1L) {
            return;
        }
        Playable playable2 = playable.isSummon() ? playable.getPlayer() : playable;
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (itemTemplate.isStackable()) {
            playable2.getInventory().addItem(n, l);
        } else {
            for (long i = 0L; i < l; ++i) {
                playable2.getInventory().addItem(n, 1L);
            }
        }
        if (bl) {
            playable2.sendPacket((IStaticPacket)SystemMessage.obtainItems(n, l, 0));
        }
    }

    public static List<ItemInstance> addItem(Playable playable, ItemTemplate itemTemplate, long l, boolean bl) {
        LinkedList<ItemInstance> linkedList;
        if (playable == null || l < 1L) {
            return Collections.emptyList();
        }
        Playable playable2 = playable.isSummon() ? playable.getPlayer() : playable;
        if (itemTemplate.isStackable()) {
            linkedList = Collections.singletonList(playable2.getInventory().addItem(itemTemplate.getItemId(), l));
        } else {
            linkedList = new LinkedList();
            for (long i = 0L; i < l; ++i) {
                linkedList.add(playable2.getInventory().addItem(itemTemplate.getItemId(), 1L));
            }
        }
        if (bl) {
            playable2.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemTemplate.getItemId(), l, 0));
        }
        return linkedList;
    }

    public static long getItemCount(Playable playable, int n) {
        if (playable == null) {
            return 0L;
        }
        Player player = playable.getPlayer();
        return ((Playable)player).getInventory().getCountOf(n);
    }

    public static long removeItem(Playable playable, int n, long l, boolean bl) {
        return ItemFunctions.removeItem(playable, n, l, bl, true);
    }

    public static long removeItem(Playable playable, int n, long l, boolean bl, boolean bl2) {
        long l2 = 0L;
        if (playable == null || l < 1L) {
            return l2;
        }
        Player player = playable.getPlayer();
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (itemTemplate.isStackable()) {
            for (ItemInstance itemInstance : player.getInventory().getItems()) {
                if (l2 != l) {
                    if (itemInstance == null || itemInstance.getTemplate() != itemTemplate) continue;
                    long l3 = Math.min(l - l2, itemInstance.getCount());
                    if (!player.getInventory().destroyItem(itemInstance, l3)) continue;
                    l2 += l3;
                    continue;
                }
                break;
            }
        } else {
            for (long i = 0L; i < l; ++i) {
                if (!player.getInventory().destroyItemByItemId(n, 1L)) continue;
                ++l2;
            }
        }
        if (l2 > 0L && bl) {
            player.sendPacket((IStaticPacket)SystemMessage.removeItems(n, l2));
        }
        if (bl2) {
            Log.LogItem(player, Log.ItemLog.RemoveItem, n, l2);
        }
        return l2;
    }

    public static SystemMessage checkIfCanEquip(PetInstance petInstance, ItemInstance itemInstance) {
        if (!itemInstance.isEquipable()) {
            return new SystemMessage(SystemMsg.YOUR_PET_CANNOT_CARRY_THIS_ITEM);
        }
        int n = petInstance.getNpcId();
        PetData petData = PetDataHolder.getInstance().getInfo(n);
        if (itemInstance.getTemplate().isPendant() || petData != null && (petData.isWolf() && itemInstance.getTemplate().isForWolf() || petData.isHatchling() && itemInstance.getTemplate().isForHatchling() || petData.isStrider() && itemInstance.getTemplate().isForStrider() || petData.isGreatWolf() && itemInstance.getTemplate().isForGWolf() || petData.isBabyPet() && itemInstance.getTemplate().isForPetBaby() || petData.isImprovedBabyPet() && itemInstance.getTemplate().isForPetBaby())) {
            return null;
        }
        return new SystemMessage(SystemMsg.YOUR_PET_CANNOT_CARRY_THIS_ITEM);
    }

    public static final L2GameServerPacket checkIfCanEquip(Player player, ItemInstance itemInstance) {
        ItemInstance itemInstance2;
        int n;
        int n2;
        int n3 = itemInstance.getItemId();
        long l = itemInstance.getTemplate().getBodyPart();
        if (l == 16384L || l == 256L || l == 128L) {
            if (n3 != player.getInventory().getPaperdollItemId(5) && CursedWeaponsManager.getInstance().isCursed(player.getInventory().getPaperdollItemId(5))) {
                return new SystemMessage(SystemMsg.YOU_DO_NOT_MEET_THE_REQUIRED_CONDITION_TO_EQUIP_THAT_ITEM);
            }
            if (player.isCursedWeaponEquipped() && n3 != player.getCursedWeaponEquippedId()) {
                return new SystemMessage(SystemMsg.YOU_DO_NOT_MEET_THE_REQUIRED_CONDITION_TO_EQUIP_THAT_ITEM);
            }
        }
        if (itemInstance.getTemplate().isCloak()) {
            if (itemInstance.getName().contains("Knight") && (player.getPledgeClass() < 3 || player.getCastle() == null)) {
                return new SystemMessage(SystemMsg.YOU_DO_NOT_MEET_THE_REQUIRED_CONDITION_TO_EQUIP_THAT_ITEM);
            }
            if (!player.getOpenCloak()) {
                return new SystemMessage(SystemMsg.THE_CLOAK_CANNOT_BE_EQUIPPED_BECAUSE_YOUR_ARMOR_SET_IS_NOT_COMPLETE);
            }
        }
        if (l == 0x400000L) {
            n2 = player.getTalismanCount();
            if (n2 <= 0) {
                return new SystemMessage(SystemMsg.YOU_CANNOT_WEAR_S1_BECAUSE_YOU_ARE_NOT_WEARING_A_BRACELET).addItemName(n3);
            }
            for (n = 22; n <= 27; ++n) {
                itemInstance2 = player.getInventory().getPaperdollItem(n);
                if (itemInstance2 == null) continue;
                if (itemInstance2 == itemInstance) {
                    return null;
                }
                if (--n2 > 0 && itemInstance2.getItemId() != n3) continue;
                return new SystemMessage(SystemMsg.YOU_CANNOT_EQUIP_S1_BECAUSE_YOU_DO_NOT_HAVE_ANY_AVAILABLE_SLOTS).addItemName(n3);
            }
        }
        if (l == 0x40000000L) {
            n2 = player.getBroochCount();
            if (n2 <= 0) {
                return new SystemMessage(SystemMsg.YOU_CANNOT_EQUIP_S1_WITHOUT_EQUIPPING_A_BROOCH).addItemName(n3);
            }
            for (n = 31; n <= 36; ++n) {
                itemInstance2 = player.getInventory().getPaperdollItem(n);
                if (itemInstance2 == null) continue;
                if (itemInstance2 == itemInstance) {
                    return null;
                }
                if (--n2 > 0 && itemInstance2.getItemId() != n3) continue;
                return new SystemMessage(SystemMsg.YOU_CANNOT_EQUIP_S1_BECAUSE_YOU_DO_NOT_HAVE_ANY_AVAILABLE_SLOTS).addItemName(n3);
            }
        }
        if (l == 0x3000000000L) {
            n2 = player.getAgathionCharmCount();
            if (n2 <= 0) {
                return new SystemMessage(SystemMsg.YOU_CANNOT_WEAR_S1_BECAUSE_YOU_ARE_NOT_WEARING_A_BRACELET).addItemName(n3);
            }
            for (n = 17; n <= 21; ++n) {
                itemInstance2 = player.getInventory().getPaperdollItem(n);
                if (itemInstance2 == null) continue;
                if (itemInstance2 == itemInstance) {
                    return null;
                }
                if (--n2 > 0 && itemInstance2.getItemId() != n3) continue;
                return new SystemMessage(SystemMsg.YOU_CANNOT_EQUIP_S1_BECAUSE_YOU_DO_NOT_HAVE_ANY_AVAILABLE_SLOTS).addItemName(n3);
            }
        }
        return null;
    }

    public static boolean checkIfCanPickup(Playable playable, ItemInstance itemInstance) {
        Player player = playable.getPlayer();
        return itemInstance.getDropTimeOwner() <= System.currentTimeMillis() || itemInstance.getDropPlayers().contains(player.getObjectId());
    }

    public static boolean canAddItem(Player player, ItemInstance itemInstance) {
        if (!player.getInventory().validateWeight(itemInstance)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
            return false;
        }
        if (!player.getInventory().validateCapacity(itemInstance)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return false;
        }
        if (!itemInstance.getTemplate().getHandler().pickupItem(player, itemInstance)) {
            return false;
        }
        PickableAttachment pickableAttachment = itemInstance.getAttachment() instanceof PickableAttachment ? (PickableAttachment)itemInstance.getAttachment() : null;
        return pickableAttachment == null || pickableAttachment.canPickUp(player);
    }

    public static boolean canAddItem(Player player, ItemTemplate itemTemplate, long l) {
        if (!player.getInventory().validateWeight(itemTemplate, l)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
            return false;
        }
        if (!player.getInventory().validateCapacity(itemTemplate, l)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return false;
        }
        return true;
    }

    public static final boolean checkIfCanDiscard(Player player, ItemInstance itemInstance) {
        if (player.isMounted() && PetDataHolder.getInstance().getByControlItemId(itemInstance.getItemId()) != null) {
            return false;
        }
        if (player.getPetControlItem() == itemInstance) {
            return false;
        }
        if (player.getEnchantScroll() == itemInstance) {
            return false;
        }
        if (itemInstance.isCursed()) {
            return false;
        }
        return !itemInstance.getTemplate().isQuest();
    }

    public static int getPaperdollIndex(long l) {
        return bZ.getOrDefault(l, -1);
    }

    public static boolean haveAnyOf(Player player, int ... nArray) {
        for (int n : nArray) {
            if (player.getInventory().getItemByItemId(n) == null) continue;
            return true;
        }
        return false;
    }
}
